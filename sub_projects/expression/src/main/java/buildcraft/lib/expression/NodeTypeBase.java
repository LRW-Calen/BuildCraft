//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package buildcraft.lib.expression;

import buildcraft.lib.expression.node.func.NodeFuncBooleanToObject;
import buildcraft.lib.expression.node.func.NodeFuncDoubleDoubleDoubleDoubleToObject;
import buildcraft.lib.expression.node.func.NodeFuncDoubleDoubleDoubleToObject;
import buildcraft.lib.expression.node.func.NodeFuncDoubleDoubleToObject;
import buildcraft.lib.expression.node.func.NodeFuncDoubleToObject;
import buildcraft.lib.expression.node.func.NodeFuncLongLongLongLongToObject;
import buildcraft.lib.expression.node.func.NodeFuncLongLongLongToObject;
import buildcraft.lib.expression.node.func.NodeFuncLongLongToObject;
import buildcraft.lib.expression.node.func.NodeFuncLongToObject;
import buildcraft.lib.expression.node.func.NodeFuncObjectBooleanToObject;
import buildcraft.lib.expression.node.func.NodeFuncObjectLongLongToLong;
import buildcraft.lib.expression.node.func.NodeFuncObjectLongLongToObject;
import buildcraft.lib.expression.node.func.NodeFuncObjectLongToLong;
import buildcraft.lib.expression.node.func.NodeFuncObjectLongToObject;
import buildcraft.lib.expression.node.func.NodeFuncObjectObjectObjectObjectToObject;
import buildcraft.lib.expression.node.func.NodeFuncObjectObjectObjectToObject;
import buildcraft.lib.expression.node.func.NodeFuncObjectObjectToBoolean;
import buildcraft.lib.expression.node.func.NodeFuncObjectObjectToDouble;
import buildcraft.lib.expression.node.func.NodeFuncObjectObjectToLong;
import buildcraft.lib.expression.node.func.NodeFuncObjectObjectToObject;
import buildcraft.lib.expression.node.func.NodeFuncObjectToBoolean;
import buildcraft.lib.expression.node.func.NodeFuncObjectToDouble;
import buildcraft.lib.expression.node.func.NodeFuncObjectToLong;
import buildcraft.lib.expression.node.func.NodeFuncObjectToObject;
import buildcraft.lib.expression.node.func.StringFunctionBi;
import buildcraft.lib.expression.node.func.StringFunctionPenta;
import buildcraft.lib.expression.node.func.StringFunctionQuad;
import buildcraft.lib.expression.node.func.StringFunctionTri;

public abstract class NodeTypeBase<T> extends FunctionContext {
    public NodeTypeBase(String name) {
        super("Type: " + name);
    }

    protected abstract Class<T> getType();

    public NodeFuncObjectToLong<T> put_t_l(String fname, NodeFuncObjectToLong.IFuncObjectToLong<T> func) {
        return this.put_o_l(fname, this.getType(), func);
    }

    public NodeFuncObjectToLong<T> put_t_l(String fname, NodeFuncObjectToLong.IFuncObjectToLong<T> func, StringFunctionBi stringFunction) {
        return this.put_o_l(fname, this.getType(), func, stringFunction);
    }

    public NodeFuncObjectLongToLong<T> put_tl_l(String fname, NodeFuncObjectLongToLong.IFuncObjectLongToLong<T> func) {
        return this.put_ol_l(fname, this.getType(), func);
    }

    public NodeFuncObjectLongToLong<T> put_tl_l(String fname, NodeFuncObjectLongToLong.IFuncObjectLongToLong<T> func, StringFunctionTri stringFunction) {
        return this.put_ol_l(fname, this.getType(), func, stringFunction);
    }

    public NodeFuncObjectLongLongToLong<T> put_tll_l(String fname, NodeFuncObjectLongLongToLong.IFuncObjectLongLongToLong<T> func) {
        return this.put_oll_l(fname, this.getType(), func);
    }

    public NodeFuncObjectLongLongToLong<T> put_tll_l(String fname, NodeFuncObjectLongLongToLong.IFuncObjectLongLongToLong<T> func, StringFunctionQuad stringFunction) {
        return this.put_oll_l(fname, this.getType(), func, stringFunction);
    }

    public <A> NodeFuncObjectObjectToLong<A, T> put_to_l(String fname, Class<A> argTypeA, NodeFuncObjectObjectToLong.IFuncObjectObjectToLong<A, T> func) {
        return this.put_oo_l(fname, argTypeA, this.getType(), func);
    }

    public <A> NodeFuncObjectObjectToLong<A, T> put_to_l(String fname, Class<A> argTypeA, NodeFuncObjectObjectToLong.IFuncObjectObjectToLong<A, T> func, StringFunctionTri stringFunction) {
        return this.put_oo_l(fname, argTypeA, this.getType(), func, stringFunction);
    }

    public <B> NodeFuncObjectObjectToLong<T, B> put_ot_l(String fname, Class<B> argTypeB, NodeFuncObjectObjectToLong.IFuncObjectObjectToLong<T, B> func) {
        return this.put_oo_l(fname, this.getType(), argTypeB, func);
    }

    public <B> NodeFuncObjectObjectToLong<T, B> put_ot_l(String fname, Class<B> argTypeB, NodeFuncObjectObjectToLong.IFuncObjectObjectToLong<T, B> func, StringFunctionTri stringFunction) {
        return this.put_oo_l(fname, this.getType(), argTypeB, func, stringFunction);
    }

    public NodeFuncObjectObjectToLong<T, T> put_tt_l(String fname, NodeFuncObjectObjectToLong.IFuncObjectObjectToLong<T, T> func) {
        return this.put_oo_l(fname, this.getType(), this.getType(), func);
    }

    public NodeFuncObjectObjectToLong<T, T> put_tt_l(String fname, NodeFuncObjectObjectToLong.IFuncObjectObjectToLong<T, T> func, StringFunctionTri stringFunction) {
        return this.put_oo_l(fname, this.getType(), this.getType(), func, stringFunction);
    }

    public NodeFuncObjectToDouble<T> put_t_d(String fname, NodeFuncObjectToDouble.IFuncObjectToDouble<T> func) {
        return this.put_o_d(fname, this.getType(), func);
    }

    public NodeFuncObjectToDouble<T> put_t_d(String fname, NodeFuncObjectToDouble.IFuncObjectToDouble<T> func, StringFunctionBi stringFunction) {
        return this.put_o_d(fname, this.getType(), func, stringFunction);
    }

    public <A> NodeFuncObjectObjectToDouble<A, T> put_to_d(String fname, Class<A> argTypeA, NodeFuncObjectObjectToDouble.IFuncObjectObjectToDouble<A, T> func) {
        return this.put_oo_d(fname, argTypeA, this.getType(), func);
    }

    public <A> NodeFuncObjectObjectToDouble<A, T> put_to_d(String fname, Class<A> argTypeA, NodeFuncObjectObjectToDouble.IFuncObjectObjectToDouble<A, T> func, StringFunctionTri stringFunction) {
        return this.put_oo_d(fname, argTypeA, this.getType(), func, stringFunction);
    }

    public <B> NodeFuncObjectObjectToDouble<T, B> put_ot_d(String fname, Class<B> argTypeB, NodeFuncObjectObjectToDouble.IFuncObjectObjectToDouble<T, B> func) {
        return this.put_oo_d(fname, this.getType(), argTypeB, func);
    }

    public <B> NodeFuncObjectObjectToDouble<T, B> put_ot_d(String fname, Class<B> argTypeB, NodeFuncObjectObjectToDouble.IFuncObjectObjectToDouble<T, B> func, StringFunctionTri stringFunction) {
        return this.put_oo_d(fname, this.getType(), argTypeB, func, stringFunction);
    }

    public NodeFuncObjectObjectToDouble<T, T> put_tt_d(String fname, NodeFuncObjectObjectToDouble.IFuncObjectObjectToDouble<T, T> func) {
        return this.put_oo_d(fname, this.getType(), this.getType(), func);
    }

    public NodeFuncObjectObjectToDouble<T, T> put_tt_d(String fname, NodeFuncObjectObjectToDouble.IFuncObjectObjectToDouble<T, T> func, StringFunctionTri stringFunction) {
        return this.put_oo_d(fname, this.getType(), this.getType(), func, stringFunction);
    }

    public NodeFuncObjectToBoolean<T> put_t_b(String fname, NodeFuncObjectToBoolean.IFuncObjectToBoolean<T> func) {
        return this.put_o_b(fname, this.getType(), func);
    }

    public NodeFuncObjectToBoolean<T> put_t_b(String fname, NodeFuncObjectToBoolean.IFuncObjectToBoolean<T> func, StringFunctionBi stringFunction) {
        return this.put_o_b(fname, this.getType(), func, stringFunction);
    }

    public <A> NodeFuncObjectObjectToBoolean<A, T> put_to_b(String fname, Class<A> argTypeA, NodeFuncObjectObjectToBoolean.IFuncObjectObjectToBoolean<A, T> func) {
        return this.put_oo_b(fname, argTypeA, this.getType(), func);
    }

    public <A> NodeFuncObjectObjectToBoolean<A, T> put_to_b(String fname, Class<A> argTypeA, NodeFuncObjectObjectToBoolean.IFuncObjectObjectToBoolean<A, T> func, StringFunctionTri stringFunction) {
        return this.put_oo_b(fname, argTypeA, this.getType(), func, stringFunction);
    }

    public <B> NodeFuncObjectObjectToBoolean<T, B> put_ot_b(String fname, Class<B> argTypeB, NodeFuncObjectObjectToBoolean.IFuncObjectObjectToBoolean<T, B> func) {
        return this.put_oo_b(fname, this.getType(), argTypeB, func);
    }

    public <B> NodeFuncObjectObjectToBoolean<T, B> put_ot_b(String fname, Class<B> argTypeB, NodeFuncObjectObjectToBoolean.IFuncObjectObjectToBoolean<T, B> func, StringFunctionTri stringFunction) {
        return this.put_oo_b(fname, this.getType(), argTypeB, func, stringFunction);
    }

    public NodeFuncObjectObjectToBoolean<T, T> put_tt_b(String fname, NodeFuncObjectObjectToBoolean.IFuncObjectObjectToBoolean<T, T> func) {
        return this.put_oo_b(fname, this.getType(), this.getType(), func);
    }

    public NodeFuncObjectObjectToBoolean<T, T> put_tt_b(String fname, NodeFuncObjectObjectToBoolean.IFuncObjectObjectToBoolean<T, T> func, StringFunctionTri stringFunction) {
        return this.put_oo_b(fname, this.getType(), this.getType(), func, stringFunction);
    }

    public NodeFuncLongToObject<T> put_l_t(String fname, NodeFuncLongToObject.IFuncLongToObject<T> func) {
        return this.put_l_o(fname, this.getType(), func);
    }

    public NodeFuncLongToObject<T> put_l_t(String fname, NodeFuncLongToObject.IFuncLongToObject<T> func, StringFunctionBi stringFunction) {
        return this.put_l_o(fname, this.getType(), func, stringFunction);
    }

    public NodeFuncLongLongToObject<T> put_ll_t(String fname, NodeFuncLongLongToObject.IFuncLongLongToObject<T> func) {
        return this.put_ll_o(fname, this.getType(), func);
    }

    public NodeFuncLongLongToObject<T> put_ll_t(String fname, NodeFuncLongLongToObject.IFuncLongLongToObject<T> func, StringFunctionTri stringFunction) {
        return this.put_ll_o(fname, this.getType(), func, stringFunction);
    }

    public NodeFuncLongLongLongToObject<T> put_lll_t(String fname, NodeFuncLongLongLongToObject.IFuncLongLongLongToObject<T> func) {
        return this.put_lll_o(fname, this.getType(), func);
    }

    public NodeFuncLongLongLongToObject<T> put_lll_t(String fname, NodeFuncLongLongLongToObject.IFuncLongLongLongToObject<T> func, StringFunctionQuad stringFunction) {
        return this.put_lll_o(fname, this.getType(), func, stringFunction);
    }

    public NodeFuncLongLongLongLongToObject<T> put_llll_t(String fname, NodeFuncLongLongLongLongToObject.IFuncLongLongLongLongToObject<T> func) {
        return this.put_llll_o(fname, this.getType(), func);
    }

    public NodeFuncLongLongLongLongToObject<T> put_llll_t(String fname, NodeFuncLongLongLongLongToObject.IFuncLongLongLongLongToObject<T> func, StringFunctionPenta stringFunction) {
        return this.put_llll_o(fname, this.getType(), func, stringFunction);
    }

    public NodeFuncDoubleToObject<T> put_d_t(String fname, NodeFuncDoubleToObject.IFuncDoubleToObject<T> func) {
        return this.put_d_o(fname, this.getType(), func);
    }

