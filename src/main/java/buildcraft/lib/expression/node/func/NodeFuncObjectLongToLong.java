package buildcraft.lib.expression.node.func;


import buildcraft.lib.expression.NodeInliningHelper;
import buildcraft.lib.expression.api.*;
import buildcraft.lib.expression.node.value.NodeConstantLong;

import java.util.Objects;

public class NodeFuncObjectLongToLong<A> extends NodeFuncBase implements INodeFunc.INodeFuncLong {
    public final IFuncObjectLongToLong<A> function;
    private final StringFunctionTri stringFunction;
    private final Class<A> argTypeA;

    public NodeFuncObjectLongToLong(String name, Class<A> argTypeA, IFuncObjectLongToLong<A> function) {
        this(argTypeA, function, (a, b) -> {
            return "[ " + NodeTypes.getName(argTypeA) + ", long -> long ] " + name + "(" + a + ", " + b + ")";
        });
    }

    public NodeFuncObjectLongToLong(Class<A> argTypeA, IFuncObjectLongToLong<A> function, StringFunctionTri stringFunction) {
        this.argTypeA = argTypeA;
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}", "{B}");
    }

    public NodeFuncObjectLongToLong<A> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeLong getNode(INodeStack stack) throws InvalidExpressionException
    {
        IExpressionNode.INodeLong b = stack.popLong();
        IExpressionNode.INodeObject<A> a = stack.popObject(this.argTypeA);
        return this.create(a, b);
    }

    public NodeFuncObjectLongToLong<A>.FuncObjectLongToLong create(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeLong argB) {
        return new FuncObjectLongToLong(argA, argB);
    }

    @FunctionalInterface
    public interface IFuncObjectLongToLong<A> {
        long apply(A var1, long var2);
    }

    public class FuncObjectLongToLong implements IExpressionNode.INodeLong, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeObject<A> argA;
        public final IExpressionNode.INodeLong argB;

        public FuncObjectLongToLong(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeLong argB) {
            this.argA = argA;
            this.argB = argB;
        }

        public long evaluate() {
            return NodeFuncObjectLongToLong.this.function.apply(this.argA.evaluate(), this.argB.evaluate());
        }

        public IExpressionNode.INodeLong inline() {
            return !NodeFuncObjectLongToLong.this.canInline ? (IExpressionNode.INodeLong)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncObjectLongToLong.this.new FuncObjectLongToLong(a, b);
            }, (a, b) -> {
                return NodeFuncObjectLongToLong.this.new FuncObjectLongToLong(a, b);
            }) : (IExpressionNode.INodeLong) NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncObjectLongToLong.this.new FuncObjectLongToLong(a, b);
            }, (a, b) -> {
                return NodeConstantLong.of(NodeFuncObjectLongToLong.this.function.apply(a.evaluate(), b.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncObjectLongToLong.this.canInline) {
                if (NodeFuncObjectLongToLong.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncObjectLongToLong.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB});
        }

        public String toString() {
            return NodeFuncObjectLongToLong.this.stringFunction.apply(this.argA.toString(), this.argB.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncObjectLongToLong.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncObjectLongToLong<A>.FuncObjectLongToLong other = (FuncObjectLongToLong)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB);
            } else {
                return false;
            }
        }
    }
}
