package buildcraft.lib.expression.node.func;


import buildcraft.lib.expression.NodeInliningHelper;
import buildcraft.lib.expression.api.*;
import buildcraft.lib.expression.node.value.NodeConstantObject;

import java.util.Objects;

public class NodeFuncObjectToObject<A, R> extends NodeFuncBase implements INodeFunc.INodeFuncObject<R> {
    public final IFuncObjectToObject<A, R> function;
    private final StringFunctionBi stringFunction;
    private final Class<A> argTypeA;
    private final Class<R> returnType;

    public NodeFuncObjectToObject(String name, Class<A> argTypeA, Class<R> returnType, IFuncObjectToObject<A, R> function) {
        this(argTypeA, returnType, function, (a) -> {
            return "[ " + NodeTypes.getName(argTypeA) + " -> " + NodeTypes.getName(returnType) + " ] " + name + "(" + a + ")";
        });
    }

    public NodeFuncObjectToObject(Class<A> argTypeA, Class<R> returnType, IFuncObjectToObject<A, R> function, StringFunctionBi stringFunction) {
        this.argTypeA = argTypeA;
        this.returnType = returnType;
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public Class<R> getType() {
        return this.returnType;
    }

    public String toString() {
        return this.stringFunction.apply("{A}");
    }

    public NodeFuncObjectToObject<A, R> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeObject<R> getNode(INodeStack stack) throws InvalidExpressionException
    {
        IExpressionNode.INodeObject<A> a = stack.popObject(this.argTypeA);
        return this.create(a);
    }

    public NodeFuncObjectToObject<A, R>.FuncObjectToObject create(IExpressionNode.INodeObject<A> argA) {
        return new FuncObjectToObject(argA);
    }

    @FunctionalInterface
    public interface IFuncObjectToObject<A, R> {
        R apply(A var1);
    }

    public class FuncObjectToObject implements IExpressionNode.INodeObject<R>, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeObject<A> argA;

        public FuncObjectToObject(IExpressionNode.INodeObject<A> argA) {
            this.argA = argA;
        }

        public Class<R> getType() {
            return NodeFuncObjectToObject.this.returnType;
        }

        public R evaluate() {
            return NodeFuncObjectToObject.this.function.apply(this.argA.evaluate());
        }

        public IExpressionNode.INodeObject<R> inline() {
            return !NodeFuncObjectToObject.this.canInline ? (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncObjectToObject.this.new FuncObjectToObject(a);
            }, (a) -> {
                return NodeFuncObjectToObject.this.new FuncObjectToObject(a);
            }) : (IExpressionNode.INodeObject) NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncObjectToObject.this.new FuncObjectToObject(a);
            }, (a) -> {
                return new NodeConstantObject(NodeFuncObjectToObject.this.returnType, NodeFuncObjectToObject.this.function.apply(a.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncObjectToObject.this.canInline) {
                if (NodeFuncObjectToObject.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncObjectToObject.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(this.argA);
        }

        public String toString() {
            return NodeFuncObjectToObject.this.stringFunction.apply(this.argA.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncObjectToObject.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncObjectToObject<A, R>.FuncObjectToObject other = (FuncObjectToObject)obj;
                return Objects.equals(this.argA, other.argA);
            } else {
                return false;
            }
        }
    }
}
