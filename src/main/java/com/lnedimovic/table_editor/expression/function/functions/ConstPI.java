package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
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
        super(id, DTypeDouble.class);
    }

    /**
     * @param args       Array of objects passed into given function. Its length must be equal to 0.
     * @return           Mathematical constant e = 3.141592653589793.
     * @throws Exception In case of invalid number of arguments or invalid values.
     */
    @Override
    public DType<?> evaluate(DType<?>... args) throws Exception {
        if (args.length != 0) {
            throw new Exception("ConstPI() -> DTypeDouble: Function doesn't accept any arguments.");
        }

        convertToValidDTypes(args);

        DTypeDouble result = new DTypeDouble(Math.PI);

        Class<?> returnType = getReturnType();
        Constructor<?> constructor = returnType.getConstructor(DTypeDouble.class);

        // Create an instance
        return (DType<?>) constructor.newInstance(result);
    }

    public boolean validValue(Object obj) {
        return true;
    }
}
