package com.lnedimovic.table_editor.dtype;

import com.lnedimovic.table_editor.dtype.dtypes.DTypeBoolean;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeDouble;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeInteger;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeString;

public class DTypeFactory {
    static final String REGEX_INTEGER = "-?\\d+";
    static final String REGEX_DOUBLE  = "-?\\d*\\.\\d+";

    public static DType<?> create(String value) {
        if (value.matches(REGEX_DOUBLE)) {
            return new DTypeDouble(Double.parseDouble(value));
        }
        else if (value.matches(REGEX_INTEGER)) {
            return new DTypeInteger(Integer.parseInt(value));
        }
        else if (value.equals("True") || value.equals("False")) {
            return new DTypeBoolean(Boolean.valueOf(value));
        }
        else {
            return new DTypeString(value);
        }
    }
}
