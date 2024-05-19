package com.lnedimovic.table_editor.expression.operation.operations.unary;

import com.lnedimovic.table_editor.expression.operation.Operation;

/**
 * <code>Negation</code> is an operation that performs standard mathematical negation to given data.
 * Standard math language uses "-" (minus sign) to annotate it, in front of some value, e.g. "-5".
 */
public class Negation extends Operation {
    /**
     * Creates an instance of <code>Negation</code> operation. Sets "unary" to true, since the operation is unary.
     * @param id         Id (representation symbol), i.e. "-".
     * @param precedence Precedence of given operation.
     */
    public Negation(String id, int precedence) {
        super(id, precedence);
        this.setUnary(true);
    }

    /**
     * Performs standard mathematical negation.
     * @param args        Array of objects passed into given operation. For unary, it is a singular object; for binary, there are two.
     * @return            Negated version of argument.
     * @throws Exception  In case of invalid number of arguments or invalid value provided.
     */
    @Override
    public String evaluate(Object... args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Negation must be provided two values.");
        }

        Object arg = args[0];
        if (!validValue(arg)) {
            throw new Exception("Negation: Invalid values provided");
        }

        arg = Double.parseDouble(arg.toString());

        double result = -(Double) arg;
        return Double.toString(result);
    }

    public boolean validValue(Object obj) {
        return true;
    }
}
