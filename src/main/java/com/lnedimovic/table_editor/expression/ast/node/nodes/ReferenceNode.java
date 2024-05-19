package com.lnedimovic.table_editor.expression.ast.node.nodes;

import com.lnedimovic.table_editor.expression.ast.node.Node;

/**
 * <code>ReferenceNode</code>  is a Node resembling a cell reference.
 */
public class ReferenceNode extends Node {
    /**
     * Cell range being referenced.
     */
    private String reference;
    /**
     * Value to be stored.
     */
    private Object value;

    /**
     * Creates an instance of <code>ReferenceNode</code>, given the cell range reference.
     * @param reference Cell range.
     */
    public ReferenceNode(String reference) {
        super();

        this.reference = reference;
    }

    /**
     * @return           Value inside given cell range (as of now, just a single cell).
     * @throws Exception
     */
    @Override
    public Object evaluate() throws Exception {
        return this.value;
    }

    /**
     * @return Array of <code>Nodes</code> of length 1, resembling the only value inside the cell.
     */
    @Override
    public Node[] getChildren() {
        return new Node[0]; // Redundant
    }

    /**
     * @return String representation of <code>ReferenceNode</code>.
     */
    public String toString() {
        return String.format("ReferenceNode(reference=%s, value=%s)", reference, value);
    }

    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
}
