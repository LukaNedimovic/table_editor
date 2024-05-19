package com.lnedimovic.table_editor.expression.ast.node.nodes;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.expression.ast.node.Node;
import com.lnedimovic.table_editor.expression.operation.OperationSet;

/**
 * <code>ConstantNode</code> is a Node resembling a constant, known value.
 */
public class ConstantNode extends Node {
    /**
     * Value stored inside the node.
     */
    private final DType<?> value;

    /**
     * Crates an instance of <code>ConstantNode</code>, given value.
     * @param value Value within the node.
     */
    public ConstantNode(DType<?> value) {
        super();

        this.value = value;
    }

    /**
     * @return           Value stored in the node.
     * @throws Exception
     */
    @Override
    public DType<?> evaluate(OperationSet operations) throws Exception {
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
