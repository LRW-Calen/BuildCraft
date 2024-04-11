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

public class NodeFuncBooleanToBoolean extends NodeFuncBase implements INodeFunc.INodeFuncBoolean {
    public final IFuncBooleanToBoolean function;
    private final StringFunctionBi stringFunction;

    public NodeFuncBooleanToBoolean(String name, IFuncBooleanToBoolean function) {
        this(function, (a) -> {
            return "[ boolean -> boolean ] " + name + "(" + a + ")";
        });
    }

    public NodeFuncBooleanToBoolean(IFuncBooleanToBoolean function, StringFunctionBi stringFunction) {
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}");
    }

    public NodeFuncBooleanToBoolean setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeBoolean getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeBoolean a = stack.popBoolean();
        return this.create(a);
    }

    public FuncBooleanToBoolean create(IExpressionNode.INodeBoolean argA) {
        return new FuncBooleanToBoolean(argA);
    }

    @FunctionalInterface
    public interface IFuncBooleanToBoolean {
        boolean apply(boolean var1);
    }

    public class FuncBooleanToBoolean implements IExpressionNode.INodeBoolean, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeBoolean argA;

        public FuncBooleanToBoolean(IExpressionNode.INodeBoolean argA) {
            this.argA = argA;
        }

        public boolean evaluate() {
            return NodeFuncBooleanToBoolean.this.function.apply(this.argA.evaluate());
        }

        public IExpressionNode.INodeBoolean inline() {
            return !NodeFuncBooleanToBoolean.this.canInline ? (IExpressionNode.INodeBoolean)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncBooleanToBoolean.this.new FuncBooleanToBoolean(a);
            }, (a) -> {
                return NodeFuncBooleanToBoolean.this.new FuncBooleanToBoolean(a);
            }) : (IExpressionNode.INodeBoolean)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncBooleanToBoolean.this.new FuncBooleanToBoolean(a);
            }, (a) -> {
                return NodeConstantBoolean.of(NodeFuncBooleanToBoolean.this.function.apply(a.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncBooleanToBoolean.this.canInline) {
                if (NodeFuncBooleanToBoolean.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncBooleanToBoolean.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(this.argA);
        }

        public String toString() {
            return NodeFuncBooleanToBoolean.this.stringFunction.apply(this.argA.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncBooleanToBoolean.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                FuncBooleanToBoolean other = (FuncBooleanToBoolean)obj;
                return Objects.equals(this.argA, other.argA);
            } else {
                return false;
            }
        }
    }
}
