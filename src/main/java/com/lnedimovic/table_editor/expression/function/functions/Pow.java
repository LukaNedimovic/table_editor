package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeDouble;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeInteger;
import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;

/**
 * Pow is a function resembling standard mathematical exponentiation, i.e. pow(2, 3) = 2 ^ 3 = 8
 */
public class Pow extends Function {
    /**
     * Creates an instance of Pow, given its id.
     * @param id          Function identifier.
     * @throws Exception  In case of invalid number of parameters.
     */
    public Pow(String id) throws Exception {
        super(id);
    }

    public DType<?> pow(DType<?> base, DType<?> exponent) throws Exception {
        return base.exp(exponent);
    }
}
