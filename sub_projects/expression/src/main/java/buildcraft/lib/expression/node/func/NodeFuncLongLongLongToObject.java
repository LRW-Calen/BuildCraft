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

public class NodeFuncLongLongLongToObject<R> extends NodeFuncBase implements INodeFunc.INodeFuncObject<R> {
    public final IFuncLongLongLongToObject<R> function;
    private final StringFunctionQuad stringFunction;
    private final Class<R> returnType;

    public NodeFuncLongLongLongToObject(String name, Class<R> returnType, IFuncLongLongLongToObject<R> function) {
        this(returnType, function, (a, b, c) -> {
            return "[ long, long, long -> " + NodeTypes.getName(returnType) + " ] " + name + "(" + a + ", " + b + ", " + c + ")";
        });
    }

    public NodeFuncLongLongLongToObject(Class<R> returnType, IFuncLongLongLongToObject<R> function, StringFunctionQuad stringFunction) {
        this.returnType = returnType;
        this.function = function;
        this.stringFunction = stringFunction;
    }

    public Class<R> getType() {
        return this.returnType;
    }

    public String toString() {
        return this.stringFunction.apply("{A}", "{B}", "{C}");
    }

    public NodeFuncLongLongLongToObject<R> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeObject<R> getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeLong c = stack.popLong();
        IExpressionNode.INodeLong b = stack.popLong();
        IExpressionNode.INodeLong a = stack.popLong();
        return this.create(a, b, c);
    }

    public NodeFuncLongLongLongToObject<R>.FuncLongLongLongToObject create(IExpressionNode.INodeLong argA, IExpressionNode.INodeLong argB, IExpressionNode.INodeLong argC) {
        return new FuncLongLongLongToObject(argA, argB, argC);
    }

    @FunctionalInterface
    public interface IFuncLongLongLongToObject<R> {
        R apply(long var1, long var3, long var5);
    }

    public class FuncLongLongLongToObject implements IExpressionNode.INodeObject<R>, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeLong argA;
        public final IExpressionNode.INodeLong argB;
        public final IExpressionNode.INodeLong argC;

        public FuncLongLongLongToObject(IExpressionNode.INodeLong argA, IExpressionNode.INodeLong argB, IExpressionNode.INodeLong argC) {
            this.argA = argA;
            this.argB = argB;
            this.argC = argC;
        }

        public Class<R> getType() {
            return NodeFuncLongLongLongToObject.this.returnType;
        }

        public R evaluate() {
            return NodeFuncLongLongLongToObject.this.function.apply(this.argA.evaluate(), this.argB.evaluate(), this.argC.evaluate());
        }

        public IExpressionNode.INodeObject<R> inline() {
            return !NodeFuncLongLongLongToObject.this.canInline ? (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, (a, b, c) -> {
                return NodeFuncLongLongLongToObject.this.new FuncLongLongLongToObject(a, b, c);
            }, (a, b, c) -> {
                return NodeFuncLongLongLongToObject.this.new FuncLongLongLongToObject(a, b, c);
            }) : (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, (a, b, c) -> {
                return NodeFuncLongLongLongToObject.this.new FuncLongLongLongToObject(a, b, c);
            }, (a, b, c) -> {
                return new NodeConstantObject(NodeFuncLongLongLongToObject.this.returnType, NodeFuncLongLongLongToObject.this.function.apply(a.evaluate(), b.evaluate(), c.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncLongLongLongToObject.this.canInline) {
                if (NodeFuncLongLongLongToObject.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncLongLongLongToObject.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB, this.argC});
        }

        public String toString() {
            return NodeFuncLongLongLongToObject.this.stringFunction.apply(this.argA.toString(), this.argB.toString(), this.argC.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncLongLongLongToObject.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB, this.argC});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncLongLongLongToObject<R>.FuncLongLongLongToObject other = (FuncLongLongLongToObject)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB) && Objects.equals(this.argC, other.argC);
            } else {
                return false;
            }
        }
    }
}
