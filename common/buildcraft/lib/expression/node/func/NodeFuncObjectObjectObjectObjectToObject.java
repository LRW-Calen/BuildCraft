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

public class NodeFuncObjectObjectObjectObjectToObject<A, B, C, D, R> extends NodeFuncBase implements INodeFunc.INodeFuncObject<R> {
    public final IFuncObjectObjectObjectObjectToObject<A, B, C, D, R> function;
    private final StringFunctionPenta stringFunction;
    private final Class<A> argTypeA;
    private final Class<B> argTypeB;
    private final Class<C> argTypeC;
    private final Class<D> argTypeD;
    private final Class<R> returnType;

    public NodeFuncObjectObjectObjectObjectToObject(String name, Class<A> argTypeA, Class<B> argTypeB, Class<C> argTypeC, Class<D> argTypeD, Class<R> returnType, IFuncObjectObjectObjectObjectToObject<A, B, C, D, R> function) {
        this(argTypeA, argTypeB, argTypeC, argTypeD, returnType, function, (a, b, c, d) -> {
            return "[ " + NodeTypes.getName(argTypeA) + ", " + NodeTypes.getName(argTypeB) + ", " + NodeTypes.getName(argTypeC) + ", " + NodeTypes.getName(argTypeD) + " -> " + NodeTypes.getName(returnType) + " ] " + name + "(" + a + ", " + b + ", " + c + ", " + d + ")";
        });
    }

    public NodeFuncObjectObjectObjectObjectToObject(Class<A> argTypeA, Class<B> argTypeB, Class<C> argTypeC, Class<D> argTypeD, Class<R> returnType, IFuncObjectObjectObjectObjectToObject<A, B, C, D, R> function, StringFunctionPenta stringFunction) {
        this.argTypeA = argTypeA;
        this.argTypeB = argTypeB;
        this.argTypeC = argTypeC;
        this.argTypeD = argTypeD;
        this.returnType = returnType;
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public Class<R> getType() {
        return this.returnType;
    }

    public String toString() {
        return this.stringFunction.apply("{A}", "{B}", "{C}", "{D}");
    }

    public NodeFuncObjectObjectObjectObjectToObject<A, B, C, D, R> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeObject<R> getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeObject<D> d = stack.popObject(this.argTypeD);
        IExpressionNode.INodeObject<C> c = stack.popObject(this.argTypeC);
        IExpressionNode.INodeObject<B> b = stack.popObject(this.argTypeB);
        IExpressionNode.INodeObject<A> a = stack.popObject(this.argTypeA);
        return this.create(a, b, c, d);
    }

    public NodeFuncObjectObjectObjectObjectToObject<A, B, C, D, R>.FuncObjectObjectObjectObjectToObject create(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeObject<B> argB, IExpressionNode.INodeObject<C> argC, IExpressionNode.INodeObject<D> argD) {
        return new FuncObjectObjectObjectObjectToObject(argA, argB, argC, argD);
    }

    @FunctionalInterface
    public interface IFuncObjectObjectObjectObjectToObject<A, B, C, D, R> {
        R apply(A var1, B var2, C var3, D var4);
    }

    public class FuncObjectObjectObjectObjectToObject implements IExpressionNode.INodeObject<R>, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeObject<A> argA;
        public final IExpressionNode.INodeObject<B> argB;
        public final IExpressionNode.INodeObject<C> argC;
        public final IExpressionNode.INodeObject<D> argD;

        public FuncObjectObjectObjectObjectToObject(IExpressionNode.INodeObject<A> argA, IExpressionNode.INodeObject<B> argB, IExpressionNode.INodeObject<C> argC, IExpressionNode.INodeObject<D> argD) {
            this.argA = argA;
            this.argB = argB;
            this.argC = argC;
            this.argD = argD;
        }

        public Class<R> getType() {
            return NodeFuncObjectObjectObjectObjectToObject.this.returnType;
        }

        public R evaluate() {
            return NodeFuncObjectObjectObjectObjectToObject.this.function.apply(this.argA.evaluate(), this.argB.evaluate(), this.argC.evaluate(), this.argD.evaluate());
        }

        public IExpressionNode.INodeObject<R> inline() {
            return !NodeFuncObjectObjectObjectObjectToObject.this.canInline ? (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, this.argD, (a, b, c, d) -> {
                return NodeFuncObjectObjectObjectObjectToObject.this.new FuncObjectObjectObjectObjectToObject(a, b, c, d);
            }, (a, b, c, d) -> {
                return NodeFuncObjectObjectObjectObjectToObject.this.new FuncObjectObjectObjectObjectToObject(a, b, c, d);
            }) : (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, this.argD, (a, b, c, d) -> {
                return NodeFuncObjectObjectObjectObjectToObject.this.new FuncObjectObjectObjectObjectToObject(a, b, c, d);
            }, (a, b, c, d) -> {
                return new NodeConstantObject(NodeFuncObjectObjectObjectObjectToObject.this.returnType, NodeFuncObjectObjectObjectObjectToObject.this.function.apply(a.evaluate(), b.evaluate(), c.evaluate(), d.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncObjectObjectObjectObjectToObject.this.canInline) {
                if (NodeFuncObjectObjectObjectObjectToObject.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncObjectObjectObjectObjectToObject.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB, this.argC, this.argD});
        }

        public String toString() {
            return NodeFuncObjectObjectObjectObjectToObject.this.stringFunction.apply(this.argA.toString(), this.argB.toString(), this.argC.toString(), this.argD.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncObjectObjectObjectObjectToObject.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB, this.argC, this.argD});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncObjectObjectObjectObjectToObject<A, B, C, D, R>.FuncObjectObjectObjectObjectToObject other = (FuncObjectObjectObjectObjectToObject)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB) && Objects.equals(this.argC, other.argC) && Objects.equals(this.argD, other.argD);
            } else {
                return false;
            }
        }
    }
}
