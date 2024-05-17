package main.java.com.lnedimovic.table_editor.expression.ast.node.nodes;

import main.java.com.lnedimovic.table_editor.expression.ast.node.Node;
import main.java.com.lnedimovic.table_editor.expression.operation.Operation;

/**
 * <code>UnaryOpNode</code> is a Node resembling a unary operation within an abstract syntax tree of the given expression.
 */
public class UnaryOpNode extends Node {
    /**
     * Operation used within the node.
     */
    private Operation operation;
    /**
     * Operand.
     */
    private Node next;

    /**
     * Creates an instance of <code>UnaryOpNode</code>, given operation and operand.
     * @param operation Operation within the node.
     * @param next      Operand.
     */
    public UnaryOpNode(Operation operation, Node next) {
        super();

        this.operation = operation;
        this.next = next;
    }

    /**
     * @return           Operation evaluation given a singular operand.
     * @throws Exception
     */
    @Override
    public Object evaluate() throws Exception {
        Object childEvaluation = next.evaluate();

        return operation.evaluate(childEvaluation);
    }

    /**
     * @return Array of <code>Nodes</code> of length 1, resembling the only operand.
     */
    @Override
    public Node[] getChildren() {
        return new Node[]{next};
    }

    /**
     * @return String representation of <code>UnaryOpNode</code>
     */
    public String toString() {
        return String.format("UnaryOpNode(next=%s)", next);
    }

    public Node getNext() {
        return next;
    }
    public void setNext(Node next) {
        this.next = next;
    }
    public Operation getOperation() {
        return operation;
    }
    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
