package main.java.com.lnedimovic.table_editor.expression.ast.node.nodes;

import main.java.com.lnedimovic.table_editor.expression.ast.node.Node;
import main.java.com.lnedimovic.table_editor.expression.operation.Operation;

/**
 * <code>BinaryOpNode</code> is a Node resembling a binary operation within an abstract syntax tree of the given expression.
 */
public class BinaryOpNode extends Node {
    /**
     * Operation used within the node.
     */
    private Operation operation;
    /**
     * Left operand.
     */
    private Node left;
    /**
     * Right operand.
     */
    private Node right;

    /**
     * Creates an instance of <code>BinaryOpNode</code>, given operation, and left and right children.
     * @param operation Operation performed on left and right children.
     * @param left      Node resembling left operand.
     * @param right     Node resembling right operand.
     */
    public BinaryOpNode(Operation operation, Node left, Node right) {
        super();

        this.operation = operation;
        this.left = left;
        this.right = right;
    }

    /**
     * @return            Operation evaluation given two children - left and right.
     * @throws Exception
     */
    @Override
    public Object evaluate() throws Exception {
        Object evaluationLeft = left.evaluate();
        Object evaluationRight = right.evaluate();

        return operation.evaluate(evaluationLeft, evaluationRight);
    }

    /**
     * @return Array of <code>Nodes</code> of length 2, resembling left and right children.
     */
    @Override
    public Node[] getChildren() {
        return new Node[]{left, right};
    }

    /**
     * @return String representation of BinaryOpNode.
     */
    public String toString() {
        return String.format("BinOpNode(left=%s, right=%s, op=%s)", left, right, operation);
    }

    public Node getLeft() {
        return left;
    }
    public void setLeft(Node left) {
        this.left = left;
    }
    public Node getRight() {
        return right;
    }
    public void setRight(Node right) {
        this.right = right;
    }
    public Operation getOperation() {
        return operation;
    }
    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
