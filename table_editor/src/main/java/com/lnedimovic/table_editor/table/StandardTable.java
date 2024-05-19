package com.lnedimovic.table_editor.table;

import com.lnedimovic.table_editor.table.model.TableModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * StandardTable is an exemplary table created to store simple data, such as one of datatype Double showed in the example.
 * It is meant to be capable of selecting multiple cells, and by using one click selecting entire rows / columns.
 * Additionally, by clicking on any other cell, a prompt appears, asking for user input.
 */
public class StandardTable extends JTable {
    /**
     * Creates an instance of <code>StandardTable</code>, given model.
     * @param model TableModel to be followed.
     */
    public StandardTable(TableModel model) {
        super(model);
    }

    /**
     * Prints currently selected cells to the console.
     */
    public void printSelectedCells() {
        // Extract all the rows and columns selected
        int[] selectedRows    = getSelectedRows();
        int[] selectedColumns = getSelectedColumns();

        // Combine them to get the selected cells - selection is of rectangular shape, exclusively
        for (int row : selectedRows) {
            for (int col : selectedColumns) {
                System.out.print("Cell(" + row + ", " + col + ") = " + getValueAt(row, col) + "; ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * In case user clicks on index row, the complete row should be indexed.
     * This feature is not used specifically, but is a nice touch of flexibility.
     */
    public void checkForSelectingRow() {
        int[] selectedRows    = getSelectedRows();
        int[] selectedColumns = getSelectedColumns();

        // If clicked on the index row
        if (selectedRows.length == 1 && selectedColumns.length == 1 && selectedColumns[0] == 0) {
            // Get the row and set the corresponding row
            int row = selectedRows[0];

            setRowSelectionInterval(row, row);                   // Select the row
            setColumnSelectionInterval(1, getColumnCount() - 1); // Select all the columns (excluding the row header)
        }
    }

    /**
     * Prepares cell rendered.
     * @param renderer  The <code>TableCellRenderer</code> to prepare
     * @param row       The row of the cell to render, where 0 is the first row
     * @param col       The column of the cell to render,
     *                  where 0 is the first column
     * @return
     */
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        if (col == 0) {
            return this.getTableHeader().getDefaultRenderer()
                       .getTableCellRendererComponent(this, this.getValueAt(row, col), false, false, row, col);
        }
        else {
            return super.prepareRenderer(renderer, row, col);
        }
    }
}
