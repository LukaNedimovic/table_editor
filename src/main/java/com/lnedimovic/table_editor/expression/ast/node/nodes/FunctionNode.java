package com.lnedimovic.table_editor.expression.ast.node.nodes;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.expression.ast.node.Node;
import com.lnedimovic.table_editor.expression.function.Function;
import com.lnedimovic.table_editor.expression.operation.OperationSet;

/**
 * <code>FunctionNode</code> is a Node resembling a function of variable length of parameters.
 */
public class FunctionNode extends Node {
    /**
     * Function used within the node.
     */
    private Function function;
    /**
     * List of all arguments passed to the function.
     */
    private Node[] arguments;

    /**
     * Creates a new instance of <code>FunctionNode</code>, given function and arguments.
     * @param function  Function used within the node.
     * @param arguments Arguments passed to the function.
     */
    public FunctionNode(Function function, Node[] arguments) {
        super();

        this.function = function;
        this.arguments = arguments;
    }

    /**
     * @return           Function evaluation of given arguments. Every argument must be first recursively evaluated itself.
     * @throws Exception
     */
    @Override
    public DType<?> evaluate(OperationSet operations) throws Exception {
        DType<?>[] childEvaluations = new DType[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            childEvaluations[i] = (DType<?>) arguments[i].evaluate(operations);
        }

        return function.evaluate(childEvaluations);
    }

    /**
     * @return Array of <code>Nodes</code> of variable length, resembling the arguments passed to the function.
     */
    @Override
    public Node[] getChildren() {
        return arguments;
    }

    /**
     * @return String representation of <code>FunctionNode</code>.
     */
    public String toString() {
        return String.format("FunctionNode(arguments=%s)", (Object) arguments);
    }
    public Function getFunction() {
        return function;
    }
    public void setFunction(Function function) {
        this.function = function;
    }
    public Node[] getArguments() {
        return arguments;
    }
    public void setArguments(Node[] arguments) {
        this.arguments = arguments;
    }
}
