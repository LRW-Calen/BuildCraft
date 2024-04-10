package buildcraft.lib.expression.node.func;


import buildcraft.lib.expression.NodeInliningHelper;
import buildcraft.lib.expression.api.*;
import buildcraft.lib.expression.node.value.NodeConstantObject;

import java.util.Objects;

public class NodeFuncObjectLongLongToObject<A, R> extends NodeFuncBase implements INodeFunc.INodeFuncObject<R> {
    public final IFuncObjectLongLongToObject<A, R> function;
    private final StringFunctionQuad stringFunction;
    private final Class<A> argTypeA;
    private final Class<R> returnType;

    public NodeFuncObjectLongLongToObject(String name, Class<A> argTypeA, Class<R> returnType, IFuncObjectLongLongToObject<A, R> function) {
        this(argTypeA, returnType, function, (a, b, c) -> {
            return "[ " + NodeTypes.getName(argTypeA) + ", long, long -> " + NodeTypes.getName(returnType) + " ] " + name + "(" + a + ", " + b + ", " + c + ")";
        });
    }

    public NodeFuncObjectLongLongToObject(Class<A> argTypeA, Class<R> returnType, IFuncObjectLongLongToObject<A, R> function, StringFunctionQuad stringFunction) {
        this.argTypeA = argTypeA;
        this.returnType = returnType;
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public Class<R> getType() {
        return this.returnType;
    }

    public String toString() {
        return this.stringFunction.apply("{A}", "{B}", "{C}");
    }

    public NodeFuncObjectLongLongToObject<A, R> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeObject<R> getNode(INodeStack stack) throws InvalidExpressionException
    {
        IExpressionNode.INodeLong c = stack.popLong();
        IExpressionNode.INodeLong b = stack.popLong();
        IExpressionNode.INodeObject<A> a = stack.popObject(this.argTypeA);
        return this.create(a, b, c);
    }

    public NodeFuncObjectLongLongToObject<A, R>.FuncObjectLongLongToObject create(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeLong argB, IExpressionNode.INodeLong argC) {
        return new FuncObjectLongLongToObject(argA, argB, argC);
    }

    @FunctionalInterface
    public interface IFuncObjectLongLongToObject<A, R> {
        R apply(A var1, long var2, long var4);
    }

    public class FuncObjectLongLongToObject implements IExpressionNode.INodeObject<R>, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeObject<A> argA;
        public final IExpressionNode.INodeLong argB;
        public final IExpressionNode.INodeLong argC;

        public FuncObjectLongLongToObject(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeLong argB, IExpressionNode.INodeLong argC) {
            this.argA = argA;
            this.argB = argB;
            this.argC = argC;
        }

        public Class<R> getType() {
            return NodeFuncObjectLongLongToObject.this.returnType;
        }

        public R evaluate() {
            return NodeFuncObjectLongLongToObject.this.function.apply(this.argA.evaluate(), this.argB.evaluate(), this.argC.evaluate());
        }

        public IExpressionNode.INodeObject<R> inline() {
            return !NodeFuncObjectLongLongToObject.this.canInline ? (IExpressionNode.INodeObject) NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, (a, b, c) -> {
                return NodeFuncObjectLongLongToObject.this.new FuncObjectLongLongToObject(a, b, c);
            }, (a, b, c) -> {
                return NodeFuncObjectLongLongToObject.this.new FuncObjectLongLongToObject(a, b, c);
            }) : (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, (a, b, c) -> {
                return NodeFuncObjectLongLongToObject.this.new FuncObjectLongLongToObject(a, b, c);
            }, (a, b, c) -> {
                return new NodeConstantObject(NodeFuncObjectLongLongToObject.this.returnType, NodeFuncObjectLongLongToObject.this.function.apply(a.evaluate(), b.evaluate(), c.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncObjectLongLongToObject.this.canInline) {
                if (NodeFuncObjectLongLongToObject.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncObjectLongLongToObject.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB, this.argC});
        }

        public String toString() {
            return NodeFuncObjectLongLongToObject.this.stringFunction.apply(this.argA.toString(), this.argB.toString(), this.argC.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncObjectLongLongToObject.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB, this.argC});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncObjectLongLongToObject<A, R>.FuncObjectLongLongToObject other = (FuncObjectLongLongToObject)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB) && Objects.equals(this.argC, other.argC);
            } else {
                return false;
            }
        }
    }
}
