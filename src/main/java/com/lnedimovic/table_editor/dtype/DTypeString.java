package com.lnedimovic.table_editor.dtype;

public class DTypeString implements DType<String> {
    private String value;

    public DTypeString(String value) {
        this.value = value;
    }

    public DTypeString(DTypeString obj) {
        this.value = obj.getValue();
    }
    public DTypeString(DTypeDouble obj) {
        this.value = obj.toString();
    }
    public DTypeString(DTypeInteger obj) {
        this.value = obj.toString();
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
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

    // CONVERSIONS

    public DTypeDouble toDTypeDouble() {
        return new DTypeDouble(Double.parseDouble(value));
    }

    public DTypeInteger toDTypeInteger() {
        return new DTypeInteger(Integer.parseInt(value));
    }

    // OPERATIONS

    public DType<?> id() throws Exception {
        return this;
    }

    public DType<?> neg() throws Exception {
        throw new Exception("DTypeString.neg: Negation is undefined for DTypeString.");
    }

    public DType<?> add(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeString.add: Can't perform addition with null value.");
        }
        if (obj instanceof DTypeString) {
            return new DTypeString(value + ((DTypeString) obj).getValue());
        }
        throw new Exception(String.format("DTypeString.add: Can't perform addition with given type (%s).", obj.getClass()));
    }

    public DType<?> sub(Object obj) throws Exception {
        throw new Exception("DTypeString.sub: Subtraction is undefined for DTypeString.");
    }

    public DType<?> mul(Object obj) throws Exception {
        throw new Exception("DTypeString.mul: Multiplication is undefined for DTypeString.");
    }

    public DType<?> div(Object obj) throws Exception {
        throw new Exception("DTypeString.div: Division is undefined for DTypeString.");
    }

    public DType<?> exp(Object obj) throws Exception {
        throw new Exception("DTypeString.exp: Exponentiation is undefined for DTypeString.");
    }

    public DType<?> mod(Object obj) throws Exception {
        throw new Exception("DTypeString.mod: Modulo is undefined for DTypeString");
    }

    public DType<?> lt(Object obj) throws Exception {
        throw new Exception("DTypeString.lt: \"Less than\" is undefined for DTypeString");
    }

    public DType<?> gt(Object obj) throws Exception {
        throw new Exception("DTypeString.lt: \"Greater than\" is undefined for DTypeString");
    }
}

