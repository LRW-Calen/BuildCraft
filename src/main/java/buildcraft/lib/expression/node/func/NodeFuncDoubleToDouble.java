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

public class NodeFuncDoubleToDouble extends NodeFuncBase implements INodeFunc.INodeFuncDouble {
    public final IFuncDoubleToDouble function;
    private final StringFunctionBi stringFunction;

    public NodeFuncDoubleToDouble(String name, IFuncDoubleToDouble function) {
        this(function, (a) -> {
            return "[ double -> double ] " + name + "(" + a + ")";
        });
    }

    public NodeFuncDoubleToDouble(IFuncDoubleToDouble function, StringFunctionBi stringFunction) {
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}");
    }

    public NodeFuncDoubleToDouble setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeDouble getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeDouble a = stack.popDouble();
        return this.create(a);
    }

    public FuncDoubleToDouble create(IExpressionNode.INodeDouble argA) {
        return new FuncDoubleToDouble(argA);
    }

    @FunctionalInterface
    public interface IFuncDoubleToDouble {
        double apply(double var1);
    }

    public class FuncDoubleToDouble implements IExpressionNode.INodeDouble, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeDouble argA;

        public FuncDoubleToDouble(IExpressionNode.INodeDouble argA) {
            this.argA = argA;
        }

        public double evaluate() {
            return NodeFuncDoubleToDouble.this.function.apply(this.argA.evaluate());
        }

        public IExpressionNode.INodeDouble inline() {
            return !NodeFuncDoubleToDouble.this.canInline ? (IExpressionNode.INodeDouble)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncDoubleToDouble.this.new FuncDoubleToDouble(a);
            }, (a) -> {
                return NodeFuncDoubleToDouble.this.new FuncDoubleToDouble(a);
            }) : (IExpressionNode.INodeDouble)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncDoubleToDouble.this.new FuncDoubleToDouble(a);
            }, (a) -> {
                return NodeConstantDouble.of(NodeFuncDoubleToDouble.this.function.apply(a.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncDoubleToDouble.this.canInline) {
                if (NodeFuncDoubleToDouble.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncDoubleToDouble.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(this.argA);
        }

        public String toString() {
            return NodeFuncDoubleToDouble.this.stringFunction.apply(this.argA.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncDoubleToDouble.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                FuncDoubleToDouble other = (FuncDoubleToDouble)obj;
                return Objects.equals(this.argA, other.argA);
            } else {
                return false;
            }
        }
    }
}
