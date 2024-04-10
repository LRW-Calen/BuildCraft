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
import buildcraft.lib.expression.node.value.NodeConstantBoolean;
import java.util.Objects;

public class NodeFuncObjectObjectToBoolean<A, B> extends NodeFuncBase implements INodeFunc.INodeFuncBoolean {
    public final IFuncObjectObjectToBoolean<A, B> function;
    private final StringFunctionTri stringFunction;
    private final Class<A> argTypeA;
    private final Class<B> argTypeB;

    public NodeFuncObjectObjectToBoolean(String name, Class<A> argTypeA, Class<B> argTypeB, IFuncObjectObjectToBoolean<A, B> function) {
        this(argTypeA, argTypeB, function, (a, b) -> {
            return "[ " + NodeTypes.getName(argTypeA) + ", " + NodeTypes.getName(argTypeB) + " -> boolean ] " + name + "(" + a + ", " + b + ")";
        });
    }

    public NodeFuncObjectObjectToBoolean(Class<A> argTypeA, Class<B> argTypeB, IFuncObjectObjectToBoolean<A, B> function, StringFunctionTri stringFunction) {
        this.argTypeA = argTypeA;
        this.argTypeB = argTypeB;
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}", "{B}");
    }

    public NodeFuncObjectObjectToBoolean<A, B> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeBoolean getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeObject<B> b = stack.popObject(this.argTypeB);
        IExpressionNode.INodeObject<A> a = stack.popObject(this.argTypeA);
        return this.create(a, b);
    }

    public NodeFuncObjectObjectToBoolean<A, B>.FuncObjectObjectToBoolean create(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeObject<B> argB) {
        return new FuncObjectObjectToBoolean(argA, argB);
    }

    @FunctionalInterface
    public interface IFuncObjectObjectToBoolean<A, B> {
        boolean apply(A var1, B var2);
    }

    public class FuncObjectObjectToBoolean implements IExpressionNode.INodeBoolean, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeObject<A> argA;
        public final IExpressionNode.INodeObject<B> argB;

        public FuncObjectObjectToBoolean(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeObject<B> argB) {
            this.argA = argA;
            this.argB = argB;
        }

        public boolean evaluate() {
            return NodeFuncObjectObjectToBoolean.this.function.apply(this.argA.evaluate(), this.argB.evaluate());
        }

        public IExpressionNode.INodeBoolean inline() {
            return !NodeFuncObjectObjectToBoolean.this.canInline ? (IExpressionNode.INodeBoolean)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncObjectObjectToBoolean.this.new FuncObjectObjectToBoolean(a, b);
            }, (a, b) -> {
                return NodeFuncObjectObjectToBoolean.this.new FuncObjectObjectToBoolean(a, b);
            }) : (IExpressionNode.INodeBoolean)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncObjectObjectToBoolean.this.new FuncObjectObjectToBoolean(a, b);
            }, (a, b) -> {
                return NodeConstantBoolean.of(NodeFuncObjectObjectToBoolean.this.function.apply(a.evaluate(), b.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncObjectObjectToBoolean.this.canInline) {
                if (NodeFuncObjectObjectToBoolean.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncObjectObjectToBoolean.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB});
        }

        public String toString() {
            return NodeFuncObjectObjectToBoolean.this.stringFunction.apply(this.argA.toString(), this.argB.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncObjectObjectToBoolean.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncObjectObjectToBoolean<A, B>.FuncObjectObjectToBoolean other = (FuncObjectObjectToBoolean)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB);
            } else {
                return false;
            }
        }
    }
}
