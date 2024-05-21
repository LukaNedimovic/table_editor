package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeBoolean;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeDouble;
import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * IFEQ is a function resembling standard equal-condition, i.e. ifeq(5, 3) = false, ifeq(25.0, pow(2, 5)) = true.
 */
public class IFEQ extends Function {
    /**
     * Creates an instance of Pow, given its id.
     * @param id          Function identifier.
     * @throws Exception  In case of invalid number of parameters.
     */
    public IFEQ(String id) throws Exception {
        super(id);
    }

    public DTypeBoolean ifeq(DType<?> left, DType<?> right) {
        return new DTypeBoolean(left.equals(right));
    }
}
