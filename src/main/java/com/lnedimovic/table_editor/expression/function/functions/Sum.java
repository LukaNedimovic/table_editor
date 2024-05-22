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
        super(id, true);

    }

    public DType<?> sum(DTypeArray args) throws Exception {
        checkTypes(args);

        DType<?> result = args.get(0, 0);
        for (int row = 0; row < args.length(); row++) {
            DTypeArray rowArray = (DTypeArray) args.get(row);

            for (int col = 0; col < rowArray.length(); col++) {
                if (row == 0 && col == 0) {
                    continue;
                }

                result = result.add(args.get(row, col));
            }
        }

        return result;
    }

    public void checkTypes(DTypeArray args) throws Exception {
        if (args.containsDType(DTypeString.class)) {
            throw new Exception("sum(DTypeArray).checkTypes: Invalid type provided: DTypeString");
        }
    }
}