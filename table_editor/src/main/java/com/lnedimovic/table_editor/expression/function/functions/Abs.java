package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;

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
        super(id, Double.class, Double.class);
    }

    /**
     * Performs standard mathematical absolute value function.
     * @param args       Array of objects passed into given function. Its length must be equal to 1.
     * @return           Absolute value of given argument.
     * @throws Exception In case of invalid number of arguments or invalid values.
     */
    @Override
    public Object evaluate(Object... args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Abs(double) -> double: Function accepts only one argument");
        }

        Object value = args[0];
        if (!validValue(value)) {
            throw new Exception("Abs(double) -> double: Invalid value provided.");
        }

        if (value instanceof String) {
            value = Double.parseDouble((String) value);
        }

        Object result = Math.abs((Double) value);

        Class<?> returnType = getReturnType();
        Class<?> primitiveTypeForConstructor = getPrimitiveType(returnType);
        Constructor<?> constructor = returnType.getConstructor(primitiveTypeForConstructor);

        // Create an instance
        return constructor.newInstance(result);
    }

    public boolean validValue(Object obj) {
        return true;
    }
}