    public NodeFuncDoubleToObject<T> put_d_t(String fname, NodeFuncDoubleToObject.IFuncDoubleToObject<T> func, StringFunctionBi stringFunction) {
        return this.put_d_o(fname, this.getType(), func, stringFunction);
    }

    public NodeFuncDoubleDoubleToObject<T> put_dd_t(String fname, NodeFuncDoubleDoubleToObject.IFuncDoubleDoubleToObject<T> func) {
        return this.put_dd_o(fname, this.getType(), func);
    }

    public NodeFuncDoubleDoubleToObject<T> put_dd_t(String fname, NodeFuncDoubleDoubleToObject.IFuncDoubleDoubleToObject<T> func, StringFunctionTri stringFunction) {
        return this.put_dd_o(fname, this.getType(), func, stringFunction);
    }

    public NodeFuncDoubleDoubleDoubleToObject<T> put_ddd_t(String fname, NodeFuncDoubleDoubleDoubleToObject.IFuncDoubleDoubleDoubleToObject<T> func) {
        return this.put_ddd_o(fname, this.getType(), func);
    }

    public NodeFuncDoubleDoubleDoubleToObject<T> put_ddd_t(String fname, NodeFuncDoubleDoubleDoubleToObject.IFuncDoubleDoubleDoubleToObject<T> func, StringFunctionQuad stringFunction) {
        return this.put_ddd_o(fname, this.getType(), func, stringFunction);
    }

    public NodeFuncDoubleDoubleDoubleDoubleToObject<T> put_dddd_t(String fname, NodeFuncDoubleDoubleDoubleDoubleToObject.IFuncDoubleDoubleDoubleDoubleToObject<T> func) {
        return this.put_dddd_o(fname, this.getType(), func);
    }

    public NodeFuncDoubleDoubleDoubleDoubleToObject<T> put_dddd_t(String fname, NodeFuncDoubleDoubleDoubleDoubleToObject.IFuncDoubleDoubleDoubleDoubleToObject<T> func, StringFunctionPenta stringFunction) {
        return this.put_dddd_o(fname, this.getType(), func, stringFunction);
    }

    public NodeFuncBooleanToObject<T> put_b_t(String fname, NodeFuncBooleanToObject.IFuncBooleanToObject<T> func) {
        return this.put_b_o(fname, this.getType(), func);
    }

    public NodeFuncBooleanToObject<T> put_b_t(String fname, NodeFuncBooleanToObject.IFuncBooleanToObject<T> func, StringFunctionBi stringFunction) {
        return this.put_b_o(fname, this.getType(), func, stringFunction);
    }

    public <A> NodeFuncObjectToObject<A, T> put_o_t(String fname, Class<A> argTypeA, NodeFuncObjectToObject.IFuncObjectToObject<A, T> func) {
        return this.put_o_o(fname, argTypeA, this.getType(), func);
    }

    public <A> NodeFuncObjectToObject<A, T> put_o_t(String fname, Class<A> argTypeA, NodeFuncObjectToObject.IFuncObjectToObject<A, T> func, StringFunctionBi stringFunction) {
        return this.put_o_o(fname, argTypeA, this.getType(), func, stringFunction);
    }

    public <R> NodeFuncObjectToObject<T, R> put_t_o(String fname, Class<R> returnType, NodeFuncObjectToObject.IFuncObjectToObject<T, R> func) {
        return this.put_o_o(fname, this.getType(), returnType, func);
    }

    public <R> NodeFuncObjectToObject<T, R> put_t_o(String fname, Class<R> returnType, NodeFuncObjectToObject.IFuncObjectToObject<T, R> func, StringFunctionBi stringFunction) {
        return this.put_o_o(fname, this.getType(), returnType, func, stringFunction);
    }

    public NodeFuncObjectToObject<T, T> put_t_t(String fname, NodeFuncObjectToObject.IFuncObjectToObject<T, T> func) {
        return this.put_o_o(fname, this.getType(), this.getType(), func);
    }

    public NodeFuncObjectToObject<T, T> put_t_t(String fname, NodeFuncObjectToObject.IFuncObjectToObject<T, T> func, StringFunctionBi stringFunction) {
        return this.put_o_o(fname, this.getType(), this.getType(), func, stringFunction);
    }

    public <A> NodeFuncObjectLongToObject<A, T> put_ol_t(String fname, Class<A> argTypeA, NodeFuncObjectLongToObject.IFuncObjectLongToObject<A, T> func) {
        return this.put_ol_o(fname, argTypeA, this.getType(), func);
    }

    public <A> NodeFuncObjectLongToObject<A, T> put_ol_t(String fname, Class<A> argTypeA, NodeFuncObjectLongToObject.IFuncObjectLongToObject<A, T> func, StringFunctionTri stringFunction) {
        return this.put_ol_o(fname, argTypeA, this.getType(), func, stringFunction);
    }

    public <R> NodeFuncObjectLongToObject<T, R> put_tl_o(String fname, Class<R> returnType, NodeFuncObjectLongToObject.IFuncObjectLongToObject<T, R> func) {
        return this.put_ol_o(fname, this.getType(), returnType, func);
    }

    public <R> NodeFuncObjectLongToObject<T, R> put_tl_o(String fname, Class<R> returnType, NodeFuncObjectLongToObject.IFuncObjectLongToObject<T, R> func, StringFunctionTri stringFunction) {
        return this.put_ol_o(fname, this.getType(), returnType, func, stringFunction);
    }

    public NodeFuncObjectLongToObject<T, T> put_tl_t(String fname, NodeFuncObjectLongToObject.IFuncObjectLongToObject<T, T> func) {
        return this.put_ol_o(fname, this.getType(), this.getType(), func);
    }

    public NodeFuncObjectLongToObject<T, T> put_tl_t(String fname, NodeFuncObjectLongToObject.IFuncObjectLongToObject<T, T> func, StringFunctionTri stringFunction) {
        return this.put_ol_o(fname, this.getType(), this.getType(), func, stringFunction);
    }

    public <A> NodeFuncObjectLongLongToObject<A, T> put_oll_t(String fname, Class<A> argTypeA, NodeFuncObjectLongLongToObject.IFuncObjectLongLongToObject<A, T> func) {
        return this.put_oll_o(fname, argTypeA, this.getType(), func);
    }

