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

public class NodeFuncBooleanToObject<R> extends NodeFuncBase implements INodeFunc.INodeFuncObject<R> {
    public final IFuncBooleanToObject<R> function;
    private final StringFunctionBi stringFunction;
    private final Class<R> returnType;

    public NodeFuncBooleanToObject(String name, Class<R> returnType, IFuncBooleanToObject<R> function) {
        this(returnType, function, (a) -> {
            return "[ boolean -> " + NodeTypes.getName(returnType) + " ] " + name + "(" + a + ")";
        });
    }

    public NodeFuncBooleanToObject(Class<R> returnType, IFuncBooleanToObject<R> function, StringFunctionBi stringFunction) {
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

    public NodeFuncBooleanToObject<R> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeObject<R> getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeBoolean a = stack.popBoolean();
        return this.create(a);
    }

    public NodeFuncBooleanToObject<R>.FuncBooleanToObject create(IExpressionNode.INodeBoolean argA) {
        return new FuncBooleanToObject(argA);
    }

    @FunctionalInterface
    public interface IFuncBooleanToObject<R> {
        R apply(boolean var1);
    }

    public class FuncBooleanToObject implements IExpressionNode.INodeObject<R>, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeBoolean argA;

        public FuncBooleanToObject(IExpressionNode.INodeBoolean argA) {
            this.argA = argA;
        }

        public Class<R> getType() {
            return NodeFuncBooleanToObject.this.returnType;
        }

        public R evaluate() {
            return NodeFuncBooleanToObject.this.function.apply(this.argA.evaluate());
        }

        public IExpressionNode.INodeObject<R> inline() {
            return !NodeFuncBooleanToObject.this.canInline ? (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncBooleanToObject.this.new FuncBooleanToObject(a);
            }, (a) -> {
                return NodeFuncBooleanToObject.this.new FuncBooleanToObject(a);
            }) : (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, (a) -> {
                return NodeFuncBooleanToObject.this.new FuncBooleanToObject(a);
            }, (a) -> {
                return new NodeConstantObject(NodeFuncBooleanToObject.this.returnType, NodeFuncBooleanToObject.this.function.apply(a.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncBooleanToObject.this.canInline) {
                if (NodeFuncBooleanToObject.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncBooleanToObject.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(this.argA);
        }

        public String toString() {
            return NodeFuncBooleanToObject.this.stringFunction.apply(this.argA.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncBooleanToObject.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncBooleanToObject<R>.FuncBooleanToObject other = (FuncBooleanToObject)obj;
                return Objects.equals(this.argA, other.argA);
            } else {
                return false;
            }
        }
    }
}
