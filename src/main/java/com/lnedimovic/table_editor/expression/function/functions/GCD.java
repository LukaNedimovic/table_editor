package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeInteger;
import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;

/**
 * GCD is a function resembling standard greatest common divisor, i.e. gcd(20, 10) = 10.
 */
public class GCD extends Function {
    /**
     * Creates an instance of Pow, given its id.
     *
     * @param id Function identifier.
     * @throws Exception In case of invalid number of parameters.
     */
    public GCD(String id) throws Exception {
        super(id);
    }

    public DTypeInteger gcd(DTypeInteger left, DTypeInteger right) {
        return new DTypeInteger(_gcd(left.getValue(), right.getValue()));
    }

    public Integer _gcd(Integer a, Integer b) {
        return (b == 0) ? a : _gcd(b, a % b);
    }

}