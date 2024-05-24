package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.dtypes.DTypeDouble;
import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;

/**
 * ConstPI is a function that returns a mathematical constant pi = 3.141592653589793.
 */
public class ConstPI extends Function {
    /**
     * Constructs an instance of ConstPI, given its id.
     * @param id          Function identifier.
     * @throws Exception  In case of invalid number of parameters.
     */
    public ConstPI(String id) throws Exception {
        super(id);
    }

    /**
     * @return Math.PI = 3.141592653589793
     */
    public DTypeDouble pi() {
        return new DTypeDouble(Math.PI);
    }
}
