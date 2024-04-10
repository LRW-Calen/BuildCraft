//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package buildcraft.lib.expression.node.func;

import buildcraft.lib.expression.NodeInliningHelper;
import buildcraft.lib.expression.api.*;
import buildcraft.lib.expression.node.value.NodeConstantObject;

import java.util.Objects;

public class NodeFuncDoubleDoubleToObject<R> extends NodeFuncBase implements INodeFunc.INodeFuncObject<R> {
    public final IFuncDoubleDoubleToObject<R> function;
    private final StringFunctionTri stringFunction;
    private final Class<R> returnType;

    public NodeFuncDoubleDoubleToObject(String name, Class<R> returnType, IFuncDoubleDoubleToObject<R> function) {
        this(returnType, function, (a, b) -> {
            return "[ double, double -> " + NodeTypes.getName(returnType) + " ] " + name + "(" + a + ", " + b + ")";
        });
    }

    public NodeFuncDoubleDoubleToObject(Class<R> returnType, IFuncDoubleDoubleToObject<R> function, StringFunctionTri stringFunction) {
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

    public NodeFuncDoubleDoubleToObject<R> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeObject<R> getNode(INodeStack stack) throws InvalidExpressionException
    {
        IExpressionNode.INodeDouble b = stack.popDouble();
        IExpressionNode.INodeDouble a = stack.popDouble();
        return this.create(a, b);
    }

    public NodeFuncDoubleDoubleToObject<R>.FuncDoubleDoubleToObject create(IExpressionNode.INodeDouble argA, IExpressionNode.INodeDouble argB) {
        return new FuncDoubleDoubleToObject(argA, argB);
    }

    @FunctionalInterface
    public interface IFuncDoubleDoubleToObject<R> {
        R apply(double var1, double var3);
    }

    public class FuncDoubleDoubleToObject implements IExpressionNode.INodeObject<R>, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeDouble argA;
        public final IExpressionNode.INodeDouble argB;

        public FuncDoubleDoubleToObject(IExpressionNode.INodeDouble argA, IExpressionNode.INodeDouble argB) {
            this.argA = argA;
            this.argB = argB;
        }

        public Class<R> getType() {
            return NodeFuncDoubleDoubleToObject.this.returnType;
        }

        public R evaluate() {
            return NodeFuncDoubleDoubleToObject.this.function.apply(this.argA.evaluate(), this.argB.evaluate());
        }

        public IExpressionNode.INodeObject<R> inline() {
            return !NodeFuncDoubleDoubleToObject.this.canInline ? (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncDoubleDoubleToObject.this.new FuncDoubleDoubleToObject(a, b);
            }, (a, b) -> {
                return NodeFuncDoubleDoubleToObject.this.new FuncDoubleDoubleToObject(a, b);
            }) : (IExpressionNode.INodeObject) NodeInliningHelper.tryInline(this, this.argA, this.argB, (a, b) -> {
                return NodeFuncDoubleDoubleToObject.this.new FuncDoubleDoubleToObject(a, b);
            }, (a, b) -> {
                return new NodeConstantObject(NodeFuncDoubleDoubleToObject.this.returnType, NodeFuncDoubleDoubleToObject.this.function.apply(a.evaluate(), b.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncDoubleDoubleToObject.this.canInline) {
                if (NodeFuncDoubleDoubleToObject.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncDoubleDoubleToObject.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB});
        }

        public String toString() {
            return NodeFuncDoubleDoubleToObject.this.stringFunction.apply(this.argA.toString(), this.argB.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncDoubleDoubleToObject.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncDoubleDoubleToObject<R>.FuncDoubleDoubleToObject other = (FuncDoubleDoubleToObject)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB);
            } else {
                return false;
            }
        }
    }
}
