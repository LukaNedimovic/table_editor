package com.lnedimovic.table_editor.expression.ast.node.nodes;

import com.lnedimovic.table_editor.expression.ast.node.Node;

/**
 * <code>ConstantNode</code> is a Node resembling a constant, known value.
 */
public class ConstantNode extends Node {
    /**
     * Value stored inside the node.
     */
    private final Object value;

    /**
     * Crates an instance of <code>ConstantNode</code>, given value.
     * @param value Value within the node.
     */
    public ConstantNode(Object value) {
        super();

        this.value = value;
    }

    /**
     * @return           Value stored in the node.
     * @throws Exception
     */
    @Override
    public Object evaluate() throws Exception {
        return value;
    }

    /**
     * @return Array of <code>Nodes</code> of length 1, resembling the only value.
     */
    @Override
    public Node[] getChildren() {
        return new Node[0];
    }

    /**
     * @return String representation of ConstantNode.
     */
    public String toString() {
        return String.format("ConstantNode(value=%s)", value);
    }
}
