package com.lnedimovic.table_editor.expression.function.functions;

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
        super(id, Double.class);
    }

    /**
     * @param args       Array of objects passed into given function. Its length must be equal to 0.
     * @return           Mathematical constant e = 2.718281828459045.
     * @throws Exception In case of invalid number of arguments or invalid values.
     */
    @Override
    public Object evaluate(Object... args) throws Exception {
        if (args.length != 0) {
            throw new Exception("ConstE() -> double: Function doesn't accept any arguments.");
        }

        Object result = Math.E;

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
