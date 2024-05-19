package com.lnedimovic.table_editor.expression.operation;

import com.lnedimovic.table_editor.dtype.DType;

public class OperationSet {
    public Operation[] operations;

    public OperationSet(Operation[] operations) {
        this.operations = operations;
    }

    public DType<?> evaluateOperation(Operation operation, DType<?> left, DType<?> right) throws Exception {
        String id       = operation.getId();
        int precedence  = operation.getPrecedence();
        boolean isUnary = operation.isUnary();

        DType<?> result = null;

        if (isUnary) {
            switch (id) {
                case "+": {
                    result = left.id();
                    break;
                }
                case "-": {
                    result = left.neg();
                    break;
                }
                default: {
                    throw new Exception(String.format("Unary operation not found: %s", id));
                }
            }
        }
        else {
            switch (id) {
                case "+": {
                    try {
                        result = left.add(right);
                    }
                    catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    break;
                }
                case "-": {
                    try {
                        result = left.sub(right);
                    }
                    catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    break;
                }
                case "*": {
                    try {
                        result = left.mul(right);
                    }
                    catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    break;
                }
                case "/": {
                    try {
                        result = left.div(right);
                    }
                    catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    break;
                }
                case "^": {
                    try {
                        result = left.exp(right);
                    }
                    catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    break;
                }
                case "%": {
                    try {
                        result = left.mod(right);
                    }
                    catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    break;
                }
                case "<": {
                    try {
                        result = left.lt(right);
                    }
                    catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    break;
                }
                case ">": {
                    try {
                        result = left.gt(right);
                    }
                    catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                    break;
                }
                default: {
                    throw new Exception(String.format("Binary operation not found: %s", id));
                }
            }
        }

        return result;
    }

    public Operation find(String id) {
        for (Operation operation : operations) {
            if (operation.getId().equals(id)) {
                return operation;
            }
        }

        return null;
    }

    public Operation find(String id, boolean isUnary) {
        for (Operation operation : operations) {
            if (operation.getId().equals(id) && operation.isUnary() == isUnary) {
                return operation;
            }
        }

        return null;
    }
}
