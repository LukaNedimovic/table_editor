package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeDouble;
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
        super(id, DTypeDouble.class, DTypeDouble.class, DTypeDouble.class);
    }

    /**
     * Performs standard mathematical maximum value function.
     * @param args       Array of objects passed into given function. Its length must be equal to 2.
     * @return           Maximum among two given arguments.
     * @throws Exception In case of invalid number of arguments or invalid values.
     */
    @Override
    public DType<?> evaluate(DType<?>... args) throws Exception {
        if (args.length != 2) {
            throw new Exception("Max(DTypeDouble, DTypeDouble) -> DTypeDouble: Invalid number of arguments");
        }

        convertToValidDTypes(args);

        DTypeDouble left  = (DTypeDouble) args[0];
        DTypeDouble right = (DTypeDouble) args[1];

        // Cast to doubles, so values become compatible with Math.pow(double, double)
        DTypeDouble result = new DTypeDouble(Math.max(left.getValue(), right.getValue()));

        Class<?> returnType = getReturnType();
        Constructor<?> constructor = returnType.getConstructor(DTypeDouble.class);

        // Create an instance
        return (DType<?>) constructor.newInstance(result);
    }

    public boolean validValue(Object obj) {
        return true;
    }
}
