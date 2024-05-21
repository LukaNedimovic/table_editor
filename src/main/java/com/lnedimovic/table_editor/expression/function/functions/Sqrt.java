package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeBoolean;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeDouble;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeInteger;
import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;
/**
 * Sqrt is a function resembling standard mathematical square root, i.e. sqrt(16) = 4.
 */
public class Sqrt extends Function {
    /**
     * Creates an instance of Sqrt, given its id.
     * @param id          Function identifier.
     * @throws Exception  In case of invalid number of parameters.
     */
    public Sqrt(String id) throws Exception {
        super(id);
    }

    public DTypeDouble sqrt(DTypeDouble arg) {
        return new DTypeDouble(Math.sqrt(arg.getValue()));
    }
    public DTypeInteger sqrt(DTypeInteger arg) {
        return new DTypeInteger(Math.sqrt(arg.getValue()));
    }
    public DTypeBoolean sqrt(DTypeBoolean arg) {
        return new DTypeBoolean(Math.sqrt(arg.getIntegerValue()));
    }
}
