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

public class NodeFuncDoubleDoubleToBoolean extends NodeFuncBase implements INodeFunc.INodeFuncBoolean {
    public final IFuncDoubleDoubleToBoolean function;
    private final StringFunctionTri stringFunction;

    public NodeFuncDoubleDoubleToBoolean(String name, IFuncDoubleDoubleToBoolean function) {
        this(function, (a, b) -> {
            return "[ double, double -> boolean ] " + name + "(" + a + ", " + b + ")";
        });
    }

    public NodeFuncDoubleDoubleToBoolean(IFuncDoubleDoubleToBoolean function, StringFunctionTri stringFunction) {
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}", "{B}");
    }

    public NodeFuncDoubleDoubleToBoolean setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeBoolean getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeDouble b = stack.popDouble();
        IExpressionNode.INodeDouble a = stack.popDouble();
        return this.create(a, b);
    }

    public FuncDoubleDoubleToBoolean create(IExpressionNode.INodeDouble argA, IExpressionNode.INodeDouble argB) {
        return new FuncDoubleDoubleToBoolean(argA, argB);
    }

    @FunctionalInterface
    public interface IFuncDoubleDoubleToBoolean {
        boolean apply(double var1, double var3);
    }

    public class FuncDoubleDoubleToBoolean implements IExpressionNode.INodeBoolean, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeDouble argA;
        public final IExpressionNode.INodeDouble argB;

        public FuncDoubleDoubleToBoolean(IExpressionNode.INodeDouble argA, IExpressionNode.INodeDouble argB) {
            this.argA = argA;
            this.argB = argB;
        }

        public boolean evaluate() {
            return NodeFuncDoubleDoubleToBoolean.this.function.apply(this.argA.evaluate(), this.argB.evaluate());
        }

        public IExpressionNode.INodeBoolean inline() {
            return !NodeFuncDoubleDoubleToBoolean.this.canInline ? (IExpressionNode.INodeBoolean)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncDoubleDoubleToBoolean.this.new FuncDoubleDoubleToBoolean(a, b);
            }, (a, b) -> {
                return NodeFuncDoubleDoubleToBoolean.this.new FuncDoubleDoubleToBoolean(a, b);
            }) : (IExpressionNode.INodeBoolean)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncDoubleDoubleToBoolean.this.new FuncDoubleDoubleToBoolean(a, b);
            }, (a, b) -> {
                return NodeConstantBoolean.of(NodeFuncDoubleDoubleToBoolean.this.function.apply(a.evaluate(), b.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncDoubleDoubleToBoolean.this.canInline) {
                if (NodeFuncDoubleDoubleToBoolean.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncDoubleDoubleToBoolean.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB});
        }

        public String toString() {
            return NodeFuncDoubleDoubleToBoolean.this.stringFunction.apply(this.argA.toString(), this.argB.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncDoubleDoubleToBoolean.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                FuncDoubleDoubleToBoolean other = (FuncDoubleDoubleToBoolean)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB);
            } else {
                return false;
            }
        }
    }
}
