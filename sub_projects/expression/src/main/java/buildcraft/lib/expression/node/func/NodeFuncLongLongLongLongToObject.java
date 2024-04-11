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

public class NodeFuncLongLongLongLongToObject<R> extends NodeFuncBase implements INodeFunc.INodeFuncObject<R> {
    public final IFuncLongLongLongLongToObject<R> function;
    private final StringFunctionPenta stringFunction;
    private final Class<R> returnType;

    public NodeFuncLongLongLongLongToObject(String name, Class<R> returnType, IFuncLongLongLongLongToObject<R> function) {
        this(returnType, function, (a, b, c, d) -> {
            return "[ long, long, long, long -> " + NodeTypes.getName(returnType) + " ] " + name + "(" + a + ", " + b + ", " + c + ", " + d + ")";
        });
    }

    public NodeFuncLongLongLongLongToObject(Class<R> returnType, IFuncLongLongLongLongToObject<R> function, StringFunctionPenta stringFunction) {
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

    public NodeFuncLongLongLongLongToObject<R> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeObject<R> getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeLong d = stack.popLong();
        IExpressionNode.INodeLong c = stack.popLong();
        IExpressionNode.INodeLong b = stack.popLong();
        IExpressionNode.INodeLong a = stack.popLong();
        return this.create(a, b, c, d);
    }

    public NodeFuncLongLongLongLongToObject<R>.FuncLongLongLongLongToObject create(IExpressionNode.INodeLong argA, IExpressionNode.INodeLong argB, IExpressionNode.INodeLong argC, IExpressionNode.INodeLong argD) {
        return new FuncLongLongLongLongToObject(argA, argB, argC, argD);
    }

    @FunctionalInterface
    public interface IFuncLongLongLongLongToObject<R> {
        R apply(long var1, long var3, long var5, long var7);
    }

    public class FuncLongLongLongLongToObject implements IExpressionNode.INodeObject<R>, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeLong argA;
        public final IExpressionNode.INodeLong argB;
        public final IExpressionNode.INodeLong argC;
        public final IExpressionNode.INodeLong argD;

        public FuncLongLongLongLongToObject(IExpressionNode.INodeLong argA, IExpressionNode.INodeLong argB, IExpressionNode.INodeLong argC, IExpressionNode.INodeLong argD) {
            this.argA = argA;
            this.argB = argB;
            this.argC = argC;
            this.argD = argD;
        }

        public Class<R> getType() {
            return NodeFuncLongLongLongLongToObject.this.returnType;
        }

        public R evaluate() {
            return NodeFuncLongLongLongLongToObject.this.function.apply(this.argA.evaluate(), this.argB.evaluate(), this.argC.evaluate(), this.argD.evaluate());
        }

        public IExpressionNode.INodeObject<R> inline() {
            return !NodeFuncLongLongLongLongToObject.this.canInline ? (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, this.argD, (a, b, c, d) -> {
                return NodeFuncLongLongLongLongToObject.this.new FuncLongLongLongLongToObject(a, b, c, d);
            }, (a, b, c, d) -> {
                return NodeFuncLongLongLongLongToObject.this.new FuncLongLongLongLongToObject(a, b, c, d);
            }) : (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, this.argD, (a, b, c, d) -> {
                return NodeFuncLongLongLongLongToObject.this.new FuncLongLongLongLongToObject(a, b, c, d);
            }, (a, b, c, d) -> {
                return new NodeConstantObject(NodeFuncLongLongLongLongToObject.this.returnType, NodeFuncLongLongLongLongToObject.this.function.apply(a.evaluate(), b.evaluate(), c.evaluate(), d.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncLongLongLongLongToObject.this.canInline) {
                if (NodeFuncLongLongLongLongToObject.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncLongLongLongLongToObject.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB, this.argC, this.argD});
        }

        public String toString() {
            return NodeFuncLongLongLongLongToObject.this.stringFunction.apply(this.argA.toString(), this.argB.toString(), this.argC.toString(), this.argD.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncLongLongLongLongToObject.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB, this.argC, this.argD});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncLongLongLongLongToObject<R>.FuncLongLongLongLongToObject other = (FuncLongLongLongLongToObject)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB) && Objects.equals(this.argC, other.argC) && Objects.equals(this.argD, other.argD);
            } else {
                return false;
            }
        }
    }
}
