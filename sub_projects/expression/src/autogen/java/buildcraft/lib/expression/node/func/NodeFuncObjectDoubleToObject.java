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
import buildcraft.lib.expression.api.INodeFunc.INodeFuncObject;
import buildcraft.lib.expression.api.INodeStack;
import buildcraft.lib.expression.api.InvalidExpressionException;
import buildcraft.lib.expression.api.NodeTypes;
import buildcraft.lib.expression.node.func.StringFunctionTri;
import buildcraft.lib.expression.node.func.NodeFuncBase;
import buildcraft.lib.expression.node.func.NodeFuncBase.IFunctionNode;
import buildcraft.lib.expression.node.value.NodeConstantObject;

// AUTO_GENERATED FILE, DO NOT EDIT MANUALLY!
public class NodeFuncObjectDoubleToObject<A, R> extends NodeFuncBase implements INodeFuncObject<R> {

    public final IFuncObjectDoubleToObject<A, R> function;
    private final StringFunctionTri stringFunction;
    private final Class<A> argTypeA;
    private final Class<R> returnType;

    public NodeFuncObjectDoubleToObject(String name, Class<A> argTypeA, Class<R> returnType, IFuncObjectDoubleToObject<A, R> function) {
        this(argTypeA, returnType, function, (a, b) -> "[ " + NodeTypes.getName(argTypeA) + ", double -> " + NodeTypes.getName(returnType) + " ] " + name + "(" + a + ", " + b +  ")");
    }

    public NodeFuncObjectDoubleToObject(Class<A> argTypeA, Class<R> returnType, IFuncObjectDoubleToObject<A, R> function, StringFunctionTri stringFunction) {
        this.argTypeA = argTypeA;
        this.returnType = returnType;

        this.function = function;
        this.stringFunction = stringFunction;
    }

    @Override
    public Class<R> getType() {
        return returnType;
    }

    @Override
    public String toString() {
        return stringFunction.apply("{A}", "{B}");
    }

    @Override
    public NodeFuncObjectDoubleToObject<A, R> setNeverInline() {
        super.setNeverInline();
        return this;
    }

    @Override
    public INodeObject<R> getNode(INodeStack stack) throws InvalidExpressionException {

        INodeDouble b = stack.popDouble();
        INodeObject<A> a = stack.popObject(argTypeA);

        return create(a, b);
    }

    /** Shortcut to create a new {@link FuncObjectDoubleToObject} without needing to create
     *  and populate an {@link INodeStack} to pass to {@link #getNode(INodeStack)}. */
    public FuncObjectDoubleToObject create(INodeObject<A> argA, INodeDouble argB) {
        return new FuncObjectDoubleToObject(argA, argB); 
    }

    public class FuncObjectDoubleToObject implements INodeObject<R>, IDependantNode, IFunctionNode {
        public final INodeObject<A> argA;
        public final INodeDouble argB;

        public FuncObjectDoubleToObject(INodeObject<A> argA, INodeDouble argB) {
            this.argA = argA;
            this.argB = argB;

        }

        @Override
        public Class<R> getType() {
            return returnType;
        }

        @Override
        public R evaluate() {
            return function.apply(argA.evaluate(), argB.evaluate());
        }

        @Override
        public INodeObject<R> inline() {
            if (!canInline) {
                // Note that we can still inline the arguments, just not *this* function
                return NodeInliningHelper.tryInline(this, argA, argB,
                    (a, b) -> new FuncObjectDoubleToObject(a, b),
                    (a, b) -> new FuncObjectDoubleToObject(a, b)
                );
            }
            return NodeInliningHelper.tryInline(this, argA, argB,
                (a, b) -> new FuncObjectDoubleToObject(a, b),
                (a, b) -> new NodeConstantObject<>(returnType, function.apply(a.evaluate(), b.evaluate()))
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
            visitor.dependOn(argA, argB);
        }

        @Override
        public String toString() {
            return stringFunction.apply(argA.toString(), argB.toString());
        }

        @Override
        public NodeFuncBase getFunction() {
            return NodeFuncObjectDoubleToObject.this;
        }

        @Override
        public int hashCode() {
            return Objects.hash(argA, argB);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            FuncObjectDoubleToObject other = (FuncObjectDoubleToObject) obj;
            return Objects.equals(argA, other.argA) //
            &&Objects.equals(argB, other.argB);
        }
    }

    @FunctionalInterface
    public interface IFuncObjectDoubleToObject<A, R> {
        R apply(A a, double b);
    }
}
