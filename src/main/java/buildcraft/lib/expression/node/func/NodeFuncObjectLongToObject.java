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

public class NodeFuncObjectLongToObject<A, R> extends NodeFuncBase implements INodeFunc.INodeFuncObject<R> {
    public final IFuncObjectLongToObject<A, R> function;
    private final StringFunctionTri stringFunction;
    private final Class<A> argTypeA;
    private final Class<R> returnType;

    public NodeFuncObjectLongToObject(String name, Class<A> argTypeA, Class<R> returnType, IFuncObjectLongToObject<A, R> function) {
        this(argTypeA, returnType, function, (a, b) -> {
            return "[ " + NodeTypes.getName(argTypeA) + ", long -> " + NodeTypes.getName(returnType) + " ] " + name + "(" + a + ", " + b + ")";
        });
    }

    public NodeFuncObjectLongToObject(Class<A> argTypeA, Class<R> returnType, IFuncObjectLongToObject<A, R> function, StringFunctionTri stringFunction) {
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

    public NodeFuncObjectLongToObject<A, R> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeObject<R> getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeLong b = stack.popLong();
        IExpressionNode.INodeObject<A> a = stack.popObject(this.argTypeA);
        return this.create(a, b);
    }

    public NodeFuncObjectLongToObject<A, R>.FuncObjectLongToObject create(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeLong argB) {
        return new FuncObjectLongToObject(argA, argB);
    }

    @FunctionalInterface
    public interface IFuncObjectLongToObject<A, R> {
        R apply(A var1, long var2);
    }

    public class FuncObjectLongToObject implements IExpressionNode.INodeObject<R>, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeObject<A> argA;
        public final IExpressionNode.INodeLong argB;

        public FuncObjectLongToObject(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeLong argB) {
            this.argA = argA;
            this.argB = argB;
        }

        public Class<R> getType() {
            return NodeFuncObjectLongToObject.this.returnType;
        }

        public R evaluate() {
            return NodeFuncObjectLongToObject.this.function.apply(this.argA.evaluate(), this.argB.evaluate());
        }

        public IExpressionNode.INodeObject<R> inline() {
            return !NodeFuncObjectLongToObject.this.canInline ? (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncObjectLongToObject.this.new FuncObjectLongToObject(a, b);
            }, (a, b) -> {
                return NodeFuncObjectLongToObject.this.new FuncObjectLongToObject(a, b);
            }) : (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncObjectLongToObject.this.new FuncObjectLongToObject(a, b);
            }, (a, b) -> {
                return new NodeConstantObject(NodeFuncObjectLongToObject.this.returnType, NodeFuncObjectLongToObject.this.function.apply(a.evaluate(), b.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncObjectLongToObject.this.canInline) {
                if (NodeFuncObjectLongToObject.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncObjectLongToObject.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB});
        }

        public String toString() {
            return NodeFuncObjectLongToObject.this.stringFunction.apply(this.argA.toString(), this.argB.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncObjectLongToObject.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncObjectLongToObject<A, R>.FuncObjectLongToObject other = (FuncObjectLongToObject)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB);
            } else {
                return false;
            }
        }
    }
}
