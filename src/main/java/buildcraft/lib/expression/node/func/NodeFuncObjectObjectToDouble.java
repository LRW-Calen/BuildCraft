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
import buildcraft.lib.expression.api.NodeTypes;
import buildcraft.lib.expression.node.value.NodeConstantDouble;
import java.util.Objects;

public class NodeFuncObjectObjectToDouble<A, B> extends NodeFuncBase implements INodeFunc.INodeFuncDouble {
    public final IFuncObjectObjectToDouble<A, B> function;
    private final StringFunctionTri stringFunction;
    private final Class<A> argTypeA;
    private final Class<B> argTypeB;

    public NodeFuncObjectObjectToDouble(String name, Class<A> argTypeA, Class<B> argTypeB, IFuncObjectObjectToDouble<A, B> function) {
        this(argTypeA, argTypeB, function, (a, b) -> {
            return "[ " + NodeTypes.getName(argTypeA) + ", " + NodeTypes.getName(argTypeB) + " -> double ] " + name + "(" + a + ", " + b + ")";
        });
    }

    public NodeFuncObjectObjectToDouble(Class<A> argTypeA, Class<B> argTypeB, IFuncObjectObjectToDouble<A, B> function, StringFunctionTri stringFunction) {
        this.argTypeA = argTypeA;
        this.argTypeB = argTypeB;
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}", "{B}");
    }

    public NodeFuncObjectObjectToDouble<A, B> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeDouble getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeObject<B> b = stack.popObject(this.argTypeB);
        IExpressionNode.INodeObject<A> a = stack.popObject(this.argTypeA);
        return this.create(a, b);
    }

    public NodeFuncObjectObjectToDouble<A, B>.FuncObjectObjectToDouble create(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeObject<B> argB) {
        return new FuncObjectObjectToDouble(argA, argB);
    }

    @FunctionalInterface
    public interface IFuncObjectObjectToDouble<A, B> {
        double apply(A var1, B var2);
    }

    public class FuncObjectObjectToDouble implements IExpressionNode.INodeDouble, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeObject<A> argA;
        public final IExpressionNode.INodeObject<B> argB;

        public FuncObjectObjectToDouble(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeObject<B> argB) {
            this.argA = argA;
            this.argB = argB;
        }

        public double evaluate() {
            return NodeFuncObjectObjectToDouble.this.function.apply(this.argA.evaluate(), this.argB.evaluate());
        }

        public IExpressionNode.INodeDouble inline() {
            return !NodeFuncObjectObjectToDouble.this.canInline ? (IExpressionNode.INodeDouble)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncObjectObjectToDouble.this.new FuncObjectObjectToDouble(a, b);
            }, (a, b) -> {
                return NodeFuncObjectObjectToDouble.this.new FuncObjectObjectToDouble(a, b);
            }) : (IExpressionNode.INodeDouble)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncObjectObjectToDouble.this.new FuncObjectObjectToDouble(a, b);
            }, (a, b) -> {
                return NodeConstantDouble.of(NodeFuncObjectObjectToDouble.this.function.apply(a.evaluate(), b.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncObjectObjectToDouble.this.canInline) {
                if (NodeFuncObjectObjectToDouble.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncObjectObjectToDouble.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB});
        }

        public String toString() {
            return NodeFuncObjectObjectToDouble.this.stringFunction.apply(this.argA.toString(), this.argB.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncObjectObjectToDouble.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncObjectObjectToDouble<A, B>.FuncObjectObjectToDouble other = (FuncObjectObjectToDouble)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB);
            } else {
                return false;
            }
        }
    }
}
