package com.lnedimovic.table_editor.expression.function.functions;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeBoolean;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeDouble;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeInteger;
import com.lnedimovic.table_editor.expression.function.Function;

import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * Min is a function resembling standard mathematical minimum value function, i.e. min(2, 3) = 2.
 */
public class Max extends Function {
    /**
     * Creates an instance of Max, given its id.
     * @param id          Function identifier.
     * @throws Exception  In case of invalid number of parameters.
     */
    public Max(String id) throws Exception {
        super(id);
    }

    public DType<?> max(DType<?> left, DType<?> right) throws Exception {
        Class<?>[] validClasses = new Class[]{DTypeDouble.class, DTypeInteger.class, DTypeBoolean.class};

        Class<?> leftClass  = left.getClass();
        Class<?> rightClass = right.getClass();
        if (!Arrays.asList(validClasses).contains(leftClass) ||
                !Arrays.asList(validClasses).contains(rightClass)) {
            throw new Exception("max(DType<?>, DType<?>): Invalid types provided.");
        }
        if (!leftClass.equals(rightClass)) {
            throw new Exception("max(DType<?>, DType<?>): Type mismatch.");
        }

        DType<?> result = null;

        // Find the type and store the result.
        if (leftClass.equals(DTypeDouble.class)) {
            result = new DTypeDouble(Math.max(((DTypeDouble) left).getValue(), ((DTypeDouble) right).getValue()));
        }
        if (leftClass.equals(DTypeInteger.class)) {
            result = new DTypeInteger(Math.max(((DTypeInteger) left).getValue(), ((DTypeInteger) right).getValue()));
        }
        if (leftClass.equals(DTypeBoolean.class)) {
            result = new DTypeBoolean(Math.max(((DTypeBoolean) left).getIntegerValue(), ((DTypeBoolean) right).getIntegerValue()));
        }

        return result;
    }

}
