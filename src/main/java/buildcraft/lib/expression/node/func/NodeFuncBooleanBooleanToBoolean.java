//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package buildcraft.lib.expression.node.func;

import buildcraft.lib.expression.NodeInliningHelper;
import buildcraft.lib.expression.api.IDependancyVisitor;
import buildcraft.lib.expression.api.IDependantNode;
import buildcraft.lib.expression.api.IExpressionNode;
import buildcraft.lib.expression.api.INodeFunc;
import buildcraft.lib.expression.api.INodeStack;
import buildcraft.lib.expression.api.InvalidExpressionException;
import buildcraft.lib.expression.node.value.NodeConstantBoolean;
import java.util.Objects;

public class NodeFuncBooleanBooleanToBoolean extends NodeFuncBase implements INodeFunc.INodeFuncBoolean {
    public final IFuncBooleanBooleanToBoolean function;
    private final StringFunctionTri stringFunction;

    public NodeFuncBooleanBooleanToBoolean(String name, IFuncBooleanBooleanToBoolean function) {
        this(function, (a, b) -> {
            return "[ boolean, boolean -> boolean ] " + name + "(" + a + ", " + b + ")";
        });
    }

    public NodeFuncBooleanBooleanToBoolean(IFuncBooleanBooleanToBoolean function, StringFunctionTri stringFunction) {
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}", "{B}");
    }

    public NodeFuncBooleanBooleanToBoolean setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeBoolean getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeBoolean b = stack.popBoolean();
        IExpressionNode.INodeBoolean a = stack.popBoolean();
        return this.create(a, b);
    }

    public FuncBooleanBooleanToBoolean create(IExpressionNode.INodeBoolean argA, IExpressionNode.INodeBoolean argB) {
        return new FuncBooleanBooleanToBoolean(argA, argB);
    }

    @FunctionalInterface
    public interface IFuncBooleanBooleanToBoolean {
        boolean apply(boolean var1, boolean var2);
    }

    public class FuncBooleanBooleanToBoolean implements IExpressionNode.INodeBoolean, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeBoolean argA;
        public final IExpressionNode.INodeBoolean argB;

        public FuncBooleanBooleanToBoolean(IExpressionNode.INodeBoolean argA, IExpressionNode.INodeBoolean argB) {
            this.argA = argA;
            this.argB = argB;
        }

        public boolean evaluate() {
            return NodeFuncBooleanBooleanToBoolean.this.function.apply(this.argA.evaluate(), this.argB.evaluate());
        }

        public IExpressionNode.INodeBoolean inline() {
            return !NodeFuncBooleanBooleanToBoolean.this.canInline ? (IExpressionNode.INodeBoolean)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncBooleanBooleanToBoolean.this.new FuncBooleanBooleanToBoolean(a, b);
            }, (a, b) -> {
                return NodeFuncBooleanBooleanToBoolean.this.new FuncBooleanBooleanToBoolean(a, b);
            }) : (IExpressionNode.INodeBoolean)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncBooleanBooleanToBoolean.this.new FuncBooleanBooleanToBoolean(a, b);
            }, (a, b) -> {
                return NodeConstantBoolean.of(NodeFuncBooleanBooleanToBoolean.this.function.apply(a.evaluate(), b.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncBooleanBooleanToBoolean.this.canInline) {
                if (NodeFuncBooleanBooleanToBoolean.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncBooleanBooleanToBoolean.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB});
        }

        public String toString() {
            return NodeFuncBooleanBooleanToBoolean.this.stringFunction.apply(this.argA.toString(), this.argB.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncBooleanBooleanToBoolean.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                FuncBooleanBooleanToBoolean other = (FuncBooleanBooleanToBoolean)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB);
            } else {
                return false;
            }
        }
    }
}
