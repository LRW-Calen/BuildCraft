package buildcraft.lib.expression.node.func;


import buildcraft.lib.expression.NodeInliningHelper;
import buildcraft.lib.expression.api.*;
import buildcraft.lib.expression.node.value.NodeConstantObject;

import java.util.Objects;

public class NodeFuncLongToObject<R> extends NodeFuncBase implements INodeFunc.INodeFuncObject<R> {
    public final IFuncLongToObject<R> function;
    private final StringFunctionBi stringFunction;
    private final Class<R> returnType;

    public NodeFuncLongToObject(String name, Class<R> returnType, IFuncLongToObject<R> function) {
        this(returnType, function, (a) -> {
            return "[ long -> " + NodeTypes.getName(returnType) + " ] " + name + "(" + a + ")";
        });
    }

    public NodeFuncLongToObject(Class<R> returnType, IFuncLongToObject<R> function, StringFunctionBi stringFunction) {
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

    public NodeFuncLongToObject<R> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeObject<R> getNode(INodeStack stack) throws InvalidExpressionException
    {
        IExpressionNode.INodeLong a = stack.popLong();
        return this.create(a);
    }

    public NodeFuncLongToObject<R>.FuncLongToObject create(IExpressionNode.INodeLong argA) {
        return new FuncLongToObject(argA);
    }

    @FunctionalInterface
    public interface IFuncLongToObject<R> {
        R apply(long var1);
    }

    public class FuncLongToObject implements IExpressionNode.INodeObject<R>, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeLong argA;

        public FuncLongToObject(IExpressionNode.INodeLong argA) {
            this.argA = argA;
        }

        public Class<R> getType() {
            return NodeFuncLongToObject.this.returnType;
        }

        public R evaluate() {
            return NodeFuncLongToObject.this.function.apply(this.argA.evaluate());
        }

        public IExpressionNode.INodeObject<R> inline() {
            return !NodeFuncLongToObject.this.canInline ? (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncLongToObject.this.new FuncLongToObject(a);
            }, (a) -> {
                return NodeFuncLongToObject.this.new FuncLongToObject(a);
            }) : (IExpressionNode.INodeObject) NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncLongToObject.this.new FuncLongToObject(a);
            }, (a) -> {
                return new NodeConstantObject(NodeFuncLongToObject.this.returnType, NodeFuncLongToObject.this.function.apply(a.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncLongToObject.this.canInline) {
                if (NodeFuncLongToObject.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncLongToObject.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(this.argA);
        }

        public String toString() {
            return NodeFuncLongToObject.this.stringFunction.apply(this.argA.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncLongToObject.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncLongToObject<R>.FuncLongToObject other = (FuncLongToObject)obj;
                return Objects.equals(this.argA, other.argA);
            } else {
                return false;
            }
        }
    }
}
