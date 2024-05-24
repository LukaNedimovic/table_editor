package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeArray;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeString;
import com.lnedimovic.table_editor.expression.function.Function;

/**
 * Sum is a function representing standard mathematical notion of "summation", i.e. addition of all the values.
 */
public class Sum extends Function {
    /**
     * Creates an instance of Sum, given its id.
     * @param id          Function identifier.
     * @throws Exception  In case of invalid number of parameters.
     */
    public Sum(String id) throws Exception {
        super(id);

    }

    /**
     * @param args       Evaluated expressions inside array passed originally in the GUI.
     * @return           Sum of all elements provided.
     * @throws Exception In case of invalid type.
     */
    public DType<?> sum(DTypeArray args) throws Exception {
        try {
            checkTypes(args);               // Check for invalid types
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
                if (row == 0 && col == 0) { // Skip the first value because it's already calculated
                    continue;
                }

                // Add the current value to the sum
                result = result.add(args.get(row, col));
            }
        }

        return result;
    }

    /**
     * Auxiliary function, created to check whether certain types are not meaningful to use inside the function.
     * @param args       Complete list of arguments.
     * @throws Exception In case of invalid type provided to the function.
     */
    public void checkTypes(DTypeArray args) throws Exception {
        if (args.containsDType(DTypeString.class)) {
            throw new Exception("sum(DTypeArray).checkTypes: Invalid type provided: DTypeString");
        }
    }
}