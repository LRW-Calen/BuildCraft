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
import buildcraft.lib.expression.api.NodeTypes;
import buildcraft.lib.expression.node.value.NodeConstantObject;
import java.util.Objects;

public class NodeFuncDoubleToObject<R> extends NodeFuncBase implements INodeFunc.INodeFuncObject<R> {
    public final IFuncDoubleToObject<R> function;
    private final StringFunctionBi stringFunction;
    private final Class<R> returnType;

    public NodeFuncDoubleToObject(String name, Class<R> returnType, IFuncDoubleToObject<R> function) {
        this(returnType, function, (a) -> {
            return "[ double -> " + NodeTypes.getName(returnType) + " ] " + name + "(" + a + ")";
        });
    }

    public NodeFuncDoubleToObject(Class<R> returnType, IFuncDoubleToObject<R> function, StringFunctionBi stringFunction) {
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

    public NodeFuncDoubleToObject<R> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeObject<R> getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeDouble a = stack.popDouble();
        return this.create(a);
    }

    public NodeFuncDoubleToObject<R>.FuncDoubleToObject create(IExpressionNode.INodeDouble argA) {
        return new FuncDoubleToObject(argA);
    }

    @FunctionalInterface
    public interface IFuncDoubleToObject<R> {
        R apply(double var1);
    }

    public class FuncDoubleToObject implements IExpressionNode.INodeObject<R>, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeDouble argA;

        public FuncDoubleToObject(IExpressionNode.INodeDouble argA) {
            this.argA = argA;
        }

        public Class<R> getType() {
            return NodeFuncDoubleToObject.this.returnType;
        }

        public R evaluate() {
            return NodeFuncDoubleToObject.this.function.apply(this.argA.evaluate());
        }

        public IExpressionNode.INodeObject<R> inline() {
            return !NodeFuncDoubleToObject.this.canInline ? (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncDoubleToObject.this.new FuncDoubleToObject(a);
            }, (a) -> {
                return NodeFuncDoubleToObject.this.new FuncDoubleToObject(a);
            }) : (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncDoubleToObject.this.new FuncDoubleToObject(a);
            }, (a) -> {
                return new NodeConstantObject(NodeFuncDoubleToObject.this.returnType, NodeFuncDoubleToObject.this.function.apply(a.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncDoubleToObject.this.canInline) {
                if (NodeFuncDoubleToObject.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncDoubleToObject.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(this.argA);
        }

        public String toString() {
            return NodeFuncDoubleToObject.this.stringFunction.apply(this.argA.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncDoubleToObject.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncDoubleToObject<R>.FuncDoubleToObject other = (FuncDoubleToObject)obj;
                return Objects.equals(this.argA, other.argA);
            } else {
                return false;
            }
        }
    }
}
