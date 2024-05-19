package com.lnedimovic.table_editor.expression.operation.operations.binary;

import com.lnedimovic.table_editor.expression.operation.Operation;

/**
 * <code>GreaterThan</code> is a logical operation that returns 1.0 if the expression is true; 0, otherwise.
 * Standard language uses ">" (greater-than sign) to annotate it, i.e. 5 > 3 ( = 1.0), -2 > 10 ( = 0.0).
 */
public class GreaterThan extends Operation {
    /**
     * Creates an instance of <code>LessThan</code> operation.
     * @param id         Id (representation symbol), i.e. "<".
     * @param precedence Precedence of given operation.
     */
    public GreaterThan(String id, int precedence) {
        super(id, precedence);
    }

    /**
     * Performs standard mathematical addition on two given arguments.
     * @param args        Array of objects passed into given operation. For unary, it is a singular object; for binary, there are two.
     * @return            Boolean evaluation of two arguments - 1.0 if first less than the second; 0.0, otherwise.
     * @throws Exception  In case of invalid number of arguments or invalid value provided.
     */
    @Override
    public String evaluate(Object... args) throws Exception {
        if (args.length != 2) {
            throw new Exception("GreaterThan must be provided two values.");
        }

        Object left = args[0];
        Object right = args[1];
        if (!validValue(left) || !validValue(right)) {
            throw new Exception("GreaterThan: Invalid values provided");
        }

        left = Double.parseDouble(left.toString());
        right = Double.parseDouble(right.toString());

        double result = ((Double) left > (Double) right) ? 1.0 : 0;;

        return Double.toString(result);
    }

    public boolean validValue(Object obj) {
        return true;
    }
}
