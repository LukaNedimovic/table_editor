package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;

/**
 * Min is a function resembling standard mathematical minimum value function, i.e. min(2, 3) = 2.
 */
public class Max extends Function {
    /**
     * Creates an instance of Max, given its id.
     * @param id          Function identifier.
     * @throws Exception  In case of invalid number of parameters.
     */
    public Max(String id) throws Exception {
        super(id, Double.class, Double.class, Double.class);
    }

    /**
     * Performs standard mathematical maximum value function.
     * @param args       Array of objects passed into given function. Its length must be equal to 2.
     * @return           Maximum among two given arguments.
     * @throws Exception In case of invalid number of arguments or invalid values.
     */
    @Override
    public Object evaluate(Object... args) throws Exception {
        if (args.length != 2) {
            throw new Exception("Max(double, double): Invalid number of arguments");
        }

        Object base     = args[0];
        Object exponent = args[1];

        if (!validValue(base) || !validValue(exponent)) {
            throw new Exception("Max(double, double) -> double: Invalid values provided.");
        }

        if (base instanceof String) {
            base = Double.parseDouble((String) base);
        }
        if (exponent instanceof String) {
            exponent = Double.parseDouble((String) exponent);
        }

        // Cast to doubles, so values become compatible with Math.pow(double, double)
        Object result = Math.max((Double) base, (Double) exponent);

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
