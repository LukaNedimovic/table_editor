package main.java.com.lnedimovic.table_editor.expression.function.functions;

import main.java.com.lnedimovic.table_editor.expression.function.Function;

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
        super(id, Double.class, Double.class);
    }

    /**
     * Performs standard mathematical square root.
     * @param args       Array of objects passed into given function. Its length must be equal to 2.
     * @return           Square root of given argument.
     * @throws Exception In case of invalid number of arguments or invalid values.
     */
    @Override
    public Object evaluate(Object... args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Sqrt(double) -> double: Function accepts only one argument");
        }

        Object value = args[0];
        if (!validValue(value)) {
            throw new Exception("Sqrt(double) -> double: Invalid values provided.");
        }

        if (value instanceof String) {
            value = Double.parseDouble((String) value);
        }

        Object result = Math.sqrt((Double) value);

        Class<?> returnType = getReturnType();
        Class<?> primitiveTypeForConstructor = getPrimitiveType(returnType);
        Constructor<?> constructor = returnType.getConstructor(primitiveTypeForConstructor);

        // Create an instance
        return constructor.newInstance(result);
    }

    public boolean validValue(Object obj) {
        return true; //
    }
}
