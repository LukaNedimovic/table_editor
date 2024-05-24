package com.lnedimovic.table_editor.table.handlers;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.table.model.TableModel;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * <code>PopupCellEditor</code> is a cell editor meant to guide user experience by prompting the user with simple input dialog and
 * storing the value provided within the right, selected cell.
 */
public class PopupCellEditor extends AbstractCellEditor implements TableCellEditor {
    /**
     * Text field inside a cell.
     */
    private final JTextField textField;

    /**
     * Creates an instance of <code>PopupCellEditor</code>.
     */
    public PopupCellEditor() {
        textField = new JTextField();

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopCellEditing();
            }
        });

        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    stopCellEditing();
                }
            }
        });
    }

    /**
     * In case of user wishing to edit the cell, it prompts them with a simple dialog to enter new cell value.
     * Afterward, the value is checked for being a formula and, if yes, it's being evaluated.
     * If no, new value is directly stored within the cell.
     *
     * @param table           The <code>JTable</code> that is asking the
     *                          editor to edit; can be <code>null</code>
     * @param value           The value of the cell to be edited; it is
     *                          up to the specific editor to interpret
     *                          and draw the value.  For example, if value is
     *                          the string "true", it could be rendered as a
     *                          string or it could be rendered as a check
     *                          box that is checked.  <code>null</code>
     *                          is a valid value
     * @param isSelected      True if the cell is to be rendered with
     *                          highlighting
     * @param row             The row of the cell being edited
     * @param column          The column of the cell being edited
     * @return
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        String expression = JOptionPane.showInputDialog(table, "Enter new cell value:", value);

        // If something has actually been entered
        if (expression != null) {
            TableModel model = (TableModel) table.getModel();
            String newValue;

            DType<?> oldValue = (DType<?>) table.getModel().getValueAt(row, column);

            // Check if the entered expression is formula and try evaluating it
            try {
                if (expression.startsWith("=")) {
                    DType<?> result = model.checkForFormula(expression);
                    if (result == null) {
                        newValue = oldValue.toString();
                    }
                    else {
                        newValue = result.toString();
                    }
                }
                else {
                    newValue = expression;
                }
                textField.setText(newValue);
            }
            // If error occurs, show the error message to the user.
            catch (Exception ex) {
                String exceptionMessage = ex.getMessage();
                JOptionPane.showMessageDialog(null, "Exception occured: " + exceptionMessage);
            }
        }

        stopCellEditing();
        return textField;
    }

    @Override
    public Object getCellEditorValue() {
        return textField.getText();
    }
    @Override
    public boolean isCellEditable(EventObject e) {
        return (e instanceof MouseEvent &&
                ((MouseEvent) e).getClickCount() >= 2) ||
                (e instanceof ActionEvent);
    }
}
