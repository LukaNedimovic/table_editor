package com.lnedimovic.table_editor.table.handlers;

import com.lnedimovic.table_editor.table.StandardTable;

import javax.swing.event.MouseInputAdapter;
import javax.swing.table.JTableHeader;
import java.awt.event.MouseEvent;

/**
 * <code>HeaderClickHandler</code> is a simple MouseInputAdapter that lets users interact with table's header.
 * Its main purpose is to do complete column selection.
 */
public class HeaderClickHandler extends MouseInputAdapter {
    /**
     * Table this <code>HeaderClickHandler</code>> is tied to.
     */
    private StandardTable table;

    /**
     *
     * @param table Table this <code>HeaderClickHandler</code> is tied to.
     */
    public HeaderClickHandler(StandardTable table) {
        this.table = table;
    }

    /**
     * In case of mouse click, check whether the column was clicked and, if yes, select the whole column.
     *
     * @param e The event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        JTableHeader header = (JTableHeader) e.getSource();

        int column = header.columnAtPoint(e.getPoint());
        if (column != -1) { // If column is clicked, select everything inside of it
            table.setRowSelectionInterval(0, table.getRowCount() - 1); // Select all the rows
            table.setColumnSelectionInterval(column, column);          // Select the entire column

//            table.printSelectedCells(); // Print selected cells, if needed
        }
    }
}