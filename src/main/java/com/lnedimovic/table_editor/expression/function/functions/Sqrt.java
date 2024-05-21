package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeDouble;
import com.lnedimovic.table_editor.expression.function.Function;

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
        super(id, DTypeDouble.class, DTypeDouble.class);
    }

    /**
     * Performs standard mathematical square root.
     * @param args       Array of objects passed into given function. Its length must be equal to 2.
     * @return           Square root of given argument.
     * @throws Exception In case of invalid number of arguments or invalid values.
     */
    @Override
    public DType<?> evaluate(DType<?>... args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Sqrt(DTypeDouble) -> DTypeDouble: Function accepts only one argument");
        }

        convertToValidDTypes(args);

        DTypeDouble operand = (DTypeDouble) args[0];
        if (operand.getValue() < 0) {
            throw new Exception("Sqrt(DTypeDouble) -> DTypeDouble: Function does not accept negative values.");
        }

        DTypeDouble result = new DTypeDouble(Math.sqrt(operand.getValue()));

        Class<?> returnType = getReturnType();
        Constructor<?> constructor = returnType.getConstructor((Class<?>) getParameterTypes()[0]);

        // Create an instance
        return (DType<?>) constructor.newInstance(result);
    }

    public boolean validValue(Object obj) {
        return true; //
    }
}
