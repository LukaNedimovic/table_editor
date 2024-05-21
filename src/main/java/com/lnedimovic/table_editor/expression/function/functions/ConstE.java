package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeDouble;
import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;

/**
 * ConstE is a function that returns a mathematical constant e = 2.718281828459045.
 */
public class ConstE extends Function {
    /**
     * Constructs an instance of ConstE, given its id.
     * @param id          Function identifier.
     * @throws Exception  In case of invalid number of parameters.
     */
    public ConstE(String id) throws Exception {
        super(id);
    }

    public DTypeDouble e() {
        return new DTypeDouble(Math.E);
    }
}
