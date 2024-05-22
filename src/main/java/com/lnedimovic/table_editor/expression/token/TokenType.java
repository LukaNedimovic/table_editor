package com.lnedimovic.table_editor.expression.token;

public enum TokenType {
    /**
     * Operation token, as in +, -, *, /, ^ and so on.
     */
    OPERATION,

    /**
     * Function token, as in pow(p, q), sqrt(p), abs(x) and so on.
     */
    FUNCTION,

    /**
     * Literal numerical value, as in -4.182, 5.2, 1.0.
     */
    NUMERICAL_CONSTANT,

    /**
     * Cell reference, expressed in the form of cell range reference.
     * Single cell reference is also a range reference, e.g. start = C2, end = C2 (C2:C2).
     */
    REFERENCE,

    /**
     * Standard parenthesis, i.e. "(" or a ")" character.
     */
    PARENTHESIS,

    /**
     * Standard bracket, i.e. "[" or a "]" character
     */
    BRACKET,

    /**
     * Standard comma, i.e. "," character.
     */
    COMMA,

    /**
     * Standard quotation mark, i.e. " character.
     */
    QUOTATION_MARK,

    /**
     * Standard String, sequence of characters.
     */
    STRING,

    /**
     * Boolean False.
     */
    TRUE,

    /**
     * Boolean True.
     */
    FALSE,



}
