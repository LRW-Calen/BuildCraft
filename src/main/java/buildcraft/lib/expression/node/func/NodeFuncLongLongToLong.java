package buildcraft.lib.expression.node.func;


import buildcraft.lib.expression.NodeInliningHelper;
import buildcraft.lib.expression.api.*;
import buildcraft.lib.expression.node.value.NodeConstantLong;

import java.util.Objects;

public class NodeFuncLongLongToLong extends NodeFuncBase implements INodeFunc.INodeFuncLong {
    public final IFuncLongLongToLong function;
    private final StringFunctionTri stringFunction;

    public NodeFuncLongLongToLong(String name, IFuncLongLongToLong function) {
        this(function, (a, b) -> {
            return "[ long, long -> long ] " + name + "(" + a + ", " + b + ")";
        });
    }

    public NodeFuncLongLongToLong(IFuncLongLongToLong function, StringFunctionTri stringFunction) {
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}", "{B}");
    }

    public NodeFuncLongLongToLong setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeLong getNode(INodeStack stack) throws InvalidExpressionException
    {
        IExpressionNode.INodeLong b = stack.popLong();
        IExpressionNode.INodeLong a = stack.popLong();
        return this.create(a, b);
    }

    public FuncLongLongToLong create(IExpressionNode.INodeLong argA, IExpressionNode.INodeLong argB) {
        return new FuncLongLongToLong(argA, argB);
    }

    @FunctionalInterface
    public interface IFuncLongLongToLong {
        long apply(long var1, long var3);
    }

    public class FuncLongLongToLong implements IExpressionNode.INodeLong, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeLong argA;
        public final IExpressionNode.INodeLong argB;

        public FuncLongLongToLong(IExpressionNode.INodeLong argA, IExpressionNode.INodeLong argB) {
            this.argA = argA;
            this.argB = argB;
        }

        public long evaluate() {
            return NodeFuncLongLongToLong.this.function.apply(this.argA.evaluate(), this.argB.evaluate());
        }

        public IExpressionNode.INodeLong inline() {
            return !NodeFuncLongLongToLong.this.canInline ? (IExpressionNode.INodeLong)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncLongLongToLong.this.new FuncLongLongToLong(a, b);
            }, (a, b) -> {
                return NodeFuncLongLongToLong.this.new FuncLongLongToLong(a, b);
            }) : (IExpressionNode.INodeLong) NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncLongLongToLong.this.new FuncLongLongToLong(a, b);
            }, (a, b) -> {
                return NodeConstantLong.of(NodeFuncLongLongToLong.this.function.apply(a.evaluate(), b.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncLongLongToLong.this.canInline) {
                if (NodeFuncLongLongToLong.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncLongLongToLong.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB});
        }

        public String toString() {
            return NodeFuncLongLongToLong.this.stringFunction.apply(this.argA.toString(), this.argB.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncLongLongToLong.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                FuncLongLongToLong other = (FuncLongLongToLong)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB);
            } else {
                return false;
            }
        }
    }
}
