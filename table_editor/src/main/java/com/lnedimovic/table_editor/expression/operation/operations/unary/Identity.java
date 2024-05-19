package com.lnedimovic.table_editor.expression.operation.operations.unary;

import com.lnedimovic.table_editor.expression.operation.Operation;

/**
 * <code>Identity</code> is an operation that performs no change to given data.
 * One very popular example is a "unary plus". Standard math expression "+5" would simply evaluate to 5, because "+" in front does no change.
 */
public class Identity extends Operation {
    /**
     * Creates an instance of <code>Identity</code> operation. Sets "unary" to true, since the operation is unary.
     * @param id         Id (representation symbol), i.e. "+".
     * @param precedence Precedence of given operation.
     */
    public Identity(String id, int precedence) {
        super(id, precedence);
        this.setUnary(true);
    }

    /**
     * Performs identity operation (does no change to the data).
     * @param args        Array of objects passed into given operation. For unary, it is a singular object; for binary, there are two.
     * @return            Unchanged argument.
     * @throws Exception  In case of invalid number of arguments or invalid value provided.
     */
    @Override
    public String evaluate(Object... args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Identity must be provided two values.");
        }

        Object arg = args[0];
        if (!validValue(arg)) {
            throw new Exception("Identity: Invalid values provided");
        };

        return (String) arg;
    }

    public boolean validValue(Object obj) {
        return true;
    }
}
