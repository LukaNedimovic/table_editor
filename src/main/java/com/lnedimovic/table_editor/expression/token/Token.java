package com.lnedimovic.table_editor.expression.token;

import com.lnedimovic.table_editor.dtype.DType;

/**
 * <code>Token</code> is a building block of an expression.
 * Array of such is passed into parser to be parsed, so an abstract syntax tree of a formula can be created.
 * List of all possible tokens can be found in {@link TokenType}.
 */
public class Token {
    /**
     * String representation of token (the way it is found in the original formula).
     */
    private DType<?> value;
    /**
     * Type of the token (e.g. numerical constant, function...).
     */
    private TokenType type;
    /**
     * Related information to the token. Used with functions and operations (by storing the respective function / operation), for easier access.
     */
    private Object    related = null;

    /**
     * Creates an instance of Token.
     * @param value   Original String representation of token.
     * @param related Related piece of information / functionality. Not always meaningfully useful / provided.
     * @param type    The type of the token. See {@link TokenType} for more information.
     */
    public Token(DType<?> value, Object related, TokenType type) {
        this.value = value;
        this.related = related;
        this.type = type;
    }

    /**
     * @return String representation of respective token.
     */
    public String toString() {
        if (related != null) {
            return String.format("Token('%s' (%s), %s, %s)", value, value.getClass(), related, type);
        }
        else {
            return String.format("Token('%s' (%s), %s)", value, value.getClass(), type);
        }
    }

    /**
     * @return Type of the token.
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Sets the new type for the respective token.
     * @param type New type for token.
     */
    public void setType(TokenType type) {
        this.type = type;
    }

    /**
     * @return Value of the token.
     */
    public DType<?> getValue() {
        return value;
    }

    /**
     * Sets the new token representation value.
     * @param value New value for token.
     */
    public void setValue(DType<?> value) {
        this.value = value;
    }

    /**
     * @return Related information / functionality of the token.
     */
    public Object getRelated() {
        return related;
    }

    /**
     * Sets the new related information / functionality.
     * @param related New related information / functionality of the token.
     */
    public void setRelated(Object related) {
        this.related = related;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Token) {
            return (this.value.equals(((Token) obj).value) && this.type.equals(((Token) obj).type) && this.related.equals(((Token) obj).related));
        }
        if (obj instanceof String) {
            return value.equals(obj);
        }

        return false;
    }
}
