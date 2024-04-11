package buildcraft.lib.expression.node.func;


import buildcraft.lib.expression.NodeInliningHelper;
import buildcraft.lib.expression.api.*;
import buildcraft.lib.expression.node.value.NodeConstantLong;

import java.util.Objects;

public class NodeFuncLongToLong extends NodeFuncBase implements INodeFunc.INodeFuncLong {
    public final IFuncLongToLong function;
    private final StringFunctionBi stringFunction;

    public NodeFuncLongToLong(String name, IFuncLongToLong function) {
        this(function, (a) -> {
            return "[ long -> long ] " + name + "(" + a + ")";
        });
    }

    public NodeFuncLongToLong(IFuncLongToLong function, StringFunctionBi stringFunction) {
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}");
    }

    public NodeFuncLongToLong setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeLong getNode(INodeStack stack) throws InvalidExpressionException
    {
        IExpressionNode.INodeLong a = stack.popLong();
        return this.create(a);
    }

    public FuncLongToLong create(IExpressionNode.INodeLong argA) {
        return new FuncLongToLong(argA);
    }

    @FunctionalInterface
    public interface IFuncLongToLong {
        long apply(long var1);
    }

    public class FuncLongToLong implements IExpressionNode.INodeLong, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeLong argA;

        public FuncLongToLong(IExpressionNode.INodeLong argA) {
            this.argA = argA;
        }

        public long evaluate() {
            return NodeFuncLongToLong.this.function.apply(this.argA.evaluate());
        }

        public IExpressionNode.INodeLong inline() {
            return !NodeFuncLongToLong.this.canInline ? (IExpressionNode.INodeLong)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncLongToLong.this.new FuncLongToLong(a);
            }, (a) -> {
                return NodeFuncLongToLong.this.new FuncLongToLong(a);
            }) : (IExpressionNode.INodeLong) NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncLongToLong.this.new FuncLongToLong(a);
            }, (a) -> {
                return NodeConstantLong.of(NodeFuncLongToLong.this.function.apply(a.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncLongToLong.this.canInline) {
                if (NodeFuncLongToLong.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncLongToLong.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(this.argA);
        }

        public String toString() {
            return NodeFuncLongToLong.this.stringFunction.apply(this.argA.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncLongToLong.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                FuncLongToLong other = (FuncLongToLong)obj;
                return Objects.equals(this.argA, other.argA);
            } else {
                return false;
            }
        }
    }
}
