package com.lnedimovic.table_editor.expression.operation.operations.binary;

import com.lnedimovic.table_editor.expression.operation.Operation;
/**
 * <code>Multiplication</code> is an operation that performs standard mathematical multiplication to given data.
 * Standard math language uses "*" (asterisk) to annotate it, i.e. 5 * 3 ( = 15).
 */
public class Multiplication extends Operation {
    /**
     * Creates an instance of <code>Multiplication</code> operation.
     * @param id         Id (representation symbol), i.e. "*".
     * @param precedence Precedence of given operation.
     */
    public Multiplication(String id, int precedence) {
        super(id, precedence);
    }

    /**
     * Performs standard mathematical multiplication on two given arguments
     * @param args        Array of objects passed into given operation. For unary, it is a singular object; for binary, there are two.
     * @return            Product of two given arguments.
     * @throws Exception  In case of invalid number of arguments or invalid value provided.
     */
    @Override
    public String evaluate(Object... args) throws Exception {
        if (args.length != 2) {
            throw new Exception("Multiplication must be provided two values.");
        }

        Object left  = args[0];
        Object right = args[1];
        if (!validValue(left) || !validValue(right)) {
            throw new Exception("Multiplication: Invalid values provided");
        }

        left = Double.parseDouble(left.toString());
        right = Double.parseDouble(right.toString());

        double result = (Double)left * (Double)right;

        return Double.toString(result);
    }

    public boolean validValue(Object obj) {
        return true;
    }
}
