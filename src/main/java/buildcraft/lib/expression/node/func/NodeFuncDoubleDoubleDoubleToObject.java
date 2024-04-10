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

public class NodeFuncDoubleDoubleDoubleToObject<R> extends NodeFuncBase implements INodeFunc.INodeFuncObject<R> {
    public final IFuncDoubleDoubleDoubleToObject<R> function;
    private final StringFunctionQuad stringFunction;
    private final Class<R> returnType;

    public NodeFuncDoubleDoubleDoubleToObject(String name, Class<R> returnType, IFuncDoubleDoubleDoubleToObject<R> function) {
        this(returnType, function, (a, b, c) -> {
            return "[ double, double, double -> " + NodeTypes.getName(returnType) + " ] " + name + "(" + a + ", " + b + ", " + c + ")";
        });
    }

    public NodeFuncDoubleDoubleDoubleToObject(Class<R> returnType, IFuncDoubleDoubleDoubleToObject<R> function, StringFunctionQuad stringFunction) {
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

    public NodeFuncDoubleDoubleDoubleToObject<R> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeObject<R> getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeDouble c = stack.popDouble();
        IExpressionNode.INodeDouble b = stack.popDouble();
        IExpressionNode.INodeDouble a = stack.popDouble();
        return this.create(a, b, c);
    }

    public NodeFuncDoubleDoubleDoubleToObject<R>.FuncDoubleDoubleDoubleToObject create(IExpressionNode.INodeDouble argA, IExpressionNode.INodeDouble argB, IExpressionNode.INodeDouble argC) {
        return new FuncDoubleDoubleDoubleToObject(argA, argB, argC);
    }

    @FunctionalInterface
    public interface IFuncDoubleDoubleDoubleToObject<R> {
        R apply(double var1, double var3, double var5);
    }

    public class FuncDoubleDoubleDoubleToObject implements IExpressionNode.INodeObject<R>, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeDouble argA;
        public final IExpressionNode.INodeDouble argB;
        public final IExpressionNode.INodeDouble argC;

        public FuncDoubleDoubleDoubleToObject(IExpressionNode.INodeDouble argA, IExpressionNode.INodeDouble argB, IExpressionNode.INodeDouble argC) {
            this.argA = argA;
            this.argB = argB;
            this.argC = argC;
        }

        public Class<R> getType() {
            return NodeFuncDoubleDoubleDoubleToObject.this.returnType;
        }

        public R evaluate() {
            return NodeFuncDoubleDoubleDoubleToObject.this.function.apply(this.argA.evaluate(), this.argB.evaluate(), this.argC.evaluate());
        }

        public IExpressionNode.INodeObject<R> inline() {
            return !NodeFuncDoubleDoubleDoubleToObject.this.canInline ? (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, (a, b, c) -> {
                return NodeFuncDoubleDoubleDoubleToObject.this.new FuncDoubleDoubleDoubleToObject(a, b, c);
            }, (a, b, c) -> {
                return NodeFuncDoubleDoubleDoubleToObject.this.new FuncDoubleDoubleDoubleToObject(a, b, c);
            }) : (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, (a, b, c) -> {
                return NodeFuncDoubleDoubleDoubleToObject.this.new FuncDoubleDoubleDoubleToObject(a, b, c);
            }, (a, b, c) -> {
                return new NodeConstantObject(NodeFuncDoubleDoubleDoubleToObject.this.returnType, NodeFuncDoubleDoubleDoubleToObject.this.function.apply(a.evaluate(), b.evaluate(), c.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncDoubleDoubleDoubleToObject.this.canInline) {
                if (NodeFuncDoubleDoubleDoubleToObject.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncDoubleDoubleDoubleToObject.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB, this.argC});
        }

        public String toString() {
            return NodeFuncDoubleDoubleDoubleToObject.this.stringFunction.apply(this.argA.toString(), this.argB.toString(), this.argC.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncDoubleDoubleDoubleToObject.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB, this.argC});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncDoubleDoubleDoubleToObject<R>.FuncDoubleDoubleDoubleToObject other = (FuncDoubleDoubleDoubleToObject)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB) && Objects.equals(this.argC, other.argC);
            } else {
                return false;
            }
        }
    }
}
