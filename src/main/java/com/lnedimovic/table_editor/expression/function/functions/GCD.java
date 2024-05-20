package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeInteger;
import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;

/**
 * GCD is a function resembling standard greatest common divisor, i.e. gcd(20, 10) = 10.
 */
public class GCD extends Function {
    /**
     * Creates an instance of Pow, given its id.
     * @param id          Function identifier.
     * @throws Exception  In case of invalid number of parameters.
     */
    public GCD(String id) throws Exception {
        super(id, DTypeInteger.class, DTypeInteger.class, DTypeInteger.class);
    }

    /**
     * Performs standard mathematical exponentiation.
     * @param args       Array of objects passed into given function. Its length must be equal to 2.
     * @return           Greatest common divisor of given arguments.
     * @throws Exception In case of invalid number of arguments or invalid values.
     */
    @Override
    public DType<?> evaluate(DType<?>... args) throws Exception {
        if (args.length != 2) {
            throw new Exception("GCD(DTypeInteger, DTypeInteger) -> DTypeInteger: Invalid number of arguments");
        }

        convertToValidDTypes(args);

        DTypeInteger left  = (DTypeInteger) args[0];
        DTypeInteger right = (DTypeInteger) args[1];

        // Call the helper function to calculate the GCD
        DTypeInteger result = new DTypeInteger(gcd(left.getValue(), right.getValue()));

        Class<?> returnType = getReturnType();
        Constructor<?> constructor = returnType.getConstructor(DTypeInteger.class);

        // Create an instance
        return (DType<?>) constructor.newInstance(result);
    }

    public Integer gcd(Integer a, Integer b) { return (b == 0) ? a : gcd(b, a%b); }

    public boolean validValue(Object obj) {
        return true;
    }
}