    public <A> NodeFuncObjectLongLongToObject<A, T> put_oll_t(String fname, Class<A> argTypeA, NodeFuncObjectLongLongToObject.IFuncObjectLongLongToObject<A, T> func, StringFunctionQuad stringFunction) {
        return this.put_oll_o(fname, argTypeA, this.getType(), func, stringFunction);
    }

    public <R> NodeFuncObjectLongLongToObject<T, R> put_tll_o(String fname, Class<R> returnType, NodeFuncObjectLongLongToObject.IFuncObjectLongLongToObject<T, R> func) {
        return this.put_oll_o(fname, this.getType(), returnType, func);
    }

    public <R> NodeFuncObjectLongLongToObject<T, R> put_tll_o(String fname, Class<R> returnType, NodeFuncObjectLongLongToObject.IFuncObjectLongLongToObject<T, R> func, StringFunctionQuad stringFunction) {
        return this.put_oll_o(fname, this.getType(), returnType, func, stringFunction);
    }

    public NodeFuncObjectLongLongToObject<T, T> put_tll_t(String fname, NodeFuncObjectLongLongToObject.IFuncObjectLongLongToObject<T, T> func) {
        return this.put_oll_o(fname, this.getType(), this.getType(), func);
    }

    public NodeFuncObjectLongLongToObject<T, T> put_tll_t(String fname, NodeFuncObjectLongLongToObject.IFuncObjectLongLongToObject<T, T> func, StringFunctionQuad stringFunction) {
        return this.put_oll_o(fname, this.getType(), this.getType(), func, stringFunction);
    }

    public <A> NodeFuncObjectBooleanToObject<A, T> put_ob_t(String fname, Class<A> argTypeA, NodeFuncObjectBooleanToObject.IFuncObjectBooleanToObject<A, T> func) {
        return this.put_ob_o(fname, argTypeA, this.getType(), func);
    }

    public <A> NodeFuncObjectBooleanToObject<A, T> put_ob_t(String fname, Class<A> argTypeA, NodeFuncObjectBooleanToObject.IFuncObjectBooleanToObject<A, T> func, StringFunctionTri stringFunction) {
        return this.put_ob_o(fname, argTypeA, this.getType(), func, stringFunction);
    }

    public <R> NodeFuncObjectBooleanToObject<T, R> put_tb_o(String fname, Class<R> returnType, NodeFuncObjectBooleanToObject.IFuncObjectBooleanToObject<T, R> func) {
        return this.put_ob_o(fname, this.getType(), returnType, func);
    }

    public <R> NodeFuncObjectBooleanToObject<T, R> put_tb_o(String fname, Class<R> returnType, NodeFuncObjectBooleanToObject.IFuncObjectBooleanToObject<T, R> func, StringFunctionTri stringFunction) {
        return this.put_ob_o(fname, this.getType(), returnType, func, stringFunction);
    }

    public NodeFuncObjectBooleanToObject<T, T> put_tb_t(String fname, NodeFuncObjectBooleanToObject.IFuncObjectBooleanToObject<T, T> func) {
        return this.put_ob_o(fname, this.getType(), this.getType(), func);
    }

    public NodeFuncObjectBooleanToObject<T, T> put_tb_t(String fname, NodeFuncObjectBooleanToObject.IFuncObjectBooleanToObject<T, T> func, StringFunctionTri stringFunction) {
        return this.put_ob_o(fname, this.getType(), this.getType(), func, stringFunction);
    }

    public <A, B> NodeFuncObjectObjectToObject<A, B, T> put_oo_t(String fname, Class<A> argTypeA, Class<B> argTypeB, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<A, B, T> func) {
        return this.put_oo_o(fname, argTypeA, argTypeB, this.getType(), func);
    }

    public <A, B> NodeFuncObjectObjectToObject<A, B, T> put_oo_t(String fname, Class<A> argTypeA, Class<B> argTypeB, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<A, B, T> func, StringFunctionTri stringFunction) {
        return this.put_oo_o(fname, argTypeA, argTypeB, this.getType(), func, stringFunction);
    }

    public <B, R> NodeFuncObjectObjectToObject<T, B, R> put_to_o(String fname, Class<B> argTypeB, Class<R> returnType, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<T, B, R> func) {
        return this.put_oo_o(fname, this.getType(), argTypeB, returnType, func);
    }

    public <B, R> NodeFuncObjectObjectToObject<T, B, R> put_to_o(String fname, Class<B> argTypeB, Class<R> returnType, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<T, B, R> func, StringFunctionTri stringFunction) {
        return this.put_oo_o(fname, this.getType(), argTypeB, returnType, func, stringFunction);
    }

    public <B> NodeFuncObjectObjectToObject<T, B, T> put_to_t(String fname, Class<B> argTypeB, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<T, B, T> func) {
        return this.put_oo_o(fname, this.getType(), argTypeB, this.getType(), func);
    }

    public <B> NodeFuncObjectObjectToObject<T, B, T> put_to_t(String fname, Class<B> argTypeB, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<T, B, T> func, StringFunctionTri stringFunction) {
        return this.put_oo_o(fname, this.getType(), argTypeB, this.getType(), func, stringFunction);
    }

    public <A, R> NodeFuncObjectObjectToObject<A, T, R> put_ot_o(String fname, Class<A> argTypeA, Class<R> returnType, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<A, T, R> func) {
        return this.put_oo_o(fname, argTypeA, this.getType(), returnType, func);
    }

    public <A, R> NodeFuncObjectObjectToObject<A, T, R> put_ot_o(String fname, Class<A> argTypeA, Class<R> returnType, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<A, T, R> func, StringFunctionTri stringFunction) {
        return this.put_oo_o(fname, argTypeA, this.getType(), returnType, func, stringFunction);
    }

    public <A> NodeFuncObjectObjectToObject<A, T, T> put_ot_t(String fname, Class<A> argTypeA, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<A, T, T> func) {
        return this.put_oo_o(fname, argTypeA, this.getType(), this.getType(), func);
    }

    public <A> NodeFuncObjectObjectToObject<A, T, T> put_ot_t(String fname, Class<A> argTypeA, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<A, T, T> func, StringFunctionTri stringFunction) {
        return this.put_oo_o(fname, argTypeA, this.getType(), this.getType(), func, stringFunction);
    }

    public <R> NodeFuncObjectObjectToObject<T, T, R> put_tt_o(String fname, Class<R> returnType, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<T, T, R> func) {
        return this.put_oo_o(fname, this.getType(), this.getType(), returnType, func);
    }

