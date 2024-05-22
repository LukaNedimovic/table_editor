package com.lnedimovic.table_editor.expression.function;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeArray;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.internal.MethodSorter.getDeclaredMethods;

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

    private boolean turnArgsToArrayBeforeCall = false;

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
     * Creates an instance of Function, given id and types.
     * @param id                       Unique identifier of the function.
     * @param turnArgsToListBeforeCall Whether to turn arguments into a list before function call.
     * @throws Exception               In case of invalid number of types.
     */
    public Function(String id, boolean turnArgsToListBeforeCall) throws Exception {
        if (!validId(id)) {
            throw new Exception("Invalid function id");
        }
        this.id = id;
        this.turnArgsToArrayBeforeCall = turnArgsToListBeforeCall;
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
        if (turnArgsToArrayBeforeCall) {
            if (args.length == 1 && args[0] instanceof DTypeArray) {
                if (((DTypeArray) args[0]).get(0) instanceof DTypeArray) {
                    // Correct shape
                }
                else {
                    args = new DType[]{args[0]};
                }
            }
            else {
                DTypeArray temp = new DTypeArray(new DTypeArray(args));
                args = new DType[]{temp};
            }
        }

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
        return (DType<?>) method.invoke(this, (Object[]) args);
    }

    private Method findMatchingMethod(String methodName, Class<?>[] argumentTypes) {
        for (Method method : getClass().getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                Class<?>[] parameterTypes = method.getParameterTypes();

                if (parameterTypes.length == argumentTypes.length) {
                    boolean matches = true;
                    for (int i = 0; i < parameterTypes.length; i++) {
                        if (!parameterTypes[i].isAssignableFrom(argumentTypes[i])) {
                            matches = false;
                            break;
                        }
                    }

                    if (matches) {
                        return method;
                    }
                }
            }
        }
        return null;
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

    public Class<?>[] getArgumentTypes(DType<?>... args) {
        int numOfArguments  = args.length;

        Class<?>[] argumentTypes = new Class[numOfArguments];
        for (int argIdx = 0; argIdx < args.length; argIdx++) {
            argumentTypes[argIdx] = args[argIdx].getClass();
        }

        return argumentTypes;
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
    public boolean isTurnArgsToArrayBeforeCall() {
        return turnArgsToArrayBeforeCall;
    }
    public void setTurnArgsToArrayBeforeCall(boolean turnArgsToListBeforeCall) {
        this.turnArgsToArrayBeforeCall = turnArgsToListBeforeCall;
    }
}