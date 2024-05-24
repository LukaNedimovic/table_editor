package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeArray;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeDouble;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeString;
import com.lnedimovic.table_editor.expression.function.Function;

/**
 * Average is a function representing standard mathematical notion of "average", i.e. (sum of all values) / (total number of value).
 */
public class Average extends Function {
    /**
     * Creates an instance of Average, given its id.
     * @param id          Function identifier.
     * @throws Exception  In case of invalid number of parameters.
     */
    public Average(String id) throws Exception {
        super(id);

    }

    /**
     * Calculates the average value of provided values.
     * @param args       List of values.
     * @return           Average value of provided values.
     * @throws Exception In case of invalid types or invalid dimensionality (>2).
     */
    public DType<?> average(DTypeArray args) throws Exception {
        try {
            checkTypes(args);               // Check for invalid types
            args = args.toTwoDimensional(); // Upscale to 2D array, if needed
        }
        catch (Exception e) {
            throw new Exception(e);
        }

        DType<?> sum = args.get(0, 0);
        for (int row = 0; row < args.length(); row++) {
            DTypeArray rowArray = (DTypeArray) args.get(row);

            for (int col = 0; col < rowArray.length(); col++) {
                if (row == 0 && col == 0) {
                    continue;
                }

                sum = sum.add(args.get(row, col));
            }
        }

        DTypeDouble elementCount = new DTypeDouble(args.length() * ((DTypeArray) args.get(0)).length());
        return sum.div(elementCount);
    }

    public void checkTypes(DTypeArray args) throws Exception {
        if (args.containsDType(DTypeString.class)) {
            throw new Exception("avg(DTypeArray).checkTypes: Invalid type provided: DTypeString");
        }
    }
}