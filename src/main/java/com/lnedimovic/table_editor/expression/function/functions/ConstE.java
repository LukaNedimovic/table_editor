package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.DTypeDouble;
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
        super(id, DTypeDouble.class);
    }

    /**
     * @param args       Array of objects passed into given function. Its length must be equal to 0.
     * @return           Mathematical constant e = 2.718281828459045.
     * @throws Exception In case of invalid number of arguments or invalid values.
     */
    @Override
    public DType<?> evaluate(DType<?>... args) throws Exception {
        if (args.length != 0) {
            throw new Exception("ConstE() -> DTypeDouble: Function doesn't accept any arguments.");
        }

        convertToValidDTypes(args);

        DTypeDouble result = new DTypeDouble(Math.E);

        Class<?> returnType = getReturnType();
        Constructor<?> constructor = returnType.getConstructor(DTypeDouble.class);

        // Create an instance
        return (DType<?>) constructor.newInstance(result);
    }

    public boolean validValue(Object obj) {
        return true;
    }
}
