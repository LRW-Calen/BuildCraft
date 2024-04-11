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
import buildcraft.lib.expression.node.value.NodeConstantBoolean;
import java.util.Objects;

public class NodeFuncLongLongToBoolean extends NodeFuncBase implements INodeFunc.INodeFuncBoolean {
    public final IFuncLongLongToBoolean function;
    private final StringFunctionTri stringFunction;

    public NodeFuncLongLongToBoolean(String name, IFuncLongLongToBoolean function) {
        this(function, (a, b) -> {
            return "[ long, long -> boolean ] " + name + "(" + a + ", " + b + ")";
        });
    }

    public NodeFuncLongLongToBoolean(IFuncLongLongToBoolean function, StringFunctionTri stringFunction) {
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}", "{B}");
    }

    public NodeFuncLongLongToBoolean setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeBoolean getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeLong b = stack.popLong();
        IExpressionNode.INodeLong a = stack.popLong();
        return this.create(a, b);
    }

    public FuncLongLongToBoolean create(IExpressionNode.INodeLong argA, IExpressionNode.INodeLong argB) {
        return new FuncLongLongToBoolean(argA, argB);
    }

    @FunctionalInterface
    public interface IFuncLongLongToBoolean {
        boolean apply(long var1, long var3);
    }

    public class FuncLongLongToBoolean implements IExpressionNode.INodeBoolean, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeLong argA;
        public final IExpressionNode.INodeLong argB;

        public FuncLongLongToBoolean(IExpressionNode.INodeLong argA, IExpressionNode.INodeLong argB) {
            this.argA = argA;
            this.argB = argB;
        }

        public boolean evaluate() {
            return NodeFuncLongLongToBoolean.this.function.apply(this.argA.evaluate(), this.argB.evaluate());
        }

        public IExpressionNode.INodeBoolean inline() {
            return !NodeFuncLongLongToBoolean.this.canInline ? (IExpressionNode.INodeBoolean)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncLongLongToBoolean.this.new FuncLongLongToBoolean(a, b);
            }, (a, b) -> {
                return NodeFuncLongLongToBoolean.this.new FuncLongLongToBoolean(a, b);
            }) : (IExpressionNode.INodeBoolean)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncLongLongToBoolean.this.new FuncLongLongToBoolean(a, b);
            }, (a, b) -> {
                return NodeConstantBoolean.of(NodeFuncLongLongToBoolean.this.function.apply(a.evaluate(), b.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncLongLongToBoolean.this.canInline) {
                if (NodeFuncLongLongToBoolean.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncLongLongToBoolean.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB});
        }

        public String toString() {
            return NodeFuncLongLongToBoolean.this.stringFunction.apply(this.argA.toString(), this.argB.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncLongLongToBoolean.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                FuncLongLongToBoolean other = (FuncLongLongToBoolean)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB);
            } else {
                return false;
            }
        }
    }
}
