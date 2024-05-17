package main.java.com.lnedimovic.table_editor.expression.operation.operations.binary;

import main.java.com.lnedimovic.table_editor.expression.operation.Operation;

/**
 * <code>Exponentiation</code> is an operation that performs standard mathematical exponentiation to given data.
 * Standard math language uses "^" (caret) to annotate it, i.e. 5 ^ 3 ( = 125).
 */
public class Exponentiation extends Operation {
    /**
     * Creates an instance of <code>Exponentiation</code> operation.
     * @param id         Id (representation symbol), i.e. "^".
     * @param precedence Precedence of given operation.
     */
    public Exponentiation(String id, int precedence) {
        super(id, precedence);
    }

    /**
     * Performs standard exponentiation on two given arguments.
     * @param args        Array of objects passed into given operation. For unary, it is a singular object; for binary, there are two.
     * @return            Exponentiation (the power) of two given arguments.
     * @throws Exception  In case of invalid number of arguments or invalid value provided.
     */
    @Override
    public String evaluate(Object... args) throws Exception {
        if (args.length != 2) {
            throw new Exception("Exponentiation must be provided two values.");
        }

        Object left  = args[0];
        Object right = args[1];
        if (!validValue(left) || !validValue(right)) {
            throw new Exception("Exponentiation: Invalid values provided");
        }

        left  = Double.parseDouble(left.toString());
        right = Double.parseDouble(right.toString());

        double result = Math.pow((Double)left, (Double)right);

        return Double.toString(result);
    }

    public boolean validValue(Object obj) {
        return true;
    }
}
