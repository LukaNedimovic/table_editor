package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeInteger;
import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;

/**
 * LCM is a function resembling standard mathematical largest common multiple, i.e. lcm(2, 5) = 10.
 */
public class LCM extends Function {
    /**
     * Creates an instance of Pow, given its id.
     * @param id          Function identifier.
     * @throws Exception  In case of invalid number of parameters.
     */
    public LCM(String id) throws Exception {
        super(id, DTypeInteger.class, DTypeInteger.class, DTypeInteger.class);
    }

    /**
     * Performs standard mathematical exponentiation.
     * @param args       Array of objects passed into given function. Its length must be equal to 2.
     * @return           Lowest common multiple of given arguments.
     * @throws Exception In case of invalid number of arguments or invalid values.
     */
    @Override
    public DType<?> evaluate(DType<?>... args) throws Exception {
        if (args.length != 2) {
            throw new Exception("LCM(DTypeInteger, DTypeInteger) -> DTypeInteger: Invalid number of arguments");
        }

        convertToValidDTypes(args);

        DTypeInteger left  = (DTypeInteger) args[0];
        DTypeInteger right = (DTypeInteger) args[1];

        Integer leftValue  = left.getValue();
        Integer rightValue = right.getValue();

        // LCM(left, right) is calculated as: LCM(left, right) = (left * right) / gcd(left, right)
        DTypeInteger result = new DTypeInteger((leftValue * rightValue) / gcd(leftValue, rightValue));

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
