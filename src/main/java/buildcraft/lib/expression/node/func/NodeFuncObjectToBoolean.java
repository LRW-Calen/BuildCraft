package buildcraft.lib.expression.node.func;


import buildcraft.lib.expression.NodeInliningHelper;
import buildcraft.lib.expression.api.*;
import buildcraft.lib.expression.node.value.NodeConstantBoolean;

import java.util.Objects;

public class NodeFuncObjectToBoolean<A> extends NodeFuncBase implements INodeFunc.INodeFuncBoolean {
    public final IFuncObjectToBoolean<A> function;
    private final StringFunctionBi stringFunction;
    private final Class<A> argTypeA;

    public NodeFuncObjectToBoolean(String name, Class<A> argTypeA, IFuncObjectToBoolean<A> function) {
        this(argTypeA, function, (a) -> {
            return "[ " + NodeTypes.getName(argTypeA) + " -> boolean ] " + name + "(" + a + ")";
        });
    }

    public NodeFuncObjectToBoolean(Class<A> argTypeA, IFuncObjectToBoolean<A> function, StringFunctionBi stringFunction) {
        this.argTypeA = argTypeA;
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}");
    }

    public NodeFuncObjectToBoolean<A> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeBoolean getNode(INodeStack stack) throws InvalidExpressionException
    {
        IExpressionNode.INodeObject<A> a = stack.popObject(this.argTypeA);
        return this.create(a);
    }

    public NodeFuncObjectToBoolean<A>.FuncObjectToBoolean create(IExpressionNode.INodeObject<A> argA) {
        return new FuncObjectToBoolean(argA);
    }

    @FunctionalInterface
    public interface IFuncObjectToBoolean<A> {
        boolean apply(A var1);
    }

    public class FuncObjectToBoolean implements IExpressionNode.INodeBoolean, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeObject<A> argA;

        public FuncObjectToBoolean(IExpressionNode.INodeObject<A> argA) {
            this.argA = argA;
        }

        public boolean evaluate() {
            return NodeFuncObjectToBoolean.this.function.apply(this.argA.evaluate());
        }

        public IExpressionNode.INodeBoolean inline() {
            return !NodeFuncObjectToBoolean.this.canInline ? (IExpressionNode.INodeBoolean)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncObjectToBoolean.this.new FuncObjectToBoolean(a);
            }, (a) -> {
                return NodeFuncObjectToBoolean.this.new FuncObjectToBoolean(a);
            }) : (IExpressionNode.INodeBoolean) NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncObjectToBoolean.this.new FuncObjectToBoolean(a);
            }, (a) -> {
                return NodeConstantBoolean.of(NodeFuncObjectToBoolean.this.function.apply(a.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncObjectToBoolean.this.canInline) {
                if (NodeFuncObjectToBoolean.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncObjectToBoolean.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(this.argA);
        }

        public String toString() {
            return NodeFuncObjectToBoolean.this.stringFunction.apply(this.argA.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncObjectToBoolean.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncObjectToBoolean<A>.FuncObjectToBoolean other = (FuncObjectToBoolean)obj;
                return Objects.equals(this.argA, other.argA);
            } else {
                return false;
            }
        }
    }
}
