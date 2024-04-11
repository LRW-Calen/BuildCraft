package buildcraft.lib.expression.node.func;


import buildcraft.lib.expression.NodeInliningHelper;
import buildcraft.lib.expression.api.*;
import buildcraft.lib.expression.node.value.NodeConstantLong;

import java.util.Objects;

public class NodeFuncDoubleToLong extends NodeFuncBase implements INodeFunc.INodeFuncLong {
    public final IFuncDoubleToLong function;
    private final StringFunctionBi stringFunction;

    public NodeFuncDoubleToLong(String name, IFuncDoubleToLong function) {
        this(function, (a) -> {
            return "[ double -> long ] " + name + "(" + a + ")";
        });
    }

    public NodeFuncDoubleToLong(IFuncDoubleToLong function, StringFunctionBi stringFunction) {
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}");
    }

    public NodeFuncDoubleToLong setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeLong getNode(INodeStack stack) throws InvalidExpressionException
    {
        IExpressionNode.INodeDouble a = stack.popDouble();
        return this.create(a);
    }

    public FuncDoubleToLong create(IExpressionNode.INodeDouble argA) {
        return new FuncDoubleToLong(argA);
    }

    @FunctionalInterface
    public interface IFuncDoubleToLong {
        long apply(double var1);
    }

    public class FuncDoubleToLong implements IExpressionNode.INodeLong, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeDouble argA;

        public FuncDoubleToLong(IExpressionNode.INodeDouble argA) {
            this.argA = argA;
        }

        public long evaluate() {
            return NodeFuncDoubleToLong.this.function.apply(this.argA.evaluate());
        }

        public IExpressionNode.INodeLong inline() {
            return !NodeFuncDoubleToLong.this.canInline ? (IExpressionNode.INodeLong) NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncDoubleToLong.this.new FuncDoubleToLong(a);
            }, (a) -> {
                return NodeFuncDoubleToLong.this.new FuncDoubleToLong(a);
            }) : (IExpressionNode.INodeLong)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncDoubleToLong.this.new FuncDoubleToLong(a);
            }, (a) -> {
                return NodeConstantLong.of(NodeFuncDoubleToLong.this.function.apply(a.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncDoubleToLong.this.canInline) {
                if (NodeFuncDoubleToLong.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncDoubleToLong.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(this.argA);
        }

        public String toString() {
            return NodeFuncDoubleToLong.this.stringFunction.apply(this.argA.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncDoubleToLong.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                FuncDoubleToLong other = (FuncDoubleToLong)obj;
                return Objects.equals(this.argA, other.argA);
            } else {
                return false;
            }
        }
    }
}
