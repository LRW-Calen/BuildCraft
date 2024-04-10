package buildcraft.lib.expression;


import buildcraft.lib.expression.api.INodeFunc;
import buildcraft.lib.expression.node.func.*;

public abstract class FunctionContextBase {
    public FunctionContextBase() {
    }

    protected abstract <F extends INodeFunc> F putFunction(String var1, F var2);

    public NodeFuncLongToLong put_l_l(String name, NodeFuncLongToLong.IFuncLongToLong func) {
        return (NodeFuncLongToLong)this.putFunction(name, new NodeFuncLongToLong(name, func));
    }

    public NodeFuncLongToLong put_l_l(String name, NodeFuncLongToLong.IFuncLongToLong func, StringFunctionBi stringFunction) {
        return (NodeFuncLongToLong)this.putFunction(name, new NodeFuncLongToLong(func, stringFunction));
    }

    public NodeFuncLongLongToLong put_ll_l(String name, NodeFuncLongLongToLong.IFuncLongLongToLong func) {
        return (NodeFuncLongLongToLong)this.putFunction(name, new NodeFuncLongLongToLong(name, func));
    }

    public NodeFuncLongLongToLong put_ll_l(String name, NodeFuncLongLongToLong.IFuncLongLongToLong func, StringFunctionTri stringFunction) {
        return (NodeFuncLongLongToLong)this.putFunction(name, new NodeFuncLongLongToLong(func, stringFunction));
    }

    public NodeFuncLongLongLongToLong put_lll_l(String name, NodeFuncLongLongLongToLong.IFuncLongLongLongToLong func) {
        return (NodeFuncLongLongLongToLong)this.putFunction(name, new NodeFuncLongLongLongToLong(name, func));
    }

    public NodeFuncLongLongLongToLong put_lll_l(String name, NodeFuncLongLongLongToLong.IFuncLongLongLongToLong func, StringFunctionQuad stringFunction) {
        return (NodeFuncLongLongLongToLong)this.putFunction(name, new NodeFuncLongLongLongToLong(func, stringFunction));
    }

    public NodeFuncDoubleToLong put_d_l(String name, NodeFuncDoubleToLong.IFuncDoubleToLong func) {
        return (NodeFuncDoubleToLong)this.putFunction(name, new NodeFuncDoubleToLong(name, func));
    }

    public NodeFuncDoubleToLong put_d_l(String name, NodeFuncDoubleToLong.IFuncDoubleToLong func, StringFunctionBi stringFunction) {
        return (NodeFuncDoubleToLong)this.putFunction(name, new NodeFuncDoubleToLong(func, stringFunction));
    }

    public NodeFuncBooleanToLong put_b_l(String name, NodeFuncBooleanToLong.IFuncBooleanToLong func) {
        return (NodeFuncBooleanToLong)this.putFunction(name, new NodeFuncBooleanToLong(name, func));
    }

    public NodeFuncBooleanToLong put_b_l(String name, NodeFuncBooleanToLong.IFuncBooleanToLong func, StringFunctionBi stringFunction) {
        return (NodeFuncBooleanToLong)this.putFunction(name, new NodeFuncBooleanToLong(func, stringFunction));
    }

    public <A> NodeFuncObjectToLong<A> put_o_l(String name, Class<A> argTypeA, NodeFuncObjectToLong.IFuncObjectToLong<A> func) {
        return (NodeFuncObjectToLong)this.putFunction(name, new NodeFuncObjectToLong(name, argTypeA, func));
    }

    public <A> NodeFuncObjectToLong<A> put_o_l(String name, Class<A> argTypeA, NodeFuncObjectToLong.IFuncObjectToLong<A> func, StringFunctionBi stringFunction) {
        return (NodeFuncObjectToLong)this.putFunction(name, new NodeFuncObjectToLong(argTypeA, func, stringFunction));
    }

    public <A> NodeFuncObjectLongToLong<A> put_ol_l(String name, Class<A> argTypeA, NodeFuncObjectLongToLong.IFuncObjectLongToLong<A> func) {
        return (NodeFuncObjectLongToLong)this.putFunction(name, new NodeFuncObjectLongToLong(name, argTypeA, func));
    }

