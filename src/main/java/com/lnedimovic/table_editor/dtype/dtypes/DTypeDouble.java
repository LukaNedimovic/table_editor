package com.lnedimovic.table_editor.dtype.dtypes;

import com.lnedimovic.table_editor.dtype.DType;

public class DTypeDouble implements DType<Double> {
    private Double value;

    public DTypeDouble(Double value) {
        this.value = value;
    }
    public DTypeDouble(Integer value) {
        this.value = value.doubleValue();
    }
    public DTypeDouble(String value) {
        this.value = Double.parseDouble(value);
    }
    public DTypeDouble(Boolean value) {
        this.value = value ? 1.0 : 0.0;
    }

    public DTypeDouble(DTypeDouble obj) {
        this.value = obj.getValue();
    }
    public DTypeDouble(DTypeInteger obj) {
        this.value = (obj.getValue()).doubleValue();
    }
    public DTypeDouble(DTypeString obj) {
        this.value = Double.parseDouble(obj.getValue());
    }
    public DTypeDouble(DTypeBoolean obj) {
        this.value = obj.getValue() ? 1.0 : 0.0;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        DTypeDouble objDouble = (DTypeDouble) obj;
        return value.equals(objDouble.getValue());
    }

    // OPERATIONS

    public DType<?> id() throws Exception {
        return this;
    }

    public DType<?> neg() throws Exception {
        return new DTypeDouble(-value);
    }

    public DType<?> add(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeDouble.add: Can't perform addition with null value.");
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeDouble(value + ((DTypeDouble) obj).getValue());
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeDouble(value + ((DTypeInteger) obj).getValue());
        }
        if (obj instanceof DTypeBoolean) {
            return new DTypeDouble(value + ((DTypeBoolean) obj).getDoubleValue());
        }

        throw new Exception(String.format("DTypeDouble.add: Can't perform addition with given type (%s).", obj.getClass()));
    }

    public DType<?> sub(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeDouble.sub: Can't perform subtraction with null value.");
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeDouble(value - ((DTypeDouble) obj).getValue());
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeDouble(value - ((DTypeInteger) obj).getValue());
        }
        if (obj instanceof DTypeBoolean) {
            return new DTypeDouble(value - ((DTypeBoolean) obj).getDoubleValue());
        }

        throw new Exception(String.format("DTypeDouble.sub: Can't perform subtraction with given type (%s).", obj.getClass()));
    }

    public DType<?> mul(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeDouble.mul: Can't perform multiplication with null value.");
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeDouble(value * ((DTypeDouble) obj).getValue());
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeDouble(value * ((DTypeInteger) obj).getValue());
        }
        if (obj instanceof DTypeBoolean) {
            return new DTypeDouble(value * ((DTypeBoolean) obj).getDoubleValue());
        }

        throw new Exception(String.format("DTypeDouble.mul: Can't perform multiplication with given type (%s).", obj.getClass()));
    }

    public DType<?> div(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeDouble.div: Can't perform division with null value.");
        }
        if (obj instanceof DTypeDouble) {
            if (((DTypeDouble) obj).getValue() == 0) {
                throw new Exception("DTypeDouble.div: Can't perform division with 0.");
            }
            return new DTypeDouble(value / ((DTypeDouble) obj).getValue());
        }
        if (obj instanceof DTypeInteger) {
            if (((DTypeInteger) obj).getValue() == 0) {
                throw new Exception("DTypeDouble.div: Can't perform division with 0.");
            }
            return new DTypeDouble(value / ((DTypeInteger) obj).getValue());
        }
        if (obj instanceof DTypeBoolean) {
            Double objValue = ((DTypeBoolean) obj).getDoubleValue();
            if (objValue == 0) {
                throw new Exception("DTypeInteger.div: Can't perform division with 0.");
            }
            return new DTypeBoolean(value / objValue);
        }

        throw new Exception(String.format("DTypeDouble.div: Can't perform division with given type (%s).", obj.getClass()));
    }

    public DType<?> exp(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeDouble.exp: Can't perform exponentiation with null value.");
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeDouble(Math.pow(value, ((DTypeDouble) obj).getValue()));
        }
        if (obj instanceof DTypeInteger) {
            return new DTypeDouble(Math.pow(value, ((DTypeInteger) obj).getValue()));
        }
        if (obj instanceof DTypeBoolean) {
            return new DTypeDouble(Math.pow(value, ((DTypeBoolean) obj).getDoubleValue()));
        }

        throw new Exception(String.format("DTypeDouble.exp: Can't perform exponentiation with given type (%s).", obj.getClass()));
    }

    public DType<?> mod(Object obj) throws Exception {
        throw new Exception("DTypeDouble.mod: Modulo is undefined for DTypeDouble");
    }

    public DType<?> lt(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeDouble.lt: Can't perform \"less than\" with null value.");
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeBoolean(value < ((DTypeDouble) obj).getValue() ? 1.0 : 0.0);
        }
        if (obj instanceof DTypeInteger) {
            DTypeDouble objDouble = new DTypeDouble(((DTypeInteger) obj).getValue());
            return new DTypeBoolean(value < objDouble.getValue() ? 1.0 : 0.0);
        }

        throw new Exception(String.format("DTypeDouble.lt: Can't perform \"less than\" with given type (%s).", obj.getClass()));
    }

    public DType<?> gt(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("DTypeDouble.gt: Can't perform \"greater than\" with null value.");
        }
        if (obj instanceof DTypeDouble) {
            return new DTypeBoolean(value > ((DTypeDouble) obj).getValue() ? 1.0 : 0.0);
        }
        if (obj instanceof DTypeInteger) {
            DTypeDouble objDouble = new DTypeDouble(((DTypeInteger) obj).getValue());
            return new DTypeBoolean(value > objDouble.getValue() ? 1.0 : 0.0);
        }

        throw new Exception(String.format("DTypeDouble.gt: Can't perform \"greater than\" with given type (%s).", obj.getClass()));
    }

    @Override
    public Double getValue() {
        return value;
    }
    @Override
    public void setValue(Double value) {
        this.value = value;
    }
}
