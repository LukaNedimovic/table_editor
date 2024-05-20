package com.lnedimovic.table_editor.expression.function;

import com.lnedimovic.table_editor.dtype.DType;

import java.util.Arrays;

/**
 * Abstract class representing a N-ary function.
 * The goal of the function is to create a basis for relatively safe creation of custom functions, that span wider from standard mathematical ones (like pow(), abs() and so on).
 * <p>
 * Every function implemented inherits from this class.
 */
public abstract class Function {
    /**
     * Id of operation, i.e. it's representation symbol. Starts with a lowercase letter and can only contain letters and digits.
     */
    private String id;

    /**
     * Arity of a function is number of values function uses.
     * As of now, it has to be a fixed amount, but it will be very easy to modify this approach.
     */
    private int arity;

    /**
     * Precedence of function. By default, it is set to 0, as in the "least precedence".
     * 0 should be used as the lowest precedence possible.
     */
    private int precedence = 0;

    /**
     * Parameter types is a list of parameter types that a function accepts.
     * As of now, every function accepts Double parameter type, but this approach makes it very flexible to test whether certain value can even be used by a function.
     */
    private Class<?>[] parameterTypes;

    /**
     * Return type of the function.
     * As of now, it is only Double.
     */
    private Class<?>   returnType;

    /**
     * Creates an instance of Function, given id and types.
     * @param id         Unique identifier of the function.
     * @param types      Types used by function. The last type provided is used as return type (it must be provided).
     * @throws Exception In case of invalid number of types.
     */
    public Function(String id, Class<?>... types) throws Exception {
        if (!validId(id)) {
            throw new Exception("Invalid function id");
        }
        this.id = id;

        // As of now, functions without parameters are supported. Return type still has to be provided, though.
        if (types.length < 1) {
            throw new Exception("Error while creating " + id + ": Number of provided types must be at least 1 (one for output).");
        }

        this.arity = types.length - 1; // Arity is defined as the number of arguments inside a function

        this.parameterTypes = new Class[arity]; // Up until last element are the input types
        for (int i = 0; i < arity; i++) {
            this.parameterTypes[i] = types[i];
        }

        this.returnType = types[arity]; // Last element of types is the return type
    }

    /**
     * Abstract method, to be overridden by every operation created, that defines how operation should be evaluated.
     * This is the "core" of functions - it is the place where the developer encodes the logic of each function and makes it scalable and highly flexible.
     * @param args       Array of objects passed into given function. Its length must be equal to function's arity.
     * @return           Result of function evaluation.
     * @throws Exception In case of invalid number of arguments or invalid data.
     */
    public abstract DType<?> evaluate(DType<?>... args) throws Exception;

    public boolean validId(String id) {
        boolean validCharacters = true;
        for (int i = 1; i < id.length(); i++) {
            validCharacters &= Character.isLetterOrDigit(id.charAt(i));
        }
        return !id.isEmpty() && Character.isLowerCase(id.charAt(0)) && validCharacters;
    }

    /**
     * @return String representation of function
     */
    public String toString() {
        return String.format("Function(id=%s, returnType=%s)", id, returnType);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Function) {
            return obj.equals(this);
        }
        if (obj instanceof String) {
            return obj.equals(id);
        }

        return false;
    }

    public DType<?> convertToDType(DType<?> obj, Class<?> type) throws Exception {
        return (DType<?>) type.getDeclaredConstructor(obj.getClass()).newInstance(obj);
    }

    public void convertToValidDTypes(DType<?>[] args) throws Exception {
        for (int i = 0; i < arity; i++) {
            args[i] = convertToDType(args[i], parameterTypes[i]);
        }
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getArity() {
        return arity;
    }
    public void setArity(int arity) {
        this.arity = arity;
    }
    public Object[] getParameterTypes() {
        return parameterTypes;
    }
    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
    public Class<?> getReturnType() {
        return returnType;
    }
    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }
    public int getPrecedence() {
        return precedence;
    }
    public void setPrecedence(int precedence) {
        this.precedence = precedence;
    }
}