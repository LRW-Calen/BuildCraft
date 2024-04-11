package buildcraft.lib.expression.node.func;


import buildcraft.lib.expression.NodeInliningHelper;
import buildcraft.lib.expression.api.*;
import buildcraft.lib.expression.node.value.NodeConstantLong;

import java.util.Objects;

public class NodeFuncLongLongLongToLong extends NodeFuncBase implements INodeFunc.INodeFuncLong {
    public final IFuncLongLongLongToLong function;
    private final StringFunctionQuad stringFunction;

    public NodeFuncLongLongLongToLong(String name, IFuncLongLongLongToLong function) {
        this(function, (a, b, c) -> {
            return "[ long, long, long -> long ] " + name + "(" + a + ", " + b + ", " + c + ")";
        });
    }

    public NodeFuncLongLongLongToLong(IFuncLongLongLongToLong function, StringFunctionQuad stringFunction) {
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}", "{B}", "{C}");
    }

    public NodeFuncLongLongLongToLong setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeLong getNode(INodeStack stack) throws InvalidExpressionException
    {
        IExpressionNode.INodeLong c = stack.popLong();
        IExpressionNode.INodeLong b = stack.popLong();
        IExpressionNode.INodeLong a = stack.popLong();
        return this.create(a, b, c);
    }

    public FuncLongLongLongToLong create(IExpressionNode.INodeLong argA, IExpressionNode.INodeLong argB, IExpressionNode.INodeLong argC) {
        return new FuncLongLongLongToLong(argA, argB, argC);
    }

    @FunctionalInterface
    public interface IFuncLongLongLongToLong {
        long apply(long var1, long var3, long var5);
    }

    public class FuncLongLongLongToLong implements IExpressionNode.INodeLong, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeLong argA;
        public final IExpressionNode.INodeLong argB;
        public final IExpressionNode.INodeLong argC;

        public FuncLongLongLongToLong(IExpressionNode.INodeLong argA, IExpressionNode.INodeLong argB, IExpressionNode.INodeLong argC) {
            this.argA = argA;
            this.argB = argB;
            this.argC = argC;
        }

        public long evaluate() {
            return NodeFuncLongLongLongToLong.this.function.apply(this.argA.evaluate(), this.argB.evaluate(), this.argC.evaluate());
        }

        public IExpressionNode.INodeLong inline() {
            return !NodeFuncLongLongLongToLong.this.canInline ? (IExpressionNode.INodeLong)NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, (a, b, c) -> {
                return NodeFuncLongLongLongToLong.this.new FuncLongLongLongToLong(a, b, c);
            }, (a, b, c) -> {
                return NodeFuncLongLongLongToLong.this.new FuncLongLongLongToLong(a, b, c);
            }) : (IExpressionNode.INodeLong) NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, (a, b, c) -> {
                return NodeFuncLongLongLongToLong.this.new FuncLongLongLongToLong(a, b, c);
            }, (a, b, c) -> {
                return NodeConstantLong.of(NodeFuncLongLongLongToLong.this.function.apply(a.evaluate(), b.evaluate(), c.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncLongLongLongToLong.this.canInline) {
                if (NodeFuncLongLongLongToLong.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncLongLongLongToLong.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB, this.argC});
        }

        public String toString() {
            return NodeFuncLongLongLongToLong.this.stringFunction.apply(this.argA.toString(), this.argB.toString(), this.argC.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncLongLongLongToLong.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB, this.argC});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                FuncLongLongLongToLong other = (FuncLongLongLongToLong)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB) && Objects.equals(this.argC, other.argC);
            } else {
                return false;
            }
        }
    }
}
