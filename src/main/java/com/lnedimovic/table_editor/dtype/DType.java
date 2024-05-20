package com.lnedimovic.table_editor.dtype;

public interface DType<T> {
    /**
     * @return The value of the instance implementing interface.
     */
    T getValue();

    /**
     * Set the value of the instance implementing interface.
     * @param value New value
     */
    void setValue(T value);

    /**
     * @return String representation of the instance implementing interface.
     */
    String toString();

    /**
     * Checks for equality between given instance implementing interface and another object.
     * @param obj Object to check equality with.
     * @return    True if objects are equal; false, otherwise.
     */
    boolean equals(Object obj);

    // Unary operations (prefix)
    DType<?> id()   throws Exception;
    DType<?> neg()  throws Exception;

    // Binary operations
    // Arithmetic operations
    DType<?> add(Object obj) throws Exception;
    DType<?> sub(Object obj) throws Exception;
    DType<?> mul(Object obj) throws Exception;
    DType<?> div(Object obj) throws Exception;
    DType<?> exp(Object obj) throws Exception;
    DType<?> mod(Object obj) throws Exception;

    // Logic operations
    DType<?> lt(Object obj)  throws Exception;
    DType<?> gt(Object obj)  throws Exception;
}