package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.*;
import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * Min is a function resembling standard mathematical minimum value function, i.e. min(2, 3) = 2.
 */
public class Min extends Function {
    /**
     * Creates an instance of Min, given its id.
     * @param id          Function identifier.
     * @throws Exception  In case of invalid number of parameters.
     */
    public Min(String id) throws Exception {
        super(id);
    }

    public DType<?> min(DType<?> left, DType<?> right) throws Exception {
        try {
            checkTypes(new DTypeArray(new DType[]{left, right}));
        }
        catch (Exception e) {
            throw new Exception(e);
        }

        if (!left.getClass().equals(right.getClass())) {
            throw new Exception("min(DType<?>, DType<?>): Type mismatch.");
        }

        // Call helper function to calculate the result
        return _min(left, right);
    }

    /**
     * @param args       List of eligible operands
     * @return           Minimum of given operands
     * @throws Exception In case of invalid types, type mismatch, or > 2 array dimension.
     */
    public DType<?> min(DTypeArray args) throws Exception {
        try {
            checkTypes(args);               // Check for invalid types
            if (!args.isClassUniform()) {   // Check for class uniformity
                throw new Exception("min(DTypeArray): Arguments must be of same DType.");
            }
            args = args.toTwoDimensional(); // Upscale to 2D array, if needed
        }
        catch (Exception e) {
            throw new Exception(e);
        }

        // To "bias" the result type around the actual data in the DTypeArray, the first element is stored inside.
        DType<?> result = args.get(0, 0);

        // Go through every element and add it to the result
        for (int row = 0; row < args.length(); row++) {
            for (int col = 0; col < ((DTypeArray) args.get(row)).length(); col++) {
                if (row == 0 && col == 0) {
                    continue;
                }

                // Take the maximum of the new element, and the current maximum
                result = _min(result, args.get(row, col));
            }
        }

        return result;
    }

    /**
     * Auxiliary function used for pairwise minimum element calculation.
     * @param left  Left operand
     * @param right Right operand
     * @return      Minimum of two given operands.
     */
    public DType<?> _min(DType<?> left, DType<?> right) {
        DType<?> result = null;

        Class<?> targetClass = left.getClass();
        // Find the type and store the result
        if (targetClass.equals(DTypeDouble.class)) {
            result = new DTypeDouble(Math.min(((DTypeDouble) left).getValue(), ((DTypeDouble) right).getValue()));
        }
        if (targetClass.equals(DTypeInteger.class)) {
            result = new DTypeInteger(Math.min(((DTypeInteger) left).getValue(), ((DTypeInteger) right).getValue()));
        }
        if (targetClass.equals(DTypeBoolean.class)) {
            result = new DTypeBoolean(Math.min(((DTypeBoolean) left).getIntegerValue(), ((DTypeBoolean) right).getIntegerValue()));
        }

        return result;
    }

    public void checkTypes(DTypeArray args) throws Exception {
        if (args.containsDType(DTypeString.class)) {
            throw new Exception("min(DTypeArray).checkTypes: Invalid type provided: DTypeString");
        }
    }
}