    public <A> NodeFuncObjectLongToLong<A> put_ol_l(String name, Class<A> argTypeA, NodeFuncObjectLongToLong.IFuncObjectLongToLong<A> func, StringFunctionTri stringFunction) {
        return (NodeFuncObjectLongToLong)this.putFunction(name, new NodeFuncObjectLongToLong(argTypeA, func, stringFunction));
    }

    public <A> NodeFuncObjectLongLongToLong<A> put_oll_l(String name, Class<A> argTypeA, NodeFuncObjectLongLongToLong.IFuncObjectLongLongToLong<A> func) {
        return (NodeFuncObjectLongLongToLong)this.putFunction(name, new NodeFuncObjectLongLongToLong(name, argTypeA, func));
    }

    public <A> NodeFuncObjectLongLongToLong<A> put_oll_l(String name, Class<A> argTypeA, NodeFuncObjectLongLongToLong.IFuncObjectLongLongToLong<A> func, StringFunctionQuad stringFunction) {
        return (NodeFuncObjectLongLongToLong)this.putFunction(name, new NodeFuncObjectLongLongToLong(argTypeA, func, stringFunction));
    }

    public <A, B> NodeFuncObjectObjectToLong<A, B> put_oo_l(String name, Class<A> argTypeA, Class<B> argTypeB, NodeFuncObjectObjectToLong.IFuncObjectObjectToLong<A, B> func) {
        return (NodeFuncObjectObjectToLong)this.putFunction(name, new NodeFuncObjectObjectToLong(name, argTypeA, argTypeB, func));
    }

    public <A, B> NodeFuncObjectObjectToLong<A, B> put_oo_l(String name, Class<A> argTypeA, Class<B> argTypeB, NodeFuncObjectObjectToLong.IFuncObjectObjectToLong<A, B> func, StringFunctionTri stringFunction) {
        return (NodeFuncObjectObjectToLong)this.putFunction(name, new NodeFuncObjectObjectToLong(argTypeA, argTypeB, func, stringFunction));
    }

    public NodeFuncLongToDouble put_l_d(String name, NodeFuncLongToDouble.IFuncLongToDouble func) {
        return (NodeFuncLongToDouble)this.putFunction(name, new NodeFuncLongToDouble(name, func));
    }

    public NodeFuncLongToDouble put_l_d(String name, NodeFuncLongToDouble.IFuncLongToDouble func, StringFunctionBi stringFunction) {
        return (NodeFuncLongToDouble)this.putFunction(name, new NodeFuncLongToDouble(func, stringFunction));
    }

    public NodeFuncDoubleToDouble put_d_d(String name, NodeFuncDoubleToDouble.IFuncDoubleToDouble func) {
        return (NodeFuncDoubleToDouble)this.putFunction(name, new NodeFuncDoubleToDouble(name, func));
    }

    public NodeFuncDoubleToDouble put_d_d(String name, NodeFuncDoubleToDouble.IFuncDoubleToDouble func, StringFunctionBi stringFunction) {
        return (NodeFuncDoubleToDouble)this.putFunction(name, new NodeFuncDoubleToDouble(func, stringFunction));
    }

    public NodeFuncDoubleDoubleToDouble put_dd_d(String name, NodeFuncDoubleDoubleToDouble.IFuncDoubleDoubleToDouble func) {
        return (NodeFuncDoubleDoubleToDouble)this.putFunction(name, new NodeFuncDoubleDoubleToDouble(name, func));
    }

    public NodeFuncDoubleDoubleToDouble put_dd_d(String name, NodeFuncDoubleDoubleToDouble.IFuncDoubleDoubleToDouble func, StringFunctionTri stringFunction) {
        return (NodeFuncDoubleDoubleToDouble)this.putFunction(name, new NodeFuncDoubleDoubleToDouble(func, stringFunction));
    }

