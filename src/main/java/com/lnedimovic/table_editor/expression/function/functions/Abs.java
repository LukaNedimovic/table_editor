package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.DTypeDouble;
import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;
import java.security.spec.RSAOtherPrimeInfo;

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
        super(id, DTypeDouble.class, DTypeDouble.class);
    }

    /**
     * Performs standard mathematical absolute value function.
     * @param args       Array of objects passed into given function. Its length must be equal to 1.
     * @return           Absolute value of given argument.
     * @throws Exception In case of invalid number of arguments or invalid values.
     */
    @Override
    public DType<?> evaluate(DType<?>... args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Abs(DTypeDouble) -> DTypeDouble: Function accepts only one argument");
        }

        convertToValidDTypes(args);

        DTypeDouble operand = (DTypeDouble) args[0];

        DTypeDouble result = new DTypeDouble(Math.abs(operand.getValue()));

        Class<?> returnType = getReturnType();
        Constructor<?> constructor = returnType.getConstructor(DTypeDouble.class);

        // Create an instance
        return (DType<?>) constructor.newInstance(result);
    }

    public boolean validValue(Object obj) {
        return true;
    }
}
