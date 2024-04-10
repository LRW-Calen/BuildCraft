package buildcraft.lib.expression.node.func;


import buildcraft.lib.expression.NodeInliningHelper;
import buildcraft.lib.expression.api.*;
import buildcraft.lib.expression.node.value.NodeConstantLong;

import java.util.Objects;

public class NodeFuncObjectToLong<A> extends NodeFuncBase implements INodeFunc.INodeFuncLong {
    public final IFuncObjectToLong<A> function;
    private final StringFunctionBi stringFunction;
    private final Class<A> argTypeA;

    public NodeFuncObjectToLong(String name, Class<A> argTypeA, IFuncObjectToLong<A> function) {
        this(argTypeA, function, (a) -> {
            return "[ " + NodeTypes.getName(argTypeA) + " -> long ] " + name + "(" + a + ")";
        });
    }

    public NodeFuncObjectToLong(Class<A> argTypeA, IFuncObjectToLong<A> function, StringFunctionBi stringFunction) {
        this.argTypeA = argTypeA;
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}");
    }

    public NodeFuncObjectToLong<A> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeLong getNode(INodeStack stack) throws InvalidExpressionException
    {
        IExpressionNode.INodeObject<A> a = stack.popObject(this.argTypeA);
        return this.create(a);
    }

    public NodeFuncObjectToLong<A>.FuncObjectToLong create(IExpressionNode.INodeObject<A> argA) {
        return new FuncObjectToLong(argA);
    }

    @FunctionalInterface
    public interface IFuncObjectToLong<A> {
        long apply(A var1);
    }

    public class FuncObjectToLong implements IExpressionNode.INodeLong, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeObject<A> argA;

        public FuncObjectToLong(IExpressionNode.INodeObject<A> argA) {
            this.argA = argA;
        }

        public long evaluate() {
            return NodeFuncObjectToLong.this.function.apply(this.argA.evaluate());
        }

        public IExpressionNode.INodeLong inline() {
            return !NodeFuncObjectToLong.this.canInline ? (IExpressionNode.INodeLong)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncObjectToLong.this.new FuncObjectToLong(a);
            }, (a) -> {
                return NodeFuncObjectToLong.this.new FuncObjectToLong(a);
            }) : (IExpressionNode.INodeLong) NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncObjectToLong.this.new FuncObjectToLong(a);
            }, (a) -> {
                return NodeConstantLong.of(NodeFuncObjectToLong.this.function.apply(a.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncObjectToLong.this.canInline) {
                if (NodeFuncObjectToLong.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncObjectToLong.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(this.argA);
        }

        public String toString() {
            return NodeFuncObjectToLong.this.stringFunction.apply(this.argA.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncObjectToLong.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncObjectToLong<A>.FuncObjectToLong other = (FuncObjectToLong)obj;
                return Objects.equals(this.argA, other.argA);
            } else {
                return false;
            }
        }
    }
}