    public <R> NodeFuncObjectObjectToObject<T, T, R> put_tt_o(String fname, Class<R> returnType, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<T, T, R> func, StringFunctionTri stringFunction) {
        return this.put_oo_o(fname, this.getType(), this.getType(), returnType, func, stringFunction);
    }

    public NodeFuncObjectObjectToObject<T, T, T> put_tt_t(String fname, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<T, T, T> func) {
        return this.put_oo_o(fname, this.getType(), this.getType(), this.getType(), func);
    }

    public NodeFuncObjectObjectToObject<T, T, T> put_tt_t(String fname, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<T, T, T> func, StringFunctionTri stringFunction) {
        return this.put_oo_o(fname, this.getType(), this.getType(), this.getType(), func, stringFunction);
    }

    public <A, B, C> NodeFuncObjectObjectObjectToObject<A, B, C, T> put_ooo_t(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<C> argTypeC, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, B, C, T> func) {
        return this.put_ooo_o(fname, argTypeA, argTypeB, argTypeC, this.getType(), func);
    }

    public <A, B, C> NodeFuncObjectObjectObjectToObject<A, B, C, T> put_ooo_t(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<C> argTypeC, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, B, C, T> func, StringFunctionQuad stringFunction) {
        return this.put_ooo_o(fname, argTypeA, argTypeB, argTypeC, this.getType(), func, stringFunction);
    }

    public <B, C, R> NodeFuncObjectObjectObjectToObject<T, B, C, R> put_too_o(String fname, Class<B> argTypeB, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, B, C, R> func) {
        return this.put_ooo_o(fname, this.getType(), argTypeB, argTypeC, returnType, func);
    }

    public <B, C, R> NodeFuncObjectObjectObjectToObject<T, B, C, R> put_too_o(String fname, Class<B> argTypeB, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, B, C, R> func, StringFunctionQuad stringFunction) {
        return this.put_ooo_o(fname, this.getType(), argTypeB, argTypeC, returnType, func, stringFunction);
    }

    public <B, C> NodeFuncObjectObjectObjectToObject<T, B, C, T> put_too_t(String fname, Class<B> argTypeB, Class<C> argTypeC, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, B, C, T> func) {
        return this.put_ooo_o(fname, this.getType(), argTypeB, argTypeC, this.getType(), func);
    }

    public <B, C> NodeFuncObjectObjectObjectToObject<T, B, C, T> put_too_t(String fname, Class<B> argTypeB, Class<C> argTypeC, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, B, C, T> func, StringFunctionQuad stringFunction) {
        return this.put_ooo_o(fname, this.getType(), argTypeB, argTypeC, this.getType(), func, stringFunction);
    }

    public <A, C, R> NodeFuncObjectObjectObjectToObject<A, T, C, R> put_oto_o(String fname, Class<A> argTypeA, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, T, C, R> func) {
        return this.put_ooo_o(fname, argTypeA, this.getType(), argTypeC, returnType, func);
    }

    public <A, C, R> NodeFuncObjectObjectObjectToObject<A, T, C, R> put_oto_o(String fname, Class<A> argTypeA, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, T, C, R> func, StringFunctionQuad stringFunction) {
        return this.put_ooo_o(fname, argTypeA, this.getType(), argTypeC, returnType, func, stringFunction);
    }

    public <A, C> NodeFuncObjectObjectObjectToObject<A, T, C, T> put_oto_t(String fname, Class<A> argTypeA, Class<C> argTypeC, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, T, C, T> func) {
        return this.put_ooo_o(fname, argTypeA, this.getType(), argTypeC, this.getType(), func);
    }

    public <A, C> NodeFuncObjectObjectObjectToObject<A, T, C, T> put_oto_t(String fname, Class<A> argTypeA, Class<C> argTypeC, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, T, C, T> func, StringFunctionQuad stringFunction) {
        return this.put_ooo_o(fname, argTypeA, this.getType(), argTypeC, this.getType(), func, stringFunction);
    }

    public <C, R> NodeFuncObjectObjectObjectToObject<T, T, C, R> put_tto_o(String fname, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, T, C, R> func) {
        return this.put_ooo_o(fname, this.getType(), this.getType(), argTypeC, returnType, func);
    }

    public <C, R> NodeFuncObjectObjectObjectToObject<T, T, C, R> put_tto_o(String fname, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, T, C, R> func, StringFunctionQuad stringFunction) {
        return this.put_ooo_o(fname, this.getType(), this.getType(), argTypeC, returnType, func, stringFunction);
    }

    public <C> NodeFuncObjectObjectObjectToObject<T, T, C, T> put_tto_t(String fname, Class<C> argTypeC, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, T, C, T> func) {
        return this.put_ooo_o(fname, this.getType(), this.getType(), argTypeC, this.getType(), func);
    }

    public <C> NodeFuncObjectObjectObjectToObject<T, T, C, T> put_tto_t(String fname, Class<C> argTypeC, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, T, C, T> func, StringFunctionQuad stringFunction) {
        return this.put_ooo_o(fname, this.getType(), this.getType(), argTypeC, this.getType(), func, stringFunction);
    }

    public <A, B, R> NodeFuncObjectObjectObjectToObject<A, B, T, R> put_oot_o(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, B, T, R> func) {
        return this.put_ooo_o(fname, argTypeA, argTypeB, this.getType(), returnType, func);
    }

    public <A, B, R> NodeFuncObjectObjectObjectToObject<A, B, T, R> put_oot_o(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, B, T, R> func, StringFunctionQuad stringFunction) {
        return this.put_ooo_o(fname, argTypeA, argTypeB, this.getType(), returnType, func, stringFunction);
    }

    public <A, B> NodeFuncObjectObjectObjectToObject<A, B, T, T> put_oot_t(String fname, Class<A> argTypeA, Class<B> argTypeB, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, B, T, T> func) {
        return this.put_ooo_o(fname, argTypeA, argTypeB, this.getType(), this.getType(), func);
    }

    public <A, B> NodeFuncObjectObjectObjectToObject<A, B, T, T> put_oot_t(String fname, Class<A> argTypeA, Class<B> argTypeB, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, B, T, T> func, StringFunctionQuad stringFunction) {
        return this.put_ooo_o(fname, argTypeA, argTypeB, this.getType(), this.getType(), func, stringFunction);
    }

    public <B, R> NodeFuncObjectObjectObjectToObject<T, B, T, R> put_tot_o(String fname, Class<B> argTypeB, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, B, T, R> func) {
        return this.put_ooo_o(fname, this.getType(), argTypeB, this.getType(), returnType, func);
    }

