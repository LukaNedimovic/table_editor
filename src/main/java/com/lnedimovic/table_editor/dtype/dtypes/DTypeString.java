package com.lnedimovic.table_editor.dtype.dtypes;

import com.lnedimovic.table_editor.dtype.DType;

public class DTypeString implements DType<String> {
    private String value;

    public DTypeString(String value) {
        this.value = value;
    }
    public DTypeString(Integer value) {
        this.value = value.toString();
    }
    public DTypeString(Double value) {
        this.value = value.toString();
    }
    public DTypeString(Boolean value) {
        this.value = value.toString();
    }

    public DTypeString(DTypeString obj) {
        this.value = obj.getValue();
    }
    public DTypeString(DTypeDouble obj) {
        this.value = obj.getValue().toString();
    }
    public DTypeString(DTypeInteger obj) {
        this.value = obj.getValue().toString();
    }
    public DTypeString(DTypeBoolean obj) {
        this.value = obj.getValue().toString();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        DTypeString objString = (DTypeString) obj;
        return value.equals(objString.getValue());
    }

    // OPERATIONS

    @Override
    public DType<?> id() throws Exception {
        return this;
    }

    @Override
    public DType<?> neg() throws Exception {
        throw new Exception("DTypeString.neg: Negation is undefined for DTypeString.");
    }

    @Override
    public DType<?> add(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeString.add: Can't perform addition with null value.");
        }
        if (obj instanceof DTypeString) {
            return new DTypeString(value + ((DTypeString) obj).getValue());
        }
        throw new Exception(String.format("DTypeString.add: Can't perform addition with given type (%s).", obj.getClass()));
    }

    @Override
    public DType<?> sub(Object obj) throws Exception {
        throw new Exception("DTypeString.sub: Subtraction is undefined for DTypeString.");
    }

    @Override
    public DType<?> mul(Object obj) throws Exception {
        if (obj instanceof DTypeInteger) {
            Integer repetitions = ((DTypeInteger) obj).getValue();
            if (repetitions <= 0) {
                throw new Exception("DTypeString.mul: Can't perform multiplication with negative value.");
            }

            return new DTypeString(value.repeat(repetitions));
        }
        if (obj instanceof DTypeBoolean) {
            Boolean booleanValue = ((DTypeBoolean) obj).getValue();
            return new DTypeString(booleanValue ? value : "");
        }

        throw new Exception(String.format("DTypeString.mul: Can't perform multiplication with given type (%s)", obj.getClass()));
    }

    @Override
    public DType<?> div(Object obj) throws Exception {
        throw new Exception("DTypeString.div: Division is undefined for DTypeString.");
    }

    @Override
    public DType<?> exp(Object obj) throws Exception {
        throw new Exception("DTypeString.exp: Exponentiation is undefined for DTypeString.");
    }

    @Override
    public DType<?> mod(Object obj) throws Exception {
        throw new Exception("DTypeString.mod: Modulo is undefined for DTypeString");
    }

    @Override
    public DType<?> lt(Object obj) throws Exception {
        if (obj instanceof DTypeString) {
            return new DTypeBoolean(value.compareTo(((DTypeString) obj).getValue()) < 0 ? 1 : 0);
        }
        throw new Exception(String.format("DTypeString.lt: Can't perform \"less than\" with given type (%s)", obj.getClass()));
    }

    @Override
    public DType<?> gt(Object obj) throws Exception {
        if (obj instanceof DTypeString) {
            return new DTypeBoolean(value.compareTo(((DTypeString) obj).getValue()) > 0 ? 1 : 0);
        }
        throw new Exception(String.format("DTypeString.gt: Can't perform \"greater than\" with given type (%s)", obj.getClass()));
    }


    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}

