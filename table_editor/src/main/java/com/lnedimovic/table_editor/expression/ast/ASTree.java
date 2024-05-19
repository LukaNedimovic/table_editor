package com.lnedimovic.table_editor.expression.ast;

import com.lnedimovic.table_editor.expression.ast.node.Node;
import com.lnedimovic.table_editor.expression.ast.node.nodes.ReferenceNode;
import com.lnedimovic.table_editor.table.model.TableModel;

/**
 * <code>ASTree</code> is a class representing an abstract syntax tree, generated in expression parsing.
 * Its main role is representing the structure of given expression and being able to evaluate it.
 * Before evaluation, cell references have to be supplemented with values.
 */
public class ASTree {
    /**
     * Root node of respective tree.
     */
    private Node root;

    /**
     * Creates an instance of <code>ASTree</code>.
     */
    public ASTree() {}

    /**
     * Creates an instance of <code>ASTree</code>, given root node.
     * @param root Root node of newly created ASTree.
     */
    public ASTree(Node root) {
        this.root = root;
    }

    /**
     * Evaluates the complete tree, using post-order traversal.
     * @return           Evaluation result.
     * @throws Exception If root is unasigned.
     */
    public Object evaluate() throws Exception {
        if (root == null) {
            throw new Exception("Root is not assigned.");
        }

        return this.root.evaluate();
    }

    /**
     * Fills reference cells with concrete values, given <code>TableModel</code> to fetch data from.
     * @param node       Current node.
     * @param model      TableModel used to fetch the data of cells.
     * @throws Exception
     */
    public void fillReferences(Node node, TableModel model) throws Exception {
        if (node instanceof ReferenceNode) {
            // Extract the exact cell and its value
            String cellReference = ((ReferenceNode) node).getReference();
            Object value         = getReferenceValue(cellReference, model);

            // Set the value from given table into the node
            ((ReferenceNode) node).setValue(value);
        }
        else {
            // Recursively fill the rest of the tree with reference values
            for (Node nextNode : node.getChildren()) {
                fillReferences(nextNode, model);
            }
        }
    }

    /**
     * Return value from specific cell.
     *
     * @param range      String representing the cell range. As of now, only singular cells are supported.
     * @param model      <code>TableModel</code> to fetch data from.
     * @return           Cell value.
     * @throws Exception In case of cell range not being a singular cell.
     */
    public String getReferenceValue(String range, TableModel model) throws Exception {
        String leftCell  = range.substring(0, range.indexOf(":"));
        String rightCell = range.substring(range.indexOf(":") + 1);

        // TODO: Range support
        if (!leftCell.equals(rightCell)) {
            throw new Exception("Cell range support to be added.");
        }

        // Internally, 0-column is reserved for row indexing, while the row 0 is standard
        int col = leftCell.charAt(0) - 'A' + 1;
        int row = Integer.parseInt(leftCell.substring(1)) - 1;

        return (String) model.getValueAt(row, col);
    }

    /**
     * @return String representation of <code>ASTree</code>.
     */
    public String toString() {
        return String.format("ASTree(structure=%s)", root);
    }

    public Node getRoot() {
        return root;
    }
    public void setRoot(Node root) {
        this.root = root;
    }
}