    public <B, R> NodeFuncObjectObjectObjectToObject<T, B, T, R> put_tot_o(String fname, Class<B> argTypeB, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, B, T, R> func, StringFunctionQuad stringFunction) {
        return this.put_ooo_o(fname, this.getType(), argTypeB, this.getType(), returnType, func, stringFunction);
    }

    public <B> NodeFuncObjectObjectObjectToObject<T, B, T, T> put_tot_t(String fname, Class<B> argTypeB, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, B, T, T> func) {
        return this.put_ooo_o(fname, this.getType(), argTypeB, this.getType(), this.getType(), func);
    }

    public <B> NodeFuncObjectObjectObjectToObject<T, B, T, T> put_tot_t(String fname, Class<B> argTypeB, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, B, T, T> func, StringFunctionQuad stringFunction) {
        return this.put_ooo_o(fname, this.getType(), argTypeB, this.getType(), this.getType(), func, stringFunction);
    }

    public <A, R> NodeFuncObjectObjectObjectToObject<A, T, T, R> put_ott_o(String fname, Class<A> argTypeA, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, T, T, R> func) {
        return this.put_ooo_o(fname, argTypeA, this.getType(), this.getType(), returnType, func);
    }

    public <A, R> NodeFuncObjectObjectObjectToObject<A, T, T, R> put_ott_o(String fname, Class<A> argTypeA, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, T, T, R> func, StringFunctionQuad stringFunction) {
        return this.put_ooo_o(fname, argTypeA, this.getType(), this.getType(), returnType, func, stringFunction);
    }

    public <A> NodeFuncObjectObjectObjectToObject<A, T, T, T> put_ott_t(String fname, Class<A> argTypeA, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, T, T, T> func) {
        return this.put_ooo_o(fname, argTypeA, this.getType(), this.getType(), this.getType(), func);
    }

    public <A> NodeFuncObjectObjectObjectToObject<A, T, T, T> put_ott_t(String fname, Class<A> argTypeA, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, T, T, T> func, StringFunctionQuad stringFunction) {
        return this.put_ooo_o(fname, argTypeA, this.getType(), this.getType(), this.getType(), func, stringFunction);
    }

    public <R> NodeFuncObjectObjectObjectToObject<T, T, T, R> put_ttt_o(String fname, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, T, T, R> func) {
        return this.put_ooo_o(fname, this.getType(), this.getType(), this.getType(), returnType, func);
    }

    public <R> NodeFuncObjectObjectObjectToObject<T, T, T, R> put_ttt_o(String fname, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, T, T, R> func, StringFunctionQuad stringFunction) {
        return this.put_ooo_o(fname, this.getType(), this.getType(), this.getType(), returnType, func, stringFunction);
    }

    public NodeFuncObjectObjectObjectToObject<T, T, T, T> put_ttt_t(String fname, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, T, T, T> func) {
        return this.put_ooo_o(fname, this.getType(), this.getType(), this.getType(), this.getType(), func);
    }

    public NodeFuncObjectObjectObjectToObject<T, T, T, T> put_ttt_t(String fname, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<T, T, T, T> func, StringFunctionQuad stringFunction) {
        return this.put_ooo_o(fname, this.getType(), this.getType(), this.getType(), this.getType(), func, stringFunction);
    }

    public <A, B, C, D> NodeFuncObjectObjectObjectObjectToObject<A, B, C, D, T> put_oooo_t(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<C> argTypeC, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, C, D, T> func) {
        return this.put_oooo_o(fname, argTypeA, argTypeB, argTypeC, argTypeD, this.getType(), func);
    }

