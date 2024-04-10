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

public class NodeFuncLongLongToObject<R> extends NodeFuncBase implements INodeFunc.INodeFuncObject<R> {
    public final IFuncLongLongToObject<R> function;
    private final StringFunctionTri stringFunction;
    private final Class<R> returnType;

    public NodeFuncLongLongToObject(String name, Class<R> returnType, IFuncLongLongToObject<R> function) {
        this(returnType, function, (a, b) -> {
            return "[ long, long -> " + NodeTypes.getName(returnType) + " ] " + name + "(" + a + ", " + b + ")";
        });
    }

    public NodeFuncLongLongToObject(Class<R> returnType, IFuncLongLongToObject<R> function, StringFunctionTri stringFunction) {
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

    public NodeFuncLongLongToObject<R> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeObject<R> getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeLong b = stack.popLong();
        IExpressionNode.INodeLong a = stack.popLong();
        return this.create(a, b);
    }

    public NodeFuncLongLongToObject<R>.FuncLongLongToObject create(IExpressionNode.INodeLong argA, IExpressionNode.INodeLong argB) {
        return new FuncLongLongToObject(argA, argB);
    }

    @FunctionalInterface
    public interface IFuncLongLongToObject<R> {
        R apply(long var1, long var3);
    }

    public class FuncLongLongToObject implements IExpressionNode.INodeObject<R>, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeLong argA;
        public final IExpressionNode.INodeLong argB;

        public FuncLongLongToObject(IExpressionNode.INodeLong argA, IExpressionNode.INodeLong argB) {
            this.argA = argA;
            this.argB = argB;
        }

        public Class<R> getType() {
            return NodeFuncLongLongToObject.this.returnType;
        }

        public R evaluate() {
            return NodeFuncLongLongToObject.this.function.apply(this.argA.evaluate(), this.argB.evaluate());
        }

        public IExpressionNode.INodeObject<R> inline() {
            return !NodeFuncLongLongToObject.this.canInline ? (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncLongLongToObject.this.new FuncLongLongToObject(a, b);
            }, (a, b) -> {
                return NodeFuncLongLongToObject.this.new FuncLongLongToObject(a, b);
            }) : (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncLongLongToObject.this.new FuncLongLongToObject(a, b);
            }, (a, b) -> {
                return new NodeConstantObject(NodeFuncLongLongToObject.this.returnType, NodeFuncLongLongToObject.this.function.apply(a.evaluate(), b.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncLongLongToObject.this.canInline) {
                if (NodeFuncLongLongToObject.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncLongLongToObject.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB});
        }

        public String toString() {
            return NodeFuncLongLongToObject.this.stringFunction.apply(this.argA.toString(), this.argB.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncLongLongToObject.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncLongLongToObject<R>.FuncLongLongToObject other = (FuncLongLongToObject)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB);
            } else {
                return false;
            }
        }
    }
}
