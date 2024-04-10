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

public class NodeFuncObjectBooleanToObject<A, R> extends NodeFuncBase implements INodeFunc.INodeFuncObject<R> {
    public final IFuncObjectBooleanToObject<A, R> function;
    private final StringFunctionTri stringFunction;
    private final Class<A> argTypeA;
    private final Class<R> returnType;

    public NodeFuncObjectBooleanToObject(String name, Class<A> argTypeA, Class<R> returnType, IFuncObjectBooleanToObject<A, R> function) {
        this(argTypeA, returnType, function, (a, b) -> {
            return "[ " + NodeTypes.getName(argTypeA) + ", boolean -> " + NodeTypes.getName(returnType) + " ] " + name + "(" + a + ", " + b + ")";
        });
    }

    public NodeFuncObjectBooleanToObject(Class<A> argTypeA, Class<R> returnType, IFuncObjectBooleanToObject<A, R> function, StringFunctionTri stringFunction) {
        this.argTypeA = argTypeA;
        this.returnType = returnType;
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public Class<R> getType() {
        return this.returnType;
    }

    public String toString() {
        return this.stringFunction.apply("{A}", "{B}");
    }

    public NodeFuncObjectBooleanToObject<A, R> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeObject<R> getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeBoolean b = stack.popBoolean();
        IExpressionNode.INodeObject<A> a = stack.popObject(this.argTypeA);
        return this.create(a, b);
    }

    public NodeFuncObjectBooleanToObject<A, R>.FuncObjectBooleanToObject create(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeBoolean argB) {
        return new FuncObjectBooleanToObject(argA, argB);
    }

    @FunctionalInterface
    public interface IFuncObjectBooleanToObject<A, R> {
        R apply(A var1, boolean var2);
    }

    public class FuncObjectBooleanToObject implements IExpressionNode.INodeObject<R>, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeObject<A> argA;
        public final IExpressionNode.INodeBoolean argB;

        public FuncObjectBooleanToObject(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeBoolean argB) {
            this.argA = argA;
            this.argB = argB;
        }

        public Class<R> getType() {
            return NodeFuncObjectBooleanToObject.this.returnType;
        }

        public R evaluate() {
            return NodeFuncObjectBooleanToObject.this.function.apply(this.argA.evaluate(), this.argB.evaluate());
        }

        public IExpressionNode.INodeObject<R> inline() {
            return !NodeFuncObjectBooleanToObject.this.canInline ? (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncObjectBooleanToObject.this.new FuncObjectBooleanToObject(a, b);
            }, (a, b) -> {
                return NodeFuncObjectBooleanToObject.this.new FuncObjectBooleanToObject(a, b);
            }) : (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncObjectBooleanToObject.this.new FuncObjectBooleanToObject(a, b);
            }, (a, b) -> {
                return new NodeConstantObject(NodeFuncObjectBooleanToObject.this.returnType, NodeFuncObjectBooleanToObject.this.function.apply(a.evaluate(), b.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncObjectBooleanToObject.this.canInline) {
                if (NodeFuncObjectBooleanToObject.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncObjectBooleanToObject.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB});
        }

        public String toString() {
            return NodeFuncObjectBooleanToObject.this.stringFunction.apply(this.argA.toString(), this.argB.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncObjectBooleanToObject.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncObjectBooleanToObject<A, R>.FuncObjectBooleanToObject other = (FuncObjectBooleanToObject)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB);
            } else {
                return false;
            }
        }
    }
}
