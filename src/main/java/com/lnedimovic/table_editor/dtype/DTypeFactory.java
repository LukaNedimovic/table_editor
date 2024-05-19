package com.lnedimovic.table_editor.dtype;

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
        else {
            return new DTypeString(value);
        }
    }
}
