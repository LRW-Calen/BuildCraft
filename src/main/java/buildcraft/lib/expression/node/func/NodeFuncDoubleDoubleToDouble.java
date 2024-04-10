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
import buildcraft.lib.expression.node.value.NodeConstantDouble;
import java.util.Objects;

public class NodeFuncDoubleDoubleToDouble extends NodeFuncBase implements INodeFunc.INodeFuncDouble {
    public final IFuncDoubleDoubleToDouble function;
    private final StringFunctionTri stringFunction;

    public NodeFuncDoubleDoubleToDouble(String name, IFuncDoubleDoubleToDouble function) {
        this(function, (a, b) -> {
            return "[ double, double -> double ] " + name + "(" + a + ", " + b + ")";
        });
    }

    public NodeFuncDoubleDoubleToDouble(IFuncDoubleDoubleToDouble function, StringFunctionTri stringFunction) {
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}", "{B}");
    }

    public NodeFuncDoubleDoubleToDouble setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeDouble getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeDouble b = stack.popDouble();
        IExpressionNode.INodeDouble a = stack.popDouble();
        return this.create(a, b);
    }

    public FuncDoubleDoubleToDouble create(IExpressionNode.INodeDouble argA, IExpressionNode.INodeDouble argB) {
        return new FuncDoubleDoubleToDouble(argA, argB);
    }

    @FunctionalInterface
    public interface IFuncDoubleDoubleToDouble {
        double apply(double var1, double var3);
    }

    public class FuncDoubleDoubleToDouble implements IExpressionNode.INodeDouble, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeDouble argA;
        public final IExpressionNode.INodeDouble argB;

        public FuncDoubleDoubleToDouble(IExpressionNode.INodeDouble argA, IExpressionNode.INodeDouble argB) {
            this.argA = argA;
            this.argB = argB;
        }

        public double evaluate() {
            return NodeFuncDoubleDoubleToDouble.this.function.apply(this.argA.evaluate(), this.argB.evaluate());
        }

        public IExpressionNode.INodeDouble inline() {
            return !NodeFuncDoubleDoubleToDouble.this.canInline ? (IExpressionNode.INodeDouble)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncDoubleDoubleToDouble.this.new FuncDoubleDoubleToDouble(a, b);
            }, (a, b) -> {
                return NodeFuncDoubleDoubleToDouble.this.new FuncDoubleDoubleToDouble(a, b);
            }) : (IExpressionNode.INodeDouble)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncDoubleDoubleToDouble.this.new FuncDoubleDoubleToDouble(a, b);
            }, (a, b) -> {
                return NodeConstantDouble.of(NodeFuncDoubleDoubleToDouble.this.function.apply(a.evaluate(), b.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncDoubleDoubleToDouble.this.canInline) {
                if (NodeFuncDoubleDoubleToDouble.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncDoubleDoubleToDouble.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB});
        }

        public String toString() {
            return NodeFuncDoubleDoubleToDouble.this.stringFunction.apply(this.argA.toString(), this.argB.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncDoubleDoubleToDouble.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                FuncDoubleDoubleToDouble other = (FuncDoubleDoubleToDouble)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB);
            } else {
                return false;
            }
        }
    }
}
