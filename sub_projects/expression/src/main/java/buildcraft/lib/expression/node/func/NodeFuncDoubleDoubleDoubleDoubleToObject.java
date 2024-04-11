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

public class NodeFuncDoubleDoubleDoubleDoubleToObject<R> extends NodeFuncBase implements INodeFunc.INodeFuncObject<R> {
    public final IFuncDoubleDoubleDoubleDoubleToObject<R> function;
    private final StringFunctionPenta stringFunction;
    private final Class<R> returnType;

    public NodeFuncDoubleDoubleDoubleDoubleToObject(String name, Class<R> returnType, IFuncDoubleDoubleDoubleDoubleToObject<R> function) {
        this(returnType, function, (a, b, c, d) -> {
            return "[ double, double, double, double -> " + NodeTypes.getName(returnType) + " ] " + name + "(" + a + ", " + b + ", " + c + ", " + d + ")";
        });
    }

    public NodeFuncDoubleDoubleDoubleDoubleToObject(Class<R> returnType, IFuncDoubleDoubleDoubleDoubleToObject<R> function, StringFunctionPenta stringFunction) {
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

    public NodeFuncDoubleDoubleDoubleDoubleToObject<R> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    public IExpressionNode.INodeObject<R> getNode(INodeStack stack) throws InvalidExpressionException {
        IExpressionNode.INodeDouble d = stack.popDouble();
        IExpressionNode.INodeDouble c = stack.popDouble();
        IExpressionNode.INodeDouble b = stack.popDouble();
        IExpressionNode.INodeDouble a = stack.popDouble();
        return this.create(a, b, c, d);
    }

    public NodeFuncDoubleDoubleDoubleDoubleToObject<R>.FuncDoubleDoubleDoubleDoubleToObject create(IExpressionNode.INodeDouble argA, IExpressionNode.INodeDouble argB, IExpressionNode.INodeDouble argC, IExpressionNode.INodeDouble argD) {
        return new FuncDoubleDoubleDoubleDoubleToObject(argA, argB, argC, argD);
    }

    @FunctionalInterface
    public interface IFuncDoubleDoubleDoubleDoubleToObject<R> {
        R apply(double var1, double var3, double var5, double var7);
    }

    public class FuncDoubleDoubleDoubleDoubleToObject implements IExpressionNode.INodeObject<R>, IDependantNode, NodeFuncBase.IFunctionNode {
        public final IExpressionNode.INodeDouble argA;
        public final IExpressionNode.INodeDouble argB;
        public final IExpressionNode.INodeDouble argC;
        public final IExpressionNode.INodeDouble argD;

        public FuncDoubleDoubleDoubleDoubleToObject(IExpressionNode.INodeDouble argA, IExpressionNode.INodeDouble argB, IExpressionNode.INodeDouble argC, IExpressionNode.INodeDouble argD) {
            this.argA = argA;
            this.argB = argB;
            this.argC = argC;
            this.argD = argD;
        }

        public Class<R> getType() {
            return NodeFuncDoubleDoubleDoubleDoubleToObject.this.returnType;
        }

        public R evaluate() {
            return NodeFuncDoubleDoubleDoubleDoubleToObject.this.function.apply(this.argA.evaluate(), this.argB.evaluate(), this.argC.evaluate(), this.argD.evaluate());
        }

        public IExpressionNode.INodeObject<R> inline() {
            return !NodeFuncDoubleDoubleDoubleDoubleToObject.this.canInline ? (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, this.argD, (a, b, c, d) -> {
                return NodeFuncDoubleDoubleDoubleDoubleToObject.this.new FuncDoubleDoubleDoubleDoubleToObject(a, b, c, d);
            }, (a, b, c, d) -> {
                return NodeFuncDoubleDoubleDoubleDoubleToObject.this.new FuncDoubleDoubleDoubleDoubleToObject(a, b, c, d);
            }) : (IExpressionNode.INodeObject)NodeInliningHelper.tryInline(this, this.argA, this.argB, this.argC, this.argD, (a, b, c, d) -> {
                return NodeFuncDoubleDoubleDoubleDoubleToObject.this.new FuncDoubleDoubleDoubleDoubleToObject(a, b, c, d);
            }, (a, b, c, d) -> {
                return new NodeConstantObject(NodeFuncDoubleDoubleDoubleDoubleToObject.this.returnType, NodeFuncDoubleDoubleDoubleDoubleToObject.this.function.apply(a.evaluate(), b.evaluate(), c.evaluate(), d.evaluate()));
            });
        }

        public void visitDependants(IDependancyVisitor visitor) {
            if (!NodeFuncDoubleDoubleDoubleDoubleToObject.this.canInline) {
                if (NodeFuncDoubleDoubleDoubleDoubleToObject.this.function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode)NodeFuncDoubleDoubleDoubleDoubleToObject.this.function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }

            visitor.dependOn(new IExpressionNode[]{this.argA, this.argB, this.argC, this.argD});
        }

        public String toString() {
            return NodeFuncDoubleDoubleDoubleDoubleToObject.this.stringFunction.apply(this.argA.toString(), this.argB.toString(), this.argC.toString(), this.argD.toString());
        }

        public NodeFuncBase getFunction() {
            return NodeFuncDoubleDoubleDoubleDoubleToObject.this;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{this.argA, this.argB, this.argC, this.argD});
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
                NodeFuncDoubleDoubleDoubleDoubleToObject<R>.FuncDoubleDoubleDoubleDoubleToObject other = (FuncDoubleDoubleDoubleDoubleToObject)obj;
                return Objects.equals(this.argA, other.argA) && Objects.equals(this.argB, other.argB) && Objects.equals(this.argC, other.argC) && Objects.equals(this.argD, other.argD);
            } else {
                return false;
            }
        }
    }
}
