package com.lnedimovic.table_editor.dtype.dtypes;

import com.lnedimovic.table_editor.dtype.DType;

public class DTypeBoolean implements DType<Boolean> {
    private Boolean value;

    public DTypeBoolean(Boolean value) {
        this.value = value;
    }
    public DTypeBoolean(Double value) {
        this.value = (value != 0.0);
    }
    public DTypeBoolean(Integer value) {
        this.value = (value != 0);
    }
    public DTypeBoolean(String value) throws Exception {
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            this.value = Boolean.valueOf(value);
        }
        else {
            throw new Exception("DTypeBoolean(): Invalid String value");
        }
    }

    public DTypeBoolean(DTypeBoolean obj) {
        this.value = obj.getValue();
    }
    public DTypeBoolean(DTypeDouble obj) {
        this.value = (obj.getValue() != 0.0);
    }
    public DTypeBoolean(DTypeInteger obj) {
        this.value = (obj.getValue() != 0);
    }
    public DTypeBoolean(DTypeString obj) throws Exception {
        if (obj.getValue().equalsIgnoreCase("true") || obj.getValue().equalsIgnoreCase("false")) {
            this.value = Boolean.valueOf(obj.getValue());
        }
        else {
            throw new Exception("DTypeBoolean(): Invalid String value");
        }
    }

    @Override
    public String toString() {
        String repr = value.toString();
        return repr.substring(0, 1).toUpperCase() + repr.substring(1);
    }

    public Integer getIntegerValue() {
        return value ? 1 : 0;
    }

    public Double getDoubleValue() {
        return value ? 1.0 : 0.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        DTypeBoolean objBoolean = (DTypeBoolean) obj;
        return value.equals(objBoolean.value);
    }

    @Override
    public DType<?> id() throws Exception {
        return this;
    }

    @Override
    public DType<?> neg() throws Exception {
        return new DTypeBoolean(!value);
    }

    @Override
    public DType<?> add(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeBoolean.add: Can't perform addition with null value.");
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeDouble(getDoubleValue() + ((DTypeDouble) obj).getValue());
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeInteger(getIntegerValue() + ((DTypeInteger) obj).getValue());
        }
        if (obj instanceof DTypeBoolean) {
            return new DTypeBoolean(value || ((DTypeBoolean) obj).getValue());
        }

        throw new Exception(String.format("DTypeBoolean.add: Can't perform addition with given type (%s).", obj.getClass()));
    }

    @Override
    public DType<?> sub(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeBoolean.sub: Can't perform subtraction with null value.");
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeDouble(getDoubleValue() - ((DTypeDouble) obj).getValue());
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeInteger(getIntegerValue() - ((DTypeInteger) obj).getValue());
        }

        throw new Exception(String.format("DTypeBoolean.sub: Can't perform subtraction with given type (%s).", obj.getClass()));
    }

    public DType<?> mul(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeBoolean.mul: Can't perform multiplication with null value.");
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeDouble(getDoubleValue() * ((DTypeDouble) obj).getValue());
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeInteger(getIntegerValue() * ((DTypeInteger) obj).getValue());
        }
        if (obj instanceof DTypeBoolean) {
            return new DTypeBoolean(value && ((DTypeBoolean) obj).getValue());
        }
        if (obj instanceof DTypeString) {
            String stringValue = ((DTypeString) obj).getValue();
            return new DTypeString(getValue() ? stringValue : "");
        }

        throw new Exception(String.format("DTypeBoolean.mul: Can't perform multiplication with given type (%s).", obj.getClass()));
    }

    public DType<?> div(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeBoolean.div: Can't perform division with null value.");
        }
        if (obj instanceof DTypeDouble) {
            if (((DTypeDouble) obj).getValue() == 0) {
                throw new Exception("DTypeBoolean.div: Can't perform division with 0.");
            }
            return new DTypeDouble(getIntegerValue() / ((DTypeDouble) obj).getValue());
        }
        if (obj instanceof DTypeInteger) {
            if (((DTypeInteger) obj).getValue() == 0) {
                throw new Exception("DTypeBoolean.div: Can't perform division with 0.");
            }
            return new DTypeInteger(getDoubleValue() / ((DTypeInteger) obj).getValue());
        }

        throw new Exception(String.format("DTypeBoolean.div: Can't perform division with given type (%s).", obj.getClass()));
    }

    @Override
    public DType<?> exp(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeBoolean.exp: Can't perform division with null value.");
        }
        if (obj instanceof DTypeDouble) {
            if (((((DTypeDouble) obj).getValue()) == 0) && (!value)) {
                throw new Exception("DTypeBoolean.exp: 0^0 is undefined.");
            }
            return new DTypeBoolean(value);
        }
        if (obj instanceof DTypeInteger) {
            if (((((DTypeInteger) obj).getValue()) == 0) && (!value)) {
                throw new Exception("DTypeBoolean.exp: 0^0 is undefined.");
            }
            return new DTypeBoolean(value);
        }
        if (obj instanceof DTypeBoolean) {
            if (!((((DTypeBoolean) obj).getValue())) && (!value)) {
                throw new Exception("DTypeBoolean.exp: 0^0 is undefined.");
            }
            return new DTypeBoolean(value);
        }

        throw new Exception("DTypeBoolean.exp: Exponentiation is undefined for DTypeBoolean.");
    }

    @Override
    public DType<?> mod(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeBoolean.mod: Can't perform modulo with null value.");
        }
        if (obj instanceof DTypeInteger) {
            Integer integerValue = ((DTypeInteger) obj).getValue();
            if (integerValue == 0) {
                throw new Exception("DTypeBoolean.mod: Can't perform modulo with 0.");
            }

            return new DTypeBoolean(getIntegerValue() % integerValue);
        }
        if (obj instanceof DTypeBoolean) {
            Integer integerValue = ((DTypeBoolean) obj).getIntegerValue();
            if (integerValue == 0) {
                throw new Exception("DTypeBoolean.mod: Can't perform modulo with 0.");
            }

            return new DTypeBoolean(getIntegerValue() % integerValue);
        }

        throw new Exception(String.format("DTypeBoolean.exp: Can't perform modulo with given type (%s).", obj.getClass()));
    }

    @Override
    public DType<?> lt(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeBoolean.lt: Can't perform \"less than\" with null value.");
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeBoolean(getDoubleValue() < ((DTypeDouble) obj).getValue() ? 1.0 : 0.0);
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeBoolean(getIntegerValue() < ((DTypeInteger) obj).getValue() ? 1.0 : 0.0);
        }
        if (obj instanceof DTypeBoolean) {
            return new DTypeBoolean((getIntegerValue() < ((DTypeBoolean) obj).getIntegerValue())? 1.0 : 0.0);
        }

        throw new Exception(String.format("DTypeBoolean.lt: Can't perform \"less than\" with given type (%s).", obj.getClass()));
    }

    @Override
    public DType<?> gt(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeBoolean.gt: Can't perform \"greater than\" with null value.");
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeBoolean(getDoubleValue() > ((DTypeDouble) obj).getValue() ? 1.0 : 0.0);
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeBoolean(getIntegerValue() > ((DTypeInteger) obj).getValue() ? 1.0 : 0.0);
        }
        if (obj instanceof DTypeBoolean) {
            return new DTypeBoolean((getIntegerValue() > ((DTypeBoolean) obj).getIntegerValue())? 1.0 : 0.0);
        }

        throw new Exception(String.format("DTypeBoolean.gt: Can't perform \"greater than\" with given type (%s).", obj.getClass()));
    }

    @Override
    public Boolean getValue() {
        return value;
    }
    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }
}
