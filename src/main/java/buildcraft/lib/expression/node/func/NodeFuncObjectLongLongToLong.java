package buildcraft.lib.expression.node.func;


import buildcraft.lib.expression.NodeInliningHelper;
import buildcraft.lib.expression.api.*;
import buildcraft.lib.expression.node.value.NodeConstantLong;

import java.util.Objects;

public class NodeFuncObjectLongLongToLong<A> extends NodeFuncBase implements INodeFunc.INodeFuncLong {
    public final IFuncObjectLongLongToLong<A> function;
    private final StringFunctionQuad stringFunction;
    private final Class<A> argTypeA;

    public NodeFuncObjectLongLongToLong(String name, Class<A> argTypeA, IFuncObjectLongLongToLong<A> function) {
        this(argTypeA, function, (a, b, c) -> {
            return "[ " + NodeTypes.getName(argTypeA) + ", long, long -> long ] " + name + "(" + a + ", " + b + ", " + c + ")";
        });
    }

    public NodeFuncObjectLongLongToLong(Class<A> argTypeA, IFuncObjectLongLongToLong<A> function, StringFunctionQuad stringFunction) {
        this.argTypeA = argTypeA;
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}", "{B}", "{C}");
    }

    public NodeFuncObjectLongLongToLong<A> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeLong getNode(INodeStack stack) throws InvalidExpressionException
    {
        IExpressionNode.INodeLong c = stack.popLong();
        IExpressionNode.INodeLong b = stack.popLong();
        IExpressionNode.INodeObject<A> a = stack.popObject(this.argTypeA);
        return this.create(a, b, c);
    }

    public NodeFuncObjectLongLongToLong<A>.FuncObjectLongLongToLong create(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeLong argB, IExpressionNode.INodeLong argC) {
        return new FuncObjectLongLongToLong(argA, argB, argC);
    }

    @FunctionalInterface
    public interface IFuncObjectLongLongToLong<A> {
        long apply(A var1, long var2, long var4);
    }

    public class FuncObjectLongLongToLong implements IExpressionNode.INodeLong, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeObject<A> argA;
        public final IExpressionNode.INodeLong argB;
        public final IExpressionNode.INodeLong argC;

        public FuncObjectLongLongToLong(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeLong argB, IExpressionNode.INodeLong argC) {
            this.argA = argA;
            this.argB = argB;
            this.argC = argC;
        }

        public long evaluate() {
            return NodeFuncObjectLongLongToLong.this.function.apply(this.argA.evaluate(), this.argB.evaluate(), this.argC.evaluate());
        }

        public IExpressionNode.INodeLong inline() {
            return !NodeFuncObjectLongLongToLong.this.canInline ? (IExpressionNode.INodeLong) NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, (a, b, c) -> {
                return NodeFuncObjectLongLongToLong.this.new FuncObjectLongLongToLong(a, b, c);
            }, (a, b, c) -> {
                return NodeFuncObjectLongLongToLong.this.new FuncObjectLongLongToLong(a, b, c);
            }) : (IExpressionNode.INodeLong)NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, (a, b, c) -> {
                return NodeFuncObjectLongLongToLong.this.new FuncObjectLongLongToLong(a, b, c);
            }, (a, b, c) -> {
                return NodeConstantLong.of(NodeFuncObjectLongLongToLong.this.function.apply(a.evaluate(), b.evaluate(), c.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncObjectLongLongToLong.this.canInline) {
                if (NodeFuncObjectLongLongToLong.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncObjectLongLongToLong.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB, this.argC});
        }

        public String toString() {
            return NodeFuncObjectLongLongToLong.this.stringFunction.apply(this.argA.toString(), this.argB.toString(), this.argC.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncObjectLongLongToLong.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB, this.argC});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncObjectLongLongToLong<A>.FuncObjectLongLongToLong other = (FuncObjectLongLongToLong)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB) && Objects.equals(this.argC, other.argC);
            } else {
                return false;
            }
        }
    }
}
