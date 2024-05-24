package com.lnedimovic.table_editor.expression.ast.node.nodes;

import com.lnedimovic.table_editor.dtype.dtypes.DTypeArray;
import com.lnedimovic.table_editor.expression.ast.node.Node;
import com.lnedimovic.table_editor.expression.operation.OperationSet;

/**
 * <code>ArrayNode</code> is a Node resembling a typical array, whose values may not be final (i.e. they are to be evaluated).
 */
public class ArrayNode extends Node {
    Node[] values;

    public ArrayNode(Node[] values) {
        super();

        this.values = values;
    }

    /**
     * @return            Evaluation of all child nodes and return a DTypeArray.
     * @throws Exception
     */
    @Override
    public DTypeArray evaluate(OperationSet operations) throws Exception {
        DTypeArray array = new DTypeArray(values.length);

        for (int idx = 0; idx < values.length; idx++) {
            array.set(idx, values[idx].evaluate(operations));
        }

        return array;
    }

    /**
     * @return Array of <code>Nodes</code> of variable length, resembling the arguments passed to the function.
     */
    @Override
    public Node[] getChildren() {
        return values;
    }

    /**
     * @return String representation of <code>ArrayNode</code>.
     */
    public String toString() {
        return String.format("ArrayNode(arguments=%s)", (Object) values);
    }
}
