package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeInteger;
import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;

/**
 * LCM is a function resembling standard mathematical lowest common multiple, i.e. lcm(2, 5) = 10.
 */
public class LCM extends Function {
    /**
     * Creates an instance of Pow, given its id.
     * @param id          Function identifier.
     * @throws Exception  In case of invalid number of parameters.
     */
    public LCM(String id) throws Exception {
        super(id);
    }

    /**
     * Calculates lowest common multiple among two integers.
     * @param left  Left operand
     * @param right Right operand
     * @return      Lowest common multiple
     */
    public DTypeInteger lcm(DTypeInteger left, DTypeInteger right) {
        Integer leftValue  = left.getValue();
        Integer rightValue = right.getValue();

        // LCM = (P * Q) / GCD(P, Q), according to standard mathematical formula
        Integer gcdResult = _gcd(leftValue, rightValue);
        Integer lcmResult = (leftValue * rightValue) / gcdResult;

        return new DTypeInteger(lcmResult);
    }

    /**
     * Helper functiono that calculates the greatest common divisor among two numbers.
     * @param a Left operand
     * @param b Right operand
     * @return  Lowest common multiple of left and right operand,
     */
    public Integer _gcd(Integer a, Integer b) {
        return (b == 0) ? a : _gcd(b, a % b);
    }
}
