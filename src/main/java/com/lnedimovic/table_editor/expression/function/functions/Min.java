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
        super(id, true);
    }

    public DType<?> min(DType<?> left, DType<?> right) throws Exception {
        Class<?>[] validClasses = new Class[]{DTypeDouble.class, DTypeInteger.class, DTypeBoolean.class};

        Class<?> leftClass  = left.getClass();
        Class<?> rightClass = right.getClass();
        if (!Arrays.asList(validClasses).contains(leftClass) ||
                !Arrays.asList(validClasses).contains(rightClass)) {
            throw new Exception("min(DType<?>, DType<?>): Invalid types provided.");
        }
        if (!leftClass.equals(rightClass)) {
            throw new Exception("min(DType<?>, DType<?>): Type mismatch.");
        }

        // Call helper function to calculate the result
        return _min(left, right);
    }
    public DType<?> min(DTypeArray args) throws Exception {
        checkTypes(args);

        DType<?> result = args.get(0, 0);
        for (int row = 0; row < args.length(); row++) {
            DTypeArray rowArray = (DTypeArray) args.get(row);

            for (int col = 0; col < rowArray.length(); col++) {
                if (row == 0 && col == 0) {
                    continue;
                }

                // Take the maximum of the new element, and the current maximum
                result = _min(result, args.get(row, col));
            }
        }

        return result;
    }

    public DType<?> _min(DType<?> left, DType<?> right) {
        Class<?> targetClass = left.getClass();

        DType<?> result = null;

        // Find the type and store the result
        if (targetClass .equals(DTypeDouble.class)) {
            result = new DTypeDouble(Math.min(((DTypeDouble) left).getValue(), ((DTypeDouble) right).getValue()));
        }
        if (targetClass .equals(DTypeInteger.class)) {
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
