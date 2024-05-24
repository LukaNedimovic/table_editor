package com.lnedimovic.table_editor.dtype.dtypes;

import com.lnedimovic.table_editor.dtype.DType;

import java.util.Arrays;

/**
 * DTypeArray is a wrapper function that should be used to store DType values inside table and in other calculationos.
 */
public class DTypeArray implements DType<DType<?>[]> {
    DType<?>[] value;

    public DTypeArray(int size) {
        value = new DType[size];
    }
    public DTypeArray(DType<?>[] arr) {
        value = arr;
    }
    public DTypeArray(DTypeArray arr) {
        value = new DTypeArray[]{arr};
    }

    public String toString() {
        return Arrays.toString(value);
    }

    public int length() {
        return this.getValue().length;
    }

    public Integer totalDimensions() {
        for (int idx = 0 ; idx < value.length ; idx++) {
            if (value[idx] instanceof DTypeArray) {
                return 1 + ((DTypeArray) value[idx]).totalDimensions();
            }
        }

        return 1;
    }

    /**
     * @return           2-dimensional array.
     * @throws Exception In case of array of higher dimension than 2 provided.
     */
    public DTypeArray toTwoDimensional() throws Exception {
        if (totalDimensions() > 2) {
            throw new Exception("Array of higher dimension than 2 provided.");
        }

        // If dimension of the provided array is 1, upscale it
        if (totalDimensions() == 1) {
            return new DTypeArray(this);
        }
        // Otherwise, it is already a 2D array, so just return it
        else {
            return this;
        }
    }

    /**
     * @return True if objects within the DTYpeArray are all the same class; false, otherwise.
     */
    public boolean isClassUniform() {
        for (int idx = 0; idx < value.length - 1; idx++) {
            // Check if the class is same for two neighboring elements
            if (value[idx].getClass() != value[idx + 1].getClass()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Function that checks whether the provided DTypeArray contains instance of given DType class
     * @param dtype Class function is checking for
     * @return      True if the given class is found; false, otherwise.
     */
    public boolean containsDType(Class<?> dtype) {
        for (int idx = 0; idx < value.length; idx++) {
            // In case of a 2D array, traverse its elements
            if (value[idx] instanceof DTypeArray) {
                boolean eval = ((DTypeArray) value[idx]).containsDType(dtype);
                if (eval) {
                    return true;
                }
            }
            else if (value[idx].getClass().equals(dtype)) {
                return true;
            }
        }

        return false;
    }

    public DType<?> get(int idx) throws Exception {
        if (idx >= value.length) {
            throw new Exception(String.format("DTypeArray: Index %s out of bounds for size %d.", idx, value.length));
        }

        return value[idx];
    }

    public DType<?> get(int row, int col) throws Exception {
        if (row >= value.length ||
            (value[row] instanceof DTypeArray && col >= ((DTypeArray) value[row]).length())) {
            throw new Exception(String.format("DTypeArray.get: Index (%s, %s) out of bounds for size (%d, %d).", row, col, value.length, ((DTypeArray) value[row]).length()));
        }

        if (!(value[row] instanceof DTypeArray)) {
            throw new Exception("DTypeArray.get: Can't access column because DTypeArray is one-dimensional.");
        }

        DTypeArray rowArray = (DTypeArray) value[row];
        return rowArray.get(col);
    }

    public void set(int idx, DType<?> newValue) throws Exception {
        if (idx >= value.length) {
            throw new Exception(String.format("DTypeArray: Index %s out of bounds for size %d.", idx, value.length));
        }

        value[idx] = newValue;
    }

    @Override
    public DType<?> id() throws Exception {
        return this;
    }

    @Override
    public DType<?> neg() throws Exception {
        throw new Exception("DTypeArray.neg: Negation is undefined for DTypeArray.");
    }

    @Override
    public DType<?> add(Object obj) throws Exception {
        throw new Exception("DTypeArray.add: Addition is undefined for DTypeArray. TODO.");
    }

    @Override
    public DType<?> sub(Object obj) throws Exception {
        throw new Exception("DTypeArray.sub: Subtraction is undefined for DTypeArray.");
    }

    @Override
    public DType<?> mul(Object obj) throws Exception {
        throw new Exception("DTypeArray.mul: Multiplication is undefined for DTypeArray. TODO.");
    }

    @Override
    public DType<?> div(Object obj) throws Exception {
        throw new Exception("DTypeArray.div: Division is undefined for DTypeArray.");
    }

    @Override
    public DType<?> exp(Object obj) throws Exception {
        throw new Exception("DTypeArray.exp: Exponentiation is undefined for DTypeArray.");
    }

    @Override
    public DType<?> mod(Object obj) throws Exception {
        throw new Exception("DTypeArray.div: Modulo is undefined for DTypeArray.");
    }

    @Override
    public DType<?> lt(Object obj) throws Exception {
        throw new Exception("DTypeArray.div: \"Less than\" is undefined for DTypeArray.");
    }

    @Override
    public DType<?> gt(Object obj) throws Exception {
        throw new Exception("DTypeArray.div: \"Greater than\" is undefined for DTypeArray.");
    }

    @Override
    public DType<?>[] getValue() {
        return value;
    }

    @Override
    public void setValue(DType<?>[] value) {
        this.value = value;
    }
}