    public NodeFuncDoubleDoubleDoubleToDouble put_ddd_d(String name, NodeFuncDoubleDoubleDoubleToDouble.IFuncDoubleDoubleDoubleToDouble func) {
        return (NodeFuncDoubleDoubleDoubleToDouble)this.putFunction(name, new NodeFuncDoubleDoubleDoubleToDouble(name, func));
    }

    public NodeFuncDoubleDoubleDoubleToDouble put_ddd_d(String name, NodeFuncDoubleDoubleDoubleToDouble.IFuncDoubleDoubleDoubleToDouble func, StringFunctionQuad stringFunction) {
        return (NodeFuncDoubleDoubleDoubleToDouble)this.putFunction(name, new NodeFuncDoubleDoubleDoubleToDouble(func, stringFunction));
    }

    public <A> NodeFuncObjectToDouble<A> put_o_d(String name, Class<A> argTypeA, NodeFuncObjectToDouble.IFuncObjectToDouble<A> func) {
        return (NodeFuncObjectToDouble)this.putFunction(name, new NodeFuncObjectToDouble(name, argTypeA, func));
    }

    public <A> NodeFuncObjectToDouble<A> put_o_d(String name, Class<A> argTypeA, NodeFuncObjectToDouble.IFuncObjectToDouble<A> func, StringFunctionBi stringFunction) {
        return (NodeFuncObjectToDouble)this.putFunction(name, new NodeFuncObjectToDouble(argTypeA, func, stringFunction));
    }

    public <A, B> NodeFuncObjectObjectToDouble<A, B> put_oo_d(String name, Class<A> argTypeA, Class<B> argTypeB, NodeFuncObjectObjectToDouble.IFuncObjectObjectToDouble<A, B> func) {
        return (NodeFuncObjectObjectToDouble)this.putFunction(name, new NodeFuncObjectObjectToDouble(name, argTypeA, argTypeB, func));
    }

    public <A, B> NodeFuncObjectObjectToDouble<A, B> put_oo_d(String name, Class<A> argTypeA, Class<B> argTypeB, NodeFuncObjectObjectToDouble.IFuncObjectObjectToDouble<A, B> func, StringFunctionTri stringFunction) {
        return (NodeFuncObjectObjectToDouble)this.putFunction(name, new NodeFuncObjectObjectToDouble(argTypeA, argTypeB, func, stringFunction));
    }

    public NodeFuncLongToBoolean put_l_b(String name, NodeFuncLongToBoolean.IFuncLongToBoolean func) {
        return (NodeFuncLongToBoolean)this.putFunction(name, new NodeFuncLongToBoolean(name, func));
    }

    public NodeFuncLongToBoolean put_l_b(String name, NodeFuncLongToBoolean.IFuncLongToBoolean func, StringFunctionBi stringFunction) {
        return (NodeFuncLongToBoolean)this.putFunction(name, new NodeFuncLongToBoolean(func, stringFunction));
    }

    public NodeFuncLongLongToBoolean put_ll_b(String name, NodeFuncLongLongToBoolean.IFuncLongLongToBoolean func) {
        return (NodeFuncLongLongToBoolean)this.putFunction(name, new NodeFuncLongLongToBoolean(name, func));
    }

    public NodeFuncLongLongToBoolean put_ll_b(String name, NodeFuncLongLongToBoolean.IFuncLongLongToBoolean func, StringFunctionTri stringFunction) {
        return (NodeFuncLongLongToBoolean)this.putFunction(name, new NodeFuncLongLongToBoolean(func, stringFunction));
    }

    public NodeFuncDoubleDoubleToBoolean put_dd_b(String name, NodeFuncDoubleDoubleToBoolean.IFuncDoubleDoubleToBoolean func) {
        return (NodeFuncDoubleDoubleToBoolean)this.putFunction(name, new NodeFuncDoubleDoubleToBoolean(name, func));
    }

    public NodeFuncDoubleDoubleToBoolean put_dd_b(String name, NodeFuncDoubleDoubleToBoolean.IFuncDoubleDoubleToBoolean func, StringFunctionTri stringFunction) {
        return (NodeFuncDoubleDoubleToBoolean)this.putFunction(name, new NodeFuncDoubleDoubleToBoolean(func, stringFunction));
    }

    public NodeFuncBooleanToBoolean put_b_b(String name, NodeFuncBooleanToBoolean.IFuncBooleanToBoolean func) {
        return (NodeFuncBooleanToBoolean)this.putFunction(name, new NodeFuncBooleanToBoolean(name, func));
    }

    public NodeFuncBooleanToBoolean put_b_b(String name, NodeFuncBooleanToBoolean.IFuncBooleanToBoolean func, StringFunctionBi stringFunction) {
        return (NodeFuncBooleanToBoolean)this.putFunction(name, new NodeFuncBooleanToBoolean(func, stringFunction));
    }

    public NodeFuncBooleanBooleanToBoolean put_bb_b(String name, NodeFuncBooleanBooleanToBoolean.IFuncBooleanBooleanToBoolean func) {
        return (NodeFuncBooleanBooleanToBoolean)this.putFunction(name, new NodeFuncBooleanBooleanToBoolean(name, func));
    }

    public NodeFuncBooleanBooleanToBoolean put_bb_b(String name, NodeFuncBooleanBooleanToBoolean.IFuncBooleanBooleanToBoolean func, StringFunctionTri stringFunction) {
        return (NodeFuncBooleanBooleanToBoolean)this.putFunction(name, new NodeFuncBooleanBooleanToBoolean(func, stringFunction));
    }

    public <A> NodeFuncObjectToBoolean<A> put_o_b(String name, Class<A> argTypeA, NodeFuncObjectToBoolean.IFuncObjectToBoolean<A> func) {
        return (NodeFuncObjectToBoolean)this.putFunction(name, new NodeFuncObjectToBoolean(name, argTypeA, func));
    }

    public <A> NodeFuncObjectToBoolean<A> put_o_b(String name, Class<A> argTypeA, NodeFuncObjectToBoolean.IFuncObjectToBoolean<A> func, StringFunctionBi stringFunction) {
        return (NodeFuncObjectToBoolean)this.putFunction(name, new NodeFuncObjectToBoolean(argTypeA, func, stringFunction));
    }

    public <A, B> NodeFuncObjectObjectToBoolean<A, B> put_oo_b(String name, Class<A> argTypeA, Class<B> argTypeB, NodeFuncObjectObjectToBoolean.IFuncObjectObjectToBoolean<A, B> func) {
        return (NodeFuncObjectObjectToBoolean)this.putFunction(name, new NodeFuncObjectObjectToBoolean(name, argTypeA, argTypeB, func));
    }

    public <A, B> NodeFuncObjectObjectToBoolean<A, B> put_oo_b(String name, Class<A> argTypeA, Class<B> argTypeB, NodeFuncObjectObjectToBoolean.IFuncObjectObjectToBoolean<A, B> func, StringFunctionTri stringFunction) {
        return (NodeFuncObjectObjectToBoolean)this.putFunction(name, new NodeFuncObjectObjectToBoolean(argTypeA, argTypeB, func, stringFunction));
    }

    public <R> NodeFuncLongToObject<R> put_l_o(String name, Class<R> returnType, NodeFuncLongToObject.IFuncLongToObject<R> func) {
        return (NodeFuncLongToObject)this.putFunction(name, new NodeFuncLongToObject(name, returnType, func));
    }

    public <R> NodeFuncLongToObject<R> put_l_o(String name, Class<R> returnType, NodeFuncLongToObject.IFuncLongToObject<R> func, StringFunctionBi stringFunction) {
        return (NodeFuncLongToObject)this.putFunction(name, new NodeFuncLongToObject(returnType, func, stringFunction));
    }

    public <R> NodeFuncLongLongToObject<R> put_ll_o(String name, Class<R> returnType, NodeFuncLongLongToObject.IFuncLongLongToObject<R> func) {
        return (NodeFuncLongLongToObject)this.putFunction(name, new NodeFuncLongLongToObject(name, returnType, func));
    }

