package com.lnedimovic.table_editor.expression.operation;

/**
 * Abstract class representing either a unary or binary operation.
 * Precedence is to be defined for each operation, for the safety of the production.
 * This class provides flexible template for creation of many different single-symbol operations.
 * For the sake of safety, operation should use some non-standard symbol, i.e. not a letter or a number.
 *
 * Every operation implemented inherits from this class.
 */
public abstract class Operation {
    /**
     * Id of operation, i.e. it's symbol. For example, in standard mathematical notation, addition's id is a "+", while exponentiation's is "^".
     * Id can be set to non-standard values, i.e. it is entirely possible to set addition's id to be a "~", or something else, and still get the same functionality.
     */
    private String  id;

    /**
     * Precedence of operation. By default, it is set to 0, as in the "least precedence".
     * 0 should be used as the lowest precedence possible.
     */
    private int     precedence = 0;

    /**
     * Whether opereation is unary or not. Unary operations only act on a single value (e.g. -p), whereas binary operations act upon two values (e.g. p + q).
     */
    private boolean isUnary = false;

    /**
     * Creates an instance of <code>Operation</code>, without explicitly setting the id and the precedence of the operation.
     */
    public Operation() {}

    /**
     * Creates an instance of <code>Operation</code>, given id and precedence.
     * @param id         Symbol defining the operation.
     * @param precedence Precedence of operation in general sense.
     */
    public Operation(String id, int precedence) {
        this.id = id;
        this.precedence = precedence;
    }

    /**
     * @return String representation of <code>Operation</code>.
     */
    public String toString() {
        return String.format("Operation(id=%s, precedence=%d, unary=%b)", id, precedence, isUnary);
    }

    /**
     * @param obj The object operation is being compared to.
     * @return    True, if the objects are equal; otherwise false.
     */
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj instanceof String) {
            return obj.equals(id);
        }
        if (obj instanceof Operation) {
            return ((Operation) obj).id.equals(id);
        }

        return false;
    }

    /**
     * Abstract method, to be overridden by every operation created, that defines how operation should be evaluated.
     * This is the "core" of operation - it is the place where the developer encodes the logic of each operation and makes it scalable and highly flexible.
     * @param args        Array of objects passed into given operation. For unary, it is a singular object; for binary, there are two.
     * @return            Result of operation evaluation.
     * @throws Exception  In case of invalid number of arguments or values.
     */
    public abstract Object evaluate(Object... args) throws Exception;

    /**
     * @return <code>Operation</code>'s id (representation symbol).
     */
    public String getId() {
        return id;
    }

    /**
     * Sets new id (representation symbol) for given operation.
     * @param id New id (representation symbol).
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Operation's precedence.
     */
    public int getPrecedence() {
        return precedence;
    }

    /**
     * Sets new precedence for operation.
     * @param precedence New precedence.
     */
    public void setPrecedence(int precedence) {
        this.precedence = precedence;
    }

    /**
     * @return Whether operation is unary or not.
     */
    public boolean isUnary() {
        return isUnary;
    }

    /**
     * Sets whether the operation is unary or not.
     * @param unary New value for unary (true / false).
     */
    public void setUnary(boolean unary) {
        isUnary = unary;
    }
}
