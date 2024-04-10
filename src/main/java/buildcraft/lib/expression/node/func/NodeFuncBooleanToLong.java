package buildcraft.lib.expression.node.func;


import buildcraft.lib.expression.NodeInliningHelper;
import buildcraft.lib.expression.api.*;
import buildcraft.lib.expression.api.INodeFunc.INodeFuncLong;
import buildcraft.lib.expression.node.value.NodeConstantLong;

import java.util.Objects;

public class NodeFuncBooleanToLong extends NodeFuncBase implements INodeFuncLong {
    public final IFuncBooleanToLong function;
    private final StringFunctionBi stringFunction;

    public NodeFuncBooleanToLong(String name, IFuncBooleanToLong function) {
        this(function, (a) -> {
            return "[ boolean -> long ] " + name + "(" + a + ")";
        });
    }

    public NodeFuncBooleanToLong(IFuncBooleanToLong function, StringFunctionBi stringFunction) {
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}");
    }

    public NodeFuncBooleanToLong setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeLong getNode(INodeStack stack) throws InvalidExpressionException
    {
        IExpressionNode.INodeBoolean a = stack.popBoolean();
        return this.create(a);
    }

    public FuncBooleanToLong create(IExpressionNode.INodeBoolean argA) {
        return new FuncBooleanToLong(argA);
    }

    @FunctionalInterface
    public interface IFuncBooleanToLong {
        long apply(boolean var1);
    }

    public class FuncBooleanToLong implements IExpressionNode.INodeLong, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeBoolean argA;

        public FuncBooleanToLong(IExpressionNode.INodeBoolean argA) {
            this.argA = argA;
        }

        public long evaluate() {
            return NodeFuncBooleanToLong.this.function.apply(this.argA.evaluate());
        }

        public IExpressionNode.INodeLong inline() {
            return !NodeFuncBooleanToLong.this.canInline ? (IExpressionNode.INodeLong) NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncBooleanToLong.this.new FuncBooleanToLong(a);
            }, (a) -> {
                return NodeFuncBooleanToLong.this.new FuncBooleanToLong(a);
            }) : (IExpressionNode.INodeLong)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncBooleanToLong.this.new FuncBooleanToLong(a);
            }, (a) -> {
                return NodeConstantLong.of(NodeFuncBooleanToLong.this.function.apply(a.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncBooleanToLong.this.canInline) {
                if (NodeFuncBooleanToLong.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncBooleanToLong.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(this.argA);
        }

        public String toString() {
            return NodeFuncBooleanToLong.this.stringFunction.apply(this.argA.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncBooleanToLong.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                FuncBooleanToLong other = (FuncBooleanToLong)obj;
                return Objects.equals(this.argA, other.argA);
            } else {
                return false;
            }
        }
    }
}
