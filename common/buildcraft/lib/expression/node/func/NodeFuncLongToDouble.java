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
import buildcraft.lib.expression.node.value.NodeConstantDouble;
import java.util.Objects;

public class NodeFuncLongToDouble extends NodeFuncBase implements INodeFunc.INodeFuncDouble {
    public final IFuncLongToDouble function;
    private final StringFunctionBi stringFunction;

    public NodeFuncLongToDouble(String name, IFuncLongToDouble function) {
        this(function, (a) -> {
            return "[ long -> double ] " + name + "(" + a + ")";
        });
    }

    public NodeFuncLongToDouble(IFuncLongToDouble function, StringFunctionBi stringFunction) {
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public String toString() {
        return this.stringFunction.apply("{A}");
    }

    public NodeFuncLongToDouble setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeDouble getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeLong a = stack.popLong();
        return this.create(a);
    }

    public FuncLongToDouble create(IExpressionNode.INodeLong argA) {
        return new FuncLongToDouble(argA);
    }

    @FunctionalInterface
    public interface IFuncLongToDouble {
        double apply(long var1);
    }

    public class FuncLongToDouble implements IExpressionNode.INodeDouble, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeLong argA;

        public FuncLongToDouble(IExpressionNode.INodeLong argA) {
            this.argA = argA;
        }

        public double evaluate() {
            return NodeFuncLongToDouble.this.function.apply(this.argA.evaluate());
        }

        public IExpressionNode.INodeDouble inline() {
            return !NodeFuncLongToDouble.this.canInline ? (IExpressionNode.INodeDouble)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncLongToDouble.this.new FuncLongToDouble(a);
            }, (a) -> {
                return NodeFuncLongToDouble.this.new FuncLongToDouble(a);
            }) : (IExpressionNode.INodeDouble)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncLongToDouble.this.new FuncLongToDouble(a);
            }, (a) -> {
                return NodeConstantDouble.of(NodeFuncLongToDouble.this.function.apply(a.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncLongToDouble.this.canInline) {
                if (NodeFuncLongToDouble.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncLongToDouble.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(this.argA);
        }

        public String toString() {
            return NodeFuncLongToDouble.this.stringFunction.apply(this.argA.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncLongToDouble.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                FuncLongToDouble other = (FuncLongToDouble)obj;
                return Objects.equals(this.argA, other.argA);
            } else {
                return false;
            }
        }
    }
}