    public <A, B, C, D> NodeFuncObjectObjectObjectObjectToObject<A, B, C, D, T> put_oooo_t(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<C> argTypeC, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, C, D, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, argTypeA, argTypeB, argTypeC, argTypeD, this.getType(), func, stringFunction);
    }

    public <B, C, D, R> NodeFuncObjectObjectObjectObjectToObject<T, B, C, D, R> put_tooo_o(String fname, Class<B> argTypeB, Class<C> argTypeC, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, C, D, R> func) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, argTypeC, argTypeD, returnType, func);
    }

    public <B, C, D, R> NodeFuncObjectObjectObjectObjectToObject<T, B, C, D, R> put_tooo_o(String fname, Class<B> argTypeB, Class<C> argTypeC, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, C, D, R> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, argTypeC, argTypeD, returnType, func, stringFunction);
    }

    public <B, C, D> NodeFuncObjectObjectObjectObjectToObject<T, B, C, D, T> put_tooo_t(String fname, Class<B> argTypeB, Class<C> argTypeC, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, C, D, T> func) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, argTypeC, argTypeD, this.getType(), func);
    }

    public <B, C, D> NodeFuncObjectObjectObjectObjectToObject<T, B, C, D, T> put_tooo_t(String fname, Class<B> argTypeB, Class<C> argTypeC, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, C, D, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, argTypeC, argTypeD, this.getType(), func, stringFunction);
    }

    public <A, C, D, R> NodeFuncObjectObjectObjectObjectToObject<A, T, C, D, R> put_otoo_o(String fname, Class<A> argTypeA, Class<C> argTypeC, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, C, D, R> func) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), argTypeC, argTypeD, returnType, func);
    }

    public <A, C, D, R> NodeFuncObjectObjectObjectObjectToObject<A, T, C, D, R> put_otoo_o(String fname, Class<A> argTypeA, Class<C> argTypeC, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, C, D, R> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), argTypeC, argTypeD, returnType, func, stringFunction);
    }

    public <A, C, D> NodeFuncObjectObjectObjectObjectToObject<A, T, C, D, T> put_otoo_t(String fname, Class<A> argTypeA, Class<C> argTypeC, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, C, D, T> func) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), argTypeC, argTypeD, this.getType(), func);
    }

    public <A, C, D> NodeFuncObjectObjectObjectObjectToObject<A, T, C, D, T> put_otoo_t(String fname, Class<A> argTypeA, Class<C> argTypeC, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, C, D, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), argTypeC, argTypeD, this.getType(), func, stringFunction);
    }

    public <C, D, R> NodeFuncObjectObjectObjectObjectToObject<T, T, C, D, R> put_ttoo_o(String fname, Class<C> argTypeC, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, C, D, R> func) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), argTypeC, argTypeD, returnType, func);
    }

    public <C, D, R> NodeFuncObjectObjectObjectObjectToObject<T, T, C, D, R> put_ttoo_o(String fname, Class<C> argTypeC, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, C, D, R> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), argTypeC, argTypeD, returnType, func, stringFunction);
    }

    public <C, D> NodeFuncObjectObjectObjectObjectToObject<T, T, C, D, T> put_ttoo_t(String fname, Class<C> argTypeC, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, C, D, T> func) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), argTypeC, argTypeD, this.getType(), func);
    }

    public <C, D> NodeFuncObjectObjectObjectObjectToObject<T, T, C, D, T> put_ttoo_t(String fname, Class<C> argTypeC, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, C, D, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), argTypeC, argTypeD, this.getType(), func, stringFunction);
    }

    public <A, B, D, R> NodeFuncObjectObjectObjectObjectToObject<A, B, T, D, R> put_ooto_o(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, T, D, R> func) {
        return this.put_oooo_o(fname, argTypeA, argTypeB, this.getType(), argTypeD, returnType, func);
    }

    public <A, B, D, R> NodeFuncObjectObjectObjectObjectToObject<A, B, T, D, R> put_ooto_o(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, T, D, R> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, argTypeA, argTypeB, this.getType(), argTypeD, returnType, func, stringFunction);
    }

    public <A, B, D> NodeFuncObjectObjectObjectObjectToObject<A, B, T, D, T> put_ooto_t(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, T, D, T> func) {
        return this.put_oooo_o(fname, argTypeA, argTypeB, this.getType(), argTypeD, this.getType(), func);
    }

    public <A, B, D> NodeFuncObjectObjectObjectObjectToObject<A, B, T, D, T> put_ooto_t(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, T, D, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, argTypeA, argTypeB, this.getType(), argTypeD, this.getType(), func, stringFunction);
    }

    public <B, D, R> NodeFuncObjectObjectObjectObjectToObject<T, B, T, D, R> put_toto_o(String fname, Class<B> argTypeB, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, T, D, R> func) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, this.getType(), argTypeD, returnType, func);
    }

    public <B, D, R> NodeFuncObjectObjectObjectObjectToObject<T, B, T, D, R> put_toto_o(String fname, Class<B> argTypeB, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, T, D, R> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, this.getType(), argTypeD, returnType, func, stringFunction);
    }

    public <B, D> NodeFuncObjectObjectObjectObjectToObject<T, B, T, D, T> put_toto_t(String fname, Class<B> argTypeB, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, T, D, T> func) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, this.getType(), argTypeD, this.getType(), func);
    }

    public <B, D> NodeFuncObjectObjectObjectObjectToObject<T, B, T, D, T> put_toto_t(String fname, Class<B> argTypeB, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, T, D, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, this.getType(), argTypeD, this.getType(), func, stringFunction);
    }

    public <A, D, R> NodeFuncObjectObjectObjectObjectToObject<A, T, T, D, R> put_otto_o(String fname, Class<A> argTypeA, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, T, D, R> func) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), this.getType(), argTypeD, returnType, func);
    }

    public <A, D, R> NodeFuncObjectObjectObjectObjectToObject<A, T, T, D, R> put_otto_o(String fname, Class<A> argTypeA, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, T, D, R> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), this.getType(), argTypeD, returnType, func, stringFunction);
    }

    public <A, D> NodeFuncObjectObjectObjectObjectToObject<A, T, T, D, T> put_otto_t(String fname, Class<A> argTypeA, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, T, D, T> func) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), this.getType(), argTypeD, this.getType(), func);
    }

    public <A, D> NodeFuncObjectObjectObjectObjectToObject<A, T, T, D, T> put_otto_t(String fname, Class<A> argTypeA, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, T, D, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), this.getType(), argTypeD, this.getType(), func, stringFunction);
    }

    public <D, R> NodeFuncObjectObjectObjectObjectToObject<T, T, T, D, R> put_ttto_o(String fname, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, T, D, R> func) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), this.getType(), argTypeD, returnType, func);
    }

    public <D, R> NodeFuncObjectObjectObjectObjectToObject<T, T, T, D, R> put_ttto_o(String fname, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, T, D, R> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), this.getType(), argTypeD, returnType, func, stringFunction);
    }

    public <D> NodeFuncObjectObjectObjectObjectToObject<T, T, T, D, T> put_ttto_t(String fname, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, T, D, T> func) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), this.getType(), argTypeD, this.getType(), func);
    }

    public <D> NodeFuncObjectObjectObjectObjectToObject<T, T, T, D, T> put_ttto_t(String fname, Class<D> argTypeD, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, T, D, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), this.getType(), argTypeD, this.getType(), func, stringFunction);
    }

    public <A, B, C, R> NodeFuncObjectObjectObjectObjectToObject<A, B, C, T, R> put_ooot_o(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, C, T, R> func) {
        return this.put_oooo_o(fname, argTypeA, argTypeB, argTypeC, this.getType(), returnType, func);
    }

    public <A, B, C, R> NodeFuncObjectObjectObjectObjectToObject<A, B, C, T, R> put_ooot_o(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, C, T, R> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, argTypeA, argTypeB, argTypeC, this.getType(), returnType, func, stringFunction);
    }

    public <A, B, C> NodeFuncObjectObjectObjectObjectToObject<A, B, C, T, T> put_ooot_t(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<C> argTypeC, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, C, T, T> func) {
        return this.put_oooo_o(fname, argTypeA, argTypeB, argTypeC, this.getType(), this.getType(), func);
    }

    public <A, B, C> NodeFuncObjectObjectObjectObjectToObject<A, B, C, T, T> put_ooot_t(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<C> argTypeC, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, C, T, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, argTypeA, argTypeB, argTypeC, this.getType(), this.getType(), func, stringFunction);
    }

    public <B, C, R> NodeFuncObjectObjectObjectObjectToObject<T, B, C, T, R> put_toot_o(String fname, Class<B> argTypeB, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, C, T, R> func) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, argTypeC, this.getType(), returnType, func);
    }

    public <B, C, R> NodeFuncObjectObjectObjectObjectToObject<T, B, C, T, R> put_toot_o(String fname, Class<B> argTypeB, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, C, T, R> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, argTypeC, this.getType(), returnType, func, stringFunction);
    }

    public <B, C> NodeFuncObjectObjectObjectObjectToObject<T, B, C, T, T> put_toot_t(String fname, Class<B> argTypeB, Class<C> argTypeC, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, C, T, T> func) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, argTypeC, this.getType(), this.getType(), func);
    }

    public <B, C> NodeFuncObjectObjectObjectObjectToObject<T, B, C, T, T> put_toot_t(String fname, Class<B> argTypeB, Class<C> argTypeC, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, C, T, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, argTypeC, this.getType(), this.getType(), func, stringFunction);
    }

    public <A, C, R> NodeFuncObjectObjectObjectObjectToObject<A, T, C, T, R> put_otot_o(String fname, Class<A> argTypeA, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, C, T, R> func) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), argTypeC, this.getType(), returnType, func);
    }

    public <A, C, R> NodeFuncObjectObjectObjectObjectToObject<A, T, C, T, R> put_otot_o(String fname, Class<A> argTypeA, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, C, T, R> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), argTypeC, this.getType(), returnType, func, stringFunction);
    }

    public <A, C> NodeFuncObjectObjectObjectObjectToObject<A, T, C, T, T> put_otot_t(String fname, Class<A> argTypeA, Class<C> argTypeC, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, C, T, T> func) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), argTypeC, this.getType(), this.getType(), func);
    }

    public <A, C> NodeFuncObjectObjectObjectObjectToObject<A, T, C, T, T> put_otot_t(String fname, Class<A> argTypeA, Class<C> argTypeC, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, C, T, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), argTypeC, this.getType(), this.getType(), func, stringFunction);
    }

    public <C, R> NodeFuncObjectObjectObjectObjectToObject<T, T, C, T, R> put_ttot_o(String fname, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, C, T, R> func) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), argTypeC, this.getType(), returnType, func);
    }

    public <C, R> NodeFuncObjectObjectObjectObjectToObject<T, T, C, T, R> put_ttot_o(String fname, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, C, T, R> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), argTypeC, this.getType(), returnType, func, stringFunction);
    }

    public <C> NodeFuncObjectObjectObjectObjectToObject<T, T, C, T, T> put_ttot_t(String fname, Class<C> argTypeC, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, C, T, T> func) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), argTypeC, this.getType(), this.getType(), func);
    }

    public <C> NodeFuncObjectObjectObjectObjectToObject<T, T, C, T, T> put_ttot_t(String fname, Class<C> argTypeC, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, C, T, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), argTypeC, this.getType(), this.getType(), func, stringFunction);
    }

    public <A, B, R> NodeFuncObjectObjectObjectObjectToObject<A, B, T, T, R> put_oott_o(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, T, T, R> func) {
        return this.put_oooo_o(fname, argTypeA, argTypeB, this.getType(), this.getType(), returnType, func);
    }

    public <A, B, R> NodeFuncObjectObjectObjectObjectToObject<A, B, T, T, R> put_oott_o(String fname, Class<A> argTypeA, Class<B> argTypeB, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, T, T, R> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, argTypeA, argTypeB, this.getType(), this.getType(), returnType, func, stringFunction);
    }

    public <A, B> NodeFuncObjectObjectObjectObjectToObject<A, B, T, T, T> put_oott_t(String fname, Class<A> argTypeA, Class<B> argTypeB, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, T, T, T> func) {
        return this.put_oooo_o(fname, argTypeA, argTypeB, this.getType(), this.getType(), this.getType(), func);
    }

    public <A, B> NodeFuncObjectObjectObjectObjectToObject<A, B, T, T, T> put_oott_t(String fname, Class<A> argTypeA, Class<B> argTypeB, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, T, T, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, argTypeA, argTypeB, this.getType(), this.getType(), this.getType(), func, stringFunction);
    }

    public <B, R> NodeFuncObjectObjectObjectObjectToObject<T, B, T, T, R> put_tott_o(String fname, Class<B> argTypeB, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, T, T, R> func) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, this.getType(), this.getType(), returnType, func);
    }

    public <B, R> NodeFuncObjectObjectObjectObjectToObject<T, B, T, T, R> put_tott_o(String fname, Class<B> argTypeB, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, T, T, R> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, this.getType(), this.getType(), returnType, func, stringFunction);
    }

    public <B> NodeFuncObjectObjectObjectObjectToObject<T, B, T, T, T> put_tott_t(String fname, Class<B> argTypeB, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, T, T, T> func) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, this.getType(), this.getType(), this.getType(), func);
    }

    public <B> NodeFuncObjectObjectObjectObjectToObject<T, B, T, T, T> put_tott_t(String fname, Class<B> argTypeB, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, B, T, T, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), argTypeB, this.getType(), this.getType(), this.getType(), func, stringFunction);
    }

    public <A, R> NodeFuncObjectObjectObjectObjectToObject<A, T, T, T, R> put_ottt_o(String fname, Class<A> argTypeA, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, T, T, R> func) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), this.getType(), this.getType(), returnType, func);
    }

    public <A, R> NodeFuncObjectObjectObjectObjectToObject<A, T, T, T, R> put_ottt_o(String fname, Class<A> argTypeA, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, T, T, R> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), this.getType(), this.getType(), returnType, func, stringFunction);
    }

    public <A> NodeFuncObjectObjectObjectObjectToObject<A, T, T, T, T> put_ottt_t(String fname, Class<A> argTypeA, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, T, T, T> func) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), this.getType(), this.getType(), this.getType(), func);
    }

    public <A> NodeFuncObjectObjectObjectObjectToObject<A, T, T, T, T> put_ottt_t(String fname, Class<A> argTypeA, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, T, T, T, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, argTypeA, this.getType(), this.getType(), this.getType(), this.getType(), func, stringFunction);
    }

    public <R> NodeFuncObjectObjectObjectObjectToObject<T, T, T, T, R> put_tttt_o(String fname, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, T, T, R> func) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), this.getType(), this.getType(), returnType, func);
    }

    public <R> NodeFuncObjectObjectObjectObjectToObject<T, T, T, T, R> put_tttt_o(String fname, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, T, T, R> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), this.getType(), this.getType(), returnType, func, stringFunction);
    }

    public NodeFuncObjectObjectObjectObjectToObject<T, T, T, T, T> put_tttt_t(String fname, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, T, T, T> func) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), this.getType(), this.getType(), this.getType(), func);
    }

    public NodeFuncObjectObjectObjectObjectToObject<T, T, T, T, T> put_tttt_t(String fname, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<T, T, T, T, T> func, StringFunctionPenta stringFunction) {
        return this.put_oooo_o(fname, this.getType(), this.getType(), this.getType(), this.getType(), this.getType(), func, stringFunction);
    }
}
