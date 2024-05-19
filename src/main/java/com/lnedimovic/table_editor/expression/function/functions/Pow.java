package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.DTypeDouble;
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
        super(id, DTypeDouble.class, DTypeDouble.class, DTypeDouble.class);
    }

    /**
     * Performs standard mathematical exponentiation.
     * @param args       Array of objects passed into given function. Its length must be equal to 2.
     * @return           Exponentiation of given arguments.
     * @throws Exception In case of invalid number of arguments or invalid values.
     */
    @Override
    public DType<?> evaluate(DType<?>... args) throws Exception {
        if (args.length != 2) {
            throw new Exception("Pow(DTypeDouble, DTypeDouble) -> DTypeDouble: Invalid number of arguments");
        }

        convertToValidDTypes(args);

        DTypeDouble base     = (DTypeDouble) args[0];
        DTypeDouble exponent = (DTypeDouble) args[1];

        // Cast to doubles, so values become compatible with Math.pow(double, double)
        DTypeDouble result = new DTypeDouble(Math.pow(base.getValue(), exponent.getValue()));

        Class<?> returnType = getReturnType();
        Constructor<?> constructor = returnType.getConstructor(DTypeDouble.class);

        System.out.println(constructor);

        // Create an instance
        return (DType<?>) constructor.newInstance(result);
    }

    public boolean validValue(Object obj) {
        return true;
    }
}
