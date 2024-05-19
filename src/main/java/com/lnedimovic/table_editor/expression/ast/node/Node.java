package com.lnedimovic.table_editor.expression.ast.node;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.expression.operation.OperationSet;

/**
 * Abstract class representing a node inside abstract syntax tree.
 */
public abstract class Node {
    /**
     * Creates new instance of <code>Node</code>.
     */
    public Node() {}

    /**
     * @return           Evaluation of given node - first evaluating its children, then the node itself.
     * @throws Exception
     */
    public abstract DType<?> evaluate(OperationSet operations) throws Exception;

    /**
     * @return Array containing all the node's children.
     */
    public abstract Node[] getChildren();
}
