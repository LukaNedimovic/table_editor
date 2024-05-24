package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeBoolean;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeDouble;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeInteger;
import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Abs is a function resembling standard mathematical absolute value function, i.e. abs(-5) = abs(5) = 5.
 */
public class Abs extends Function {
    /**
     * Creates an instance of Abs, given its id.
     * @param id          Function identifier.
     * @throws Exception  In case of invalid number of parameters.
     */
    public Abs(String id) throws Exception {
        super(id);
    }

    /**
     * @param arg
     * @return    Absolute value of operand
     */
    public DType<?> abs(DTypeDouble arg) {
        return new DTypeDouble(Math.abs(arg.getValue()));
    }

    /**
     * @param arg
     * @return    Absolute value of operand
     */
    public DType<?> abs(DTypeInteger arg) {
        return new DTypeInteger(Math.abs(arg.getValue()));
    }

    /**
     * @param arg
     * @return    Absolute value of operand
     */
    public DType<?> abs(DTypeBoolean arg) {
        return new DTypeBoolean(Math.abs(arg.getIntegerValue()));
    }
}
