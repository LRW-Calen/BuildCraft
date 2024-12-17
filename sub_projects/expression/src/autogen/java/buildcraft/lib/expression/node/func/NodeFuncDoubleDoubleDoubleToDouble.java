/*
 * Copyright (c) 2017 SpaceToad and the BuildCraft team
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at https://mozilla.org/MPL/2.0/
 */

package buildcraft.lib.expression.node.func;

import java.util.Objects;

import buildcraft.lib.expression.NodeInliningHelper;
import buildcraft.lib.expression.api.IDependantNode;
import buildcraft.lib.expression.api.IDependancyVisitor;
import buildcraft.lib.expression.api.IExpressionNode.INodeBoolean;
import buildcraft.lib.expression.api.IExpressionNode.INodeDouble;
import buildcraft.lib.expression.api.IExpressionNode.INodeLong;
import buildcraft.lib.expression.api.IExpressionNode.INodeObject;
import buildcraft.lib.expression.api.INodeFunc.INodeFuncDouble;
import buildcraft.lib.expression.api.INodeStack;
import buildcraft.lib.expression.api.InvalidExpressionException;
import buildcraft.lib.expression.api.NodeTypes;
import buildcraft.lib.expression.node.func.StringFunctionQuad;
import buildcraft.lib.expression.node.func.NodeFuncBase;
import buildcraft.lib.expression.node.func.NodeFuncBase.IFunctionNode;
import buildcraft.lib.expression.node.value.NodeConstantDouble;

// AUTO_GENERATED FILE, DO NOT EDIT MANUALLY!
public class NodeFuncDoubleDoubleDoubleToDouble extends NodeFuncBase implements INodeFuncDouble {

    public final IFuncDoubleDoubleDoubleToDouble function;
    private final StringFunctionQuad stringFunction;

    public NodeFuncDoubleDoubleDoubleToDouble(String name, IFuncDoubleDoubleDoubleToDouble function) {
        this(function, (a, b, c) -> "[ double, double, double -> double ] " + name + "(" + a + ", " + b + ", " + c +  ")");
    }

    public NodeFuncDoubleDoubleDoubleToDouble(IFuncDoubleDoubleDoubleToDouble function, StringFunctionQuad stringFunction) {

        this.function = function;
        this.stringFunction = stringFunction;
    }

    @Override
    public String toString() {
        return stringFunction.apply("{A}", "{B}", "{C}");
    }

    @Override
    public NodeFuncDoubleDoubleDoubleToDouble setNeverInline() {
        super.setNeverInline();
        return this;
    }

    @Override
    public INodeDouble getNode(INodeStack stack) throws InvalidExpressionException {

        INodeDouble c = stack.popDouble();
        INodeDouble b = stack.popDouble();
        INodeDouble a = stack.popDouble();

        return create(a, b, c);
    }

    /** Shortcut to create a new {@link FuncDoubleDoubleDoubleToDouble} without needing to create
     *  and populate an {@link INodeStack} to pass to {@link #getNode(INodeStack)}. */
    public FuncDoubleDoubleDoubleToDouble create(INodeDouble argA, INodeDouble argB, INodeDouble argC) {
        return new FuncDoubleDoubleDoubleToDouble(argA, argB, argC); 
    }

    public class FuncDoubleDoubleDoubleToDouble implements INodeDouble, IDependantNode, IFunctionNode {
        public final INodeDouble argA;
        public final INodeDouble argB;
        public final INodeDouble argC;

        public FuncDoubleDoubleDoubleToDouble(INodeDouble argA, INodeDouble argB, INodeDouble argC) {
            this.argA = argA;
            this.argB = argB;
            this.argC = argC;

        }

        @Override
        public double evaluate() {
            return function.apply(argA.evaluate(), argB.evaluate(), argC.evaluate());
        }

        @Override
        public INodeDouble inline() {
            if (!canInline) {
                // Note that we can still inline the arguments, just not *this* function
                return NodeInliningHelper.tryInline(this, argA, argB, argC,
                    (a, b, c) -> new FuncDoubleDoubleDoubleToDouble(a, b, c),
                    (a, b, c) -> new FuncDoubleDoubleDoubleToDouble(a, b, c)
                );
            }
            return NodeInliningHelper.tryInline(this, argA, argB, argC,
                (a, b, c) -> new FuncDoubleDoubleDoubleToDouble(a, b, c),
                (a, b, c) -> NodeConstantDouble.of(function.apply(a.evaluate(), b.evaluate(), c.evaluate()))
            );
        }

        @Override
        public void visitDependants(IDependancyVisitor visitor) {
            if (!canInline) {
                if (function instanceof IDependantNode) {
                    visitor.dependOn((IDependantNode) function);
                } else {
                    visitor.dependOnExplictly(this);
                }
            }
            visitor.dependOn(argA, argB, argC);
        }

        @Override
        public String toString() {
            return stringFunction.apply(argA.toString(), argB.toString(), argC.toString());
        }

        @Override
        public NodeFuncBase getFunction() {
            return NodeFuncDoubleDoubleDoubleToDouble.this;
        }

        @Override
        public int hashCode() {
            return Objects.hash(argA, argB, argC);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            FuncDoubleDoubleDoubleToDouble other = (FuncDoubleDoubleDoubleToDouble) obj;
            return Objects.equals(argA, other.argA) //
            &&Objects.equals(argB, other.argB) //
            &&Objects.equals(argC, other.argC);
        }
    }

    @FunctionalInterface
    public interface IFuncDoubleDoubleDoubleToDouble {
        double apply(double a, double b, double c);
    }
}
