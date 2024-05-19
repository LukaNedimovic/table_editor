package com.lnedimovic.table_editor.expression.function.functions;

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
        super(id, Double.class);
    }

    /**
     * @param args       Array of objects passed into given function. Its length must be equal to 0.
     * @return           Mathematical constant e = 3.141592653589793.
     * @throws Exception In case of invalid number of arguments or invalid values.
     */
    @Override
    public Object evaluate(Object... args) throws Exception {
        if (args.length != 0) {
            throw new Exception("ConstPI() -> double: Function doesn't accept any arguments.");
        }

        Object result = Math.PI;

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
