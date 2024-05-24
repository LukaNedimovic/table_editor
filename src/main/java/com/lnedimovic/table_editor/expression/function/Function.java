package com.lnedimovic.table_editor.expression.function;

import com.lnedimovic.table_editor.dtype.DType;

import java.lang.reflect.Method;

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
     * @throws Exception In case of invalid number of types.
     */
    public Function(String id) throws Exception {
        if (!validId(id)) {
            throw new Exception("Invalid function id");
        }
        this.id = id;
    }

    /**
     * Evaluate function call by finding the appropriately named method inside the child function.
     * @param args       Array of objects passed into given function.
     * @return           Result of function evaluation.
     * @throws Exception In case of invalid number of arguments or invalid data.
     */
    public DType<?> evaluate(DType<?>... args) throws Exception {
        // As of now, functions can use 2D arrays for their tasks.
        // This, rather ugly, patch of code transforms it into the correct shape.

        // Get the argument types provided
        Class<?>[] argumentTypes = getArgumentTypes(args);

        // Find if method with g
        Method method;
        try {
            method = findMatchingMethod(getId(), argumentTypes);
        }
        catch (Exception e) {
            throw new Exception("Can't find method: " + e.getMessage());
        }

        // If method is found, call it and return.
        try {
            return (DType<?>) method.invoke(this, (Object[]) args);
        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * @param args Arguments provided to the function.
     * @return     Array containing type of every argument, in order.
     */
    public Class<?>[] getArgumentTypes(DType<?>... args) {
        int numOfArguments  = args.length;
        Class<?>[] argumentTypes = new Class[numOfArguments];

        // For each argument, store its class on its respective index
        for (int argIdx = 0; argIdx < args.length; argIdx++) {
            argumentTypes[argIdx] = args[argIdx].getClass();
        }

        return argumentTypes;
    }

    /**
     * Finds matching method in class being used for evaluation.
     * @param methodName    Name of the method. It is always the same as the "identifier" of a function (i.e. "sum")
     * @param argumentTypes List of argument types provided to the class for evaluation.
     * @return              Found method.
     */
    private Method findMatchingMethod(String methodName, Class<?>[] argumentTypes) throws Exception {
        // For each method inside the class
        for (Method method : getClass().getDeclaredMethods()) {
            // Check if method name is desired (i.e. same)
            if (method.getName().equals(methodName)) {
                Class<?>[] parameterTypes = method.getParameterTypes();

                // Number of parameters and provided values must match
                if (parameterTypes.length == argumentTypes.length) {
                    boolean allTypesMatching = true;
                    for (int idx = 0; idx < parameterTypes.length; idx++) {
                        // In case of parameter type mismatch, flip the flag to false and stop checking for other types.
                        if (!parameterTypes[idx].isAssignableFrom(argumentTypes[idx])) {
                            allTypesMatching = false;
                            break;
                        }
                    }

                    // In case of all types matching, return the current function as found!
                    if (allTypesMatching) {
                        return method;
                    }
                }
            }
        }

        // In case of no matching function, throw an exception.
        throw new Exception("Can't find function with given parameter types.");
    }

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

    /**
     * @param obj Object to check equality with.
     * @return    True if objects are equal; false, otherwise.
     */
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