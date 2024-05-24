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

        this.function  = function;
        this.arguments = arguments;
    }

    /**
     * @return           Function evaluation of given arguments. Every argument must be first recursively evaluated itself.
     * @throws Exception In case of error in the child evaluation, or the function evaluation itself.
     */
    @Override
    public DType<?> evaluate(OperationSet operations) throws Exception {
        // To evaluate a function, values of all the children must be known
        // Therefore, first evaluate the children nodes in AST, then use those values to evaluate the function itself
        DType<?>[] childEvaluations = new DType[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            try {
                childEvaluations[i] = arguments[i].evaluate(operations);
            }
            catch (Exception e) {
                throw new Exception(e);
            }
        }

        // Return function evaluation
        try {
            return function.evaluate(childEvaluations);
        }
        catch (Exception e) {
            throw new Exception(e);
        }
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