    public <R> NodeFuncLongLongToObject<R> put_ll_o(String name, Class<R> returnType, NodeFuncLongLongToObject.IFuncLongLongToObject<R> func, StringFunctionTri stringFunction) {
        return (NodeFuncLongLongToObject)this.putFunction(name, new NodeFuncLongLongToObject(returnType, func, stringFunction));
    }

    public <R> NodeFuncLongLongLongToObject<R> put_lll_o(String name, Class<R> returnType, NodeFuncLongLongLongToObject.IFuncLongLongLongToObject<R> func) {
        return (NodeFuncLongLongLongToObject)this.putFunction(name, new NodeFuncLongLongLongToObject(name, returnType, func));
    }

    public <R> NodeFuncLongLongLongToObject<R> put_lll_o(String name, Class<R> returnType, NodeFuncLongLongLongToObject.IFuncLongLongLongToObject<R> func, StringFunctionQuad stringFunction) {
        return (NodeFuncLongLongLongToObject)this.putFunction(name, new NodeFuncLongLongLongToObject(returnType, func, stringFunction));
    }

    public <R> NodeFuncLongLongLongLongToObject<R> put_llll_o(String name, Class<R> returnType, NodeFuncLongLongLongLongToObject.IFuncLongLongLongLongToObject<R> func) {
        return (NodeFuncLongLongLongLongToObject)this.putFunction(name, new NodeFuncLongLongLongLongToObject(name, returnType, func));
    }

    public <R> NodeFuncLongLongLongLongToObject<R> put_llll_o(String name, Class<R> returnType, NodeFuncLongLongLongLongToObject.IFuncLongLongLongLongToObject<R> func, StringFunctionPenta stringFunction) {
        return (NodeFuncLongLongLongLongToObject)this.putFunction(name, new NodeFuncLongLongLongLongToObject(returnType, func, stringFunction));
    }

    public <R> NodeFuncDoubleToObject<R> put_d_o(String name, Class<R> returnType, NodeFuncDoubleToObject.IFuncDoubleToObject<R> func) {
        return (NodeFuncDoubleToObject)this.putFunction(name, new NodeFuncDoubleToObject(name, returnType, func));
    }

    public <R> NodeFuncDoubleToObject<R> put_d_o(String name, Class<R> returnType, NodeFuncDoubleToObject.IFuncDoubleToObject<R> func, StringFunctionBi stringFunction) {
        return (NodeFuncDoubleToObject)this.putFunction(name, new NodeFuncDoubleToObject(returnType, func, stringFunction));
    }

    public <R> NodeFuncDoubleDoubleToObject<R> put_dd_o(String name, Class<R> returnType, NodeFuncDoubleDoubleToObject.IFuncDoubleDoubleToObject<R> func) {
        return (NodeFuncDoubleDoubleToObject)this.putFunction(name, new NodeFuncDoubleDoubleToObject(name, returnType, func));
    }

    public <R> NodeFuncDoubleDoubleToObject<R> put_dd_o(String name, Class<R> returnType, NodeFuncDoubleDoubleToObject.IFuncDoubleDoubleToObject<R> func, StringFunctionTri stringFunction) {
        return (NodeFuncDoubleDoubleToObject)this.putFunction(name, new NodeFuncDoubleDoubleToObject(returnType, func, stringFunction));
    }

    public <R> NodeFuncDoubleDoubleDoubleToObject<R> put_ddd_o(String name, Class<R> returnType, NodeFuncDoubleDoubleDoubleToObject.IFuncDoubleDoubleDoubleToObject<R> func) {
        return (NodeFuncDoubleDoubleDoubleToObject)this.putFunction(name, new NodeFuncDoubleDoubleDoubleToObject(name, returnType, func));
    }

    public <R> NodeFuncDoubleDoubleDoubleToObject<R> put_ddd_o(String name, Class<R> returnType, NodeFuncDoubleDoubleDoubleToObject.IFuncDoubleDoubleDoubleToObject<R> func, StringFunctionQuad stringFunction) {
        return (NodeFuncDoubleDoubleDoubleToObject)this.putFunction(name, new NodeFuncDoubleDoubleDoubleToObject(returnType, func, stringFunction));
    }

    public <R> NodeFuncDoubleDoubleDoubleDoubleToObject<R> put_dddd_o(String name, Class<R> returnType, NodeFuncDoubleDoubleDoubleDoubleToObject.IFuncDoubleDoubleDoubleDoubleToObject<R> func) {
        return (NodeFuncDoubleDoubleDoubleDoubleToObject)this.putFunction(name, new NodeFuncDoubleDoubleDoubleDoubleToObject(name, returnType, func));
    }

    public <R> NodeFuncDoubleDoubleDoubleDoubleToObject<R> put_dddd_o(String name, Class<R> returnType, NodeFuncDoubleDoubleDoubleDoubleToObject.IFuncDoubleDoubleDoubleDoubleToObject<R> func, StringFunctionPenta stringFunction) {
        return (NodeFuncDoubleDoubleDoubleDoubleToObject)this.putFunction(name, new NodeFuncDoubleDoubleDoubleDoubleToObject(returnType, func, stringFunction));
    }

    public <R> NodeFuncBooleanToObject<R> put_b_o(String name, Class<R> returnType, NodeFuncBooleanToObject.IFuncBooleanToObject<R> func) {
        return (NodeFuncBooleanToObject)this.putFunction(name, new NodeFuncBooleanToObject(name, returnType, func));
    }

    public <R> NodeFuncBooleanToObject<R> put_b_o(String name, Class<R> returnType, NodeFuncBooleanToObject.IFuncBooleanToObject<R> func, StringFunctionBi stringFunction) {
        return (NodeFuncBooleanToObject)this.putFunction(name, new NodeFuncBooleanToObject(returnType, func, stringFunction));
    }

    public <A, R> NodeFuncObjectToObject<A, R> put_o_o(String name, Class<A> argTypeA, Class<R> returnType, NodeFuncObjectToObject.IFuncObjectToObject<A, R> func) {
        return (NodeFuncObjectToObject)this.putFunction(name, new NodeFuncObjectToObject(name, argTypeA, returnType, func));
    }

    public <A, R> NodeFuncObjectToObject<A, R> put_o_o(String name, Class<A> argTypeA, Class<R> returnType, NodeFuncObjectToObject.IFuncObjectToObject<A, R> func, StringFunctionBi stringFunction) {
        return (NodeFuncObjectToObject)this.putFunction(name, new NodeFuncObjectToObject(argTypeA, returnType, func, stringFunction));
    }

    public <A, R> NodeFuncObjectLongToObject<A, R> put_ol_o(String name, Class<A> argTypeA, Class<R> returnType, NodeFuncObjectLongToObject.IFuncObjectLongToObject<A, R> func) {
        return (NodeFuncObjectLongToObject)this.putFunction(name, new NodeFuncObjectLongToObject(name, argTypeA, returnType, func));
    }

    public <A, R> NodeFuncObjectLongToObject<A, R> put_ol_o(String name, Class<A> argTypeA, Class<R> returnType, NodeFuncObjectLongToObject.IFuncObjectLongToObject<A, R> func, StringFunctionTri stringFunction) {
        return (NodeFuncObjectLongToObject)this.putFunction(name, new NodeFuncObjectLongToObject(argTypeA, returnType, func, stringFunction));
    }

    public <A, R> NodeFuncObjectLongLongToObject<A, R> put_oll_o(String name, Class<A> argTypeA, Class<R> returnType, NodeFuncObjectLongLongToObject.IFuncObjectLongLongToObject<A, R> func) {
        return (NodeFuncObjectLongLongToObject)this.putFunction(name, new NodeFuncObjectLongLongToObject(name, argTypeA, returnType, func));
    }

    public <A, R> NodeFuncObjectLongLongToObject<A, R> put_oll_o(String name, Class<A> argTypeA, Class<R> returnType, NodeFuncObjectLongLongToObject.IFuncObjectLongLongToObject<A, R> func, StringFunctionQuad stringFunction) {
        return (NodeFuncObjectLongLongToObject)this.putFunction(name, new NodeFuncObjectLongLongToObject(argTypeA, returnType, func, stringFunction));
    }

    public <A, R> NodeFuncObjectBooleanToObject<A, R> put_ob_o(String name, Class<A> argTypeA, Class<R> returnType, NodeFuncObjectBooleanToObject.IFuncObjectBooleanToObject<A, R> func) {
        return (NodeFuncObjectBooleanToObject)this.putFunction(name, new NodeFuncObjectBooleanToObject(name, argTypeA, returnType, func));
    }

    public <A, R> NodeFuncObjectBooleanToObject<A, R> put_ob_o(String name, Class<A> argTypeA, Class<R> returnType, NodeFuncObjectBooleanToObject.IFuncObjectBooleanToObject<A, R> func, StringFunctionTri stringFunction) {
        return (NodeFuncObjectBooleanToObject)this.putFunction(name, new NodeFuncObjectBooleanToObject(argTypeA, returnType, func, stringFunction));
    }

    public <A, B, R> NodeFuncObjectObjectToObject<A, B, R> put_oo_o(String name, Class<A> argTypeA, Class<B> argTypeB, Class<R> returnType, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<A, B, R> func) {
        return (NodeFuncObjectObjectToObject)this.putFunction(name, new NodeFuncObjectObjectToObject(name, argTypeA, argTypeB, returnType, func));
    }

    public <A, B, R> NodeFuncObjectObjectToObject<A, B, R> put_oo_o(String name, Class<A> argTypeA, Class<B> argTypeB, Class<R> returnType, NodeFuncObjectObjectToObject.IFuncObjectObjectToObject<A, B, R> func, StringFunctionTri stringFunction) {
        return (NodeFuncObjectObjectToObject)this.putFunction(name, new NodeFuncObjectObjectToObject(argTypeA, argTypeB, returnType, func, stringFunction));
    }

    public <A, B, C, R> NodeFuncObjectObjectObjectToObject<A, B, C, R> put_ooo_o(String name, Class<A> argTypeA, Class<B> argTypeB, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, B, C, R> func) {
        return (NodeFuncObjectObjectObjectToObject)this.putFunction(name, new NodeFuncObjectObjectObjectToObject(name, argTypeA, argTypeB, argTypeC, returnType, func));
    }

    public <A, B, C, R> NodeFuncObjectObjectObjectToObject<A, B, C, R> put_ooo_o(String name, Class<A> argTypeA, Class<B> argTypeB, Class<C> argTypeC, Class<R> returnType, NodeFuncObjectObjectObjectToObject.IFuncObjectObjectObjectToObject<A, B, C, R> func, StringFunctionQuad stringFunction) {
        return (NodeFuncObjectObjectObjectToObject)this.putFunction(name, new NodeFuncObjectObjectObjectToObject(argTypeA, argTypeB, argTypeC, returnType, func, stringFunction));
    }

    public <A, B, C, D, R> NodeFuncObjectObjectObjectObjectToObject<A, B, C, D, R> put_oooo_o(String name, Class<A> argTypeA, Class<B> argTypeB, Class<C> argTypeC, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, C, D, R> func) {
        return (NodeFuncObjectObjectObjectObjectToObject)this.putFunction(name, new NodeFuncObjectObjectObjectObjectToObject(name, argTypeA, argTypeB, argTypeC, argTypeD, returnType, func));
    }

    public <A, B, C, D, R> NodeFuncObjectObjectObjectObjectToObject<A, B, C, D, R> put_oooo_o(String name, Class<A> argTypeA, Class<B> argTypeB, Class<C> argTypeC, Class<D> argTypeD, Class<R> returnType, NodeFuncObjectObjectObjectObjectToObject.IFuncObjectObjectObjectObjectToObject<A, B, C, D, R> func, StringFunctionPenta stringFunction) {
        return (NodeFuncObjectObjectObjectObjectToObject)this.putFunction(name, new NodeFuncObjectObjectObjectObjectToObject(argTypeA, argTypeB, argTypeC, argTypeD, returnType, func, stringFunction));
    }
}
