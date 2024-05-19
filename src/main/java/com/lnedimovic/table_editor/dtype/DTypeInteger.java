package com.lnedimovic.table_editor.dtype;

public class DTypeInteger implements DType<Integer> {
    private Integer value;

    public DTypeInteger(Integer value) {
        this.value = value;
    }
    public DTypeInteger(Double value) {
        this.value = value.intValue();
    }
    public DTypeInteger(String value) {
        this.value = Integer.parseInt(value);
    }

    public DTypeInteger(DTypeInteger obj) {
        this.value = obj.getValue();
    }
    public DTypeInteger(DTypeDouble obj) {
        this.value = obj.getValue().intValue();
    }
    public DTypeInteger(DTypeString obj) {
        this.value = Integer.parseInt(obj.getValue());
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        DTypeInteger objInteger = (DTypeInteger) obj;
        return value.equals(objInteger.value);
    }

    // CONVERSIONS

    public DTypeDouble toDTypeDouble() {
        return new DTypeDouble(value.doubleValue());
    }

    public DTypeString toDTypeString() {
        return new DTypeString(value.toString());
    }

    // OPERATIONS

    public DType<?> id() throws Exception {
        return this;
    }

    public DType<?> neg() throws Exception {
        return new DTypeInteger(-value);
    }

    public DType<?> add(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeInteger.add: Can't perform addition with null value.");
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeDouble(value + ((DTypeDouble) obj).getValue());
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeInteger(value + ((DTypeInteger) obj).getValue());
        }

        throw new Exception(String.format("DTypeInteger.add: Can't perform addition with given type (%s).", obj.getClass()));
    }

    public DType<?> sub(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeInteger.sub: Can't perform addition with null value.");
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeDouble(value - ((DTypeDouble) obj).getValue());
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeInteger(value - ((DTypeInteger) obj).getValue());
        }

        throw new Exception(String.format("DTypeInteger.add: Can't perform addition with given type (%s).", obj.getClass()));
    }

    public DType<?> mul(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeInteger.mul: Can't perform multiplication with null value.");
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeDouble(value * ((DTypeDouble) obj).getValue());
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeInteger(value * ((DTypeInteger) obj).getValue());
        }

        throw new Exception(String.format("DTypeInteger.mul: Can't perform multiplication with given type (%s).", obj.getClass()));
    }

    public DType<?> div(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeInteger.div: Can't perform division with null value.");
        }
        if (obj instanceof DTypeInteger) {
            if (((DTypeInteger) obj).getValue() == 0) {
                throw new Exception("DTypeInteger.div: Can't perform division with 0.");
            }
            return new DTypeInteger(value / ((DTypeInteger) obj).getValue());
        }
        if (obj instanceof DTypeDouble) {
            if (((DTypeDouble) obj).getValue() == 0) {
                throw new Exception("DTypeInteger.div: Can't perform division with 0.");
            }
            return new DTypeDouble(value / ((DTypeDouble) obj).getValue());
        }

        throw new Exception(String.format("DTypeInteger.div: Can't perform division with given type (%s).", obj.getClass()));
    }

    public DType<?> exp(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeInteger.exp: Can't perform exponentiation with null value.");
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeInteger(Math.pow(value, ((DTypeInteger) obj).getValue()));
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeDouble(Math.pow(value, ((DTypeDouble) obj).getValue()));
        }

        throw new Exception(String.format("DTypeInteger.exp: Can't perform exponentiation with given type (%s).", obj.getClass()));
    }

    public DType<?> mod(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeInteger.mod: Can't perform modulo with null value.");
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeInteger(value % (((DTypeInteger) obj).getValue()));
        }

        throw new Exception(String.format("DTypeInteger.exp: Can't perform modulo with given type (%s).", obj.getClass()));
    }

    public DType<?> lt(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeInteger.lt: Can't perform \"less than\" with null value.");
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeInteger(value < ((DTypeInteger) obj).getValue() ? 1.0 : 0.0);
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeDouble(value.doubleValue() < ((DTypeDouble) obj).getValue() ? 1.0 : 0.0);
        }

        throw new Exception(String.format("DTypeInteger.lt: Can't perform \"less than\" with given type (%s).", obj.getClass()));
    }

    public DType<?> gt(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeInteger.gt: Can't perform \"greater than\" with null value.");
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeInteger(value > ((DTypeInteger) obj).getValue() ? 1.0 : 0.0);
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeDouble(value.doubleValue() > ((DTypeDouble) obj).getValue() ? 1.0 : 0.0);
        }

        throw new Exception(String.format("DTypeInteger.gt: Can't perform \"greater than\" with given type (%s).", obj.getClass()));
    }
}
