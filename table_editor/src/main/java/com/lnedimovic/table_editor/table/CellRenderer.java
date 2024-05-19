package com.lnedimovic.table_editor.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * A short and simple cell renderer that applies a different style to both table header and each row's header.
 */
public class CellRenderer extends DefaultTableCellRenderer {
    /**
     *
     * @param table      The <code>JTable</code>
     * @param value      The value to assign to the cell at
     *                   <code>[row, column]</code>
     * @param isSelected True if cell is selected
     * @param hasFocus   True if cell has focus
     * @param row        The row of the cell to render
     * @param column     The column of the cell to render
     * @return           Edited component.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Set background gradient for the headers
        if (component instanceof JComponent && (column == 0 || row == -1)) {
            ((JComponent) component).setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(185, 185, 185)),
                    BorderFactory.createMatteBorder(1, 1, 2, 2, new Color(185, 185, 185))
            ));

            // Make headers bolded
            setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));
        }

        setHorizontalAlignment(JLabel.CENTER);

        return component;
    }
}
