package com.lnedimovic.table_editor.table.handlers;

import com.lnedimovic.table_editor.table.StandardTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * <code>SelectionHandler</code> is a simple handler whose main job is to detect change in selection of cells within the table.
 * Whenever one occurs, its job is to check whether user tried to select a complete row.
 * It can be extended for additional functionality.
 */
public class SelectionHandler implements ListSelectionListener {
    /**
     * Table this <code>SelectionHandler</code> applies to.
     */
    private StandardTable table;

    /**
     * Creates an instance of SelectionHandler.
     * @param table Table this <code>SelectionHandler</code> applies to.
     */
    public SelectionHandler(StandardTable table) {
        this.table = table;
    }

    /**
     * Given event <code>e</code>, check whether the user tried selecting the complete row, using one click on index row.
     * @param e The event that characterizes the change.
     */
    public void valueChanged(ListSelectionEvent e) {
        if (table != null) {
            table.checkForSelectingRow(); // In case the cell in the index row has been clicked, select the complete row
            // table.printSelectedCells();   // Print selected cells, if needed
        }
    }

    public JTable getTable() {
        return table;
    }
    public void setTable(StandardTable table) {
        this.table = table;
    }
}