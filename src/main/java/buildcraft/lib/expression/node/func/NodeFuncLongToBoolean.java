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

public class NodeFuncLongToBoolean extends NodeFuncBase implements INodeFunc.INodeFuncBoolean {
    public final IFuncLongToBoolean function;
    private final StringFunctionBi stringFunction;

    public NodeFuncLongToBoolean(String name, IFuncLongToBoolean function) {
        this(function, (a) -> {
            return "[ long -> boolean ] " + name + "(" + a + ")";
        });
    }

    public NodeFuncLongToBoolean(IFuncLongToBoolean function, StringFunctionBi stringFunction) {
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}");
    }

    public NodeFuncLongToBoolean setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeBoolean getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeLong a = stack.popLong();
        return this.create(a);
    }

    public FuncLongToBoolean create(IExpressionNode.INodeLong argA) {
        return new FuncLongToBoolean(argA);
    }

    @FunctionalInterface
    public interface IFuncLongToBoolean {
        boolean apply(long var1);
    }

    public class FuncLongToBoolean implements IExpressionNode.INodeBoolean, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeLong argA;

        public FuncLongToBoolean(IExpressionNode.INodeLong argA) {
            this.argA = argA;
        }

        public boolean evaluate() {
            return NodeFuncLongToBoolean.this.function.apply(this.argA.evaluate());
        }

        public IExpressionNode.INodeBoolean inline() {
            return !NodeFuncLongToBoolean.this.canInline ? (IExpressionNode.INodeBoolean)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncLongToBoolean.this.new FuncLongToBoolean(a);
            }, (a) -> {
                return NodeFuncLongToBoolean.this.new FuncLongToBoolean(a);
            }) : (IExpressionNode.INodeBoolean)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncLongToBoolean.this.new FuncLongToBoolean(a);
            }, (a) -> {
                return NodeConstantBoolean.of(NodeFuncLongToBoolean.this.function.apply(a.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncLongToBoolean.this.canInline) {
                if (NodeFuncLongToBoolean.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncLongToBoolean.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(this.argA);
        }

        public String toString() {
            return NodeFuncLongToBoolean.this.stringFunction.apply(this.argA.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncLongToBoolean.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                FuncLongToBoolean other = (FuncLongToBoolean)obj;
                return Objects.equals(this.argA, other.argA);
            } else {
                return false;
            }
        }
    }
}
