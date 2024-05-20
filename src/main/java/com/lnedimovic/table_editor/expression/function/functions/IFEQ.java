package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeBoolean;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeDouble;
import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;

/**
 * IFEQ is a function resembling standard equal-condition, i.e. ifeq(5, 3) = false, ifeq(25.0, pow(2, 5)) = true.
 */
public class IFEQ extends Function {
    /**
     * Creates an instance of Pow, given its id.
     * @param id          Function identifier.
     * @throws Exception  In case of invalid number of parameters.
     */
    public IFEQ(String id) throws Exception {
        super(id, DType.class, DType.class, DTypeBoolean.class);
    }

    /**
     * Performs standard mathematical exponentiation.
     * @param args       Array of objects passed into given function. Its length must be equal to 2.
     * @return           Whether given arguments are equal.
     * @throws Exception In case of invalid number of arguments or invalid values.
     */
    @Override
    public DType<?> evaluate(DType<?>... args) throws Exception {
        if (args.length != 2) {
            throw new Exception("IFEQ(DType<?>, DType<?>) -> DTypeBoolean: Invalid number of arguments");
        }

        // Conversion does not happen in this case, any DType<?> is acceptable
//        convertToValidDTypes(args);

        DType<?> left  = args[0];
        DType<?> right = args[1];

        // Compare them using the built-in .equals(Object obj) function.
        DTypeBoolean result = new DTypeBoolean(left.equals(right));

        Class<?> returnType = getReturnType();
        Constructor<?> constructor = returnType.getConstructor(DTypeBoolean.class);

        // Create an instance
        return (DType<?>) constructor.newInstance(result);
    }

    public boolean validValue(Object obj) {
        return true;
    }
}
