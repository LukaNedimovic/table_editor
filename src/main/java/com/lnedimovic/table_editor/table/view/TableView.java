package com.lnedimovic.table_editor.table.view;

import com.lnedimovic.table_editor.expression.Parser;
import com.lnedimovic.table_editor.expression.Tokenizer;

import com.lnedimovic.table_editor.table.CellRenderer;
import com.lnedimovic.table_editor.table.StandardTable;
import com.lnedimovic.table_editor.table.handlers.*;
import com.lnedimovic.table_editor.table.model.TableModel;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

/**
 * <code>TableView</code> is a class used to setting up the <code>JFrame</code> presented to the user.
 * Additionally, within this class, <code>StandardTable</code> is being given most of its functionality.
 */
public class TableView extends JFrame {
    /**
     * Minimum width of a column.
     */
    private final int MIN_WIDTH_COLUMN = 70;
    /**
     * Minimum height of a row.
     */
    private final int MIN_HEIGHT_ROW   = 40;

    /**
     * Table this view applies to.
     */
    private StandardTable table;

    /**
     * Model the <code>table</code> is using.
     */
    private TableModel    model;

    /**
     * Creates a new instance of TableView.
     *
     * @param data        Row data.
     * @param columnNames Column names.
     * @param tokenizer   <code>Tokenizer</code> to be used within the table.
     * @param parser      <code>Parser</code> to be used within the table.
     */
    public TableView(List<List<Object>> data, String[] columnNames, Tokenizer tokenizer, Parser parser) {
        // Table Setup
        model = new TableModel(data, columnNames, tokenizer, parser);
        table = new StandardTable(model);

        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Cell rendering
        table.setDefaultRenderer(Object.class, new CellRenderer());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setShowGrid(true);

        // Column and row setup
        for (int col = 0; col < columnNames.length; col++) {
            TableColumn column = table.getColumn(columnNames[col]);
            column.setPreferredWidth(MIN_WIDTH_COLUMN);
            column.setCellEditor(new PopupCellEditor());
        }
        table.setRowHeight(MIN_HEIGHT_ROW);
        table.setAutoCreateRowSorter(false);

        // Table Header setup
        JTableHeader header = table.getTableHeader();
        header.addMouseListener(new HeaderClickHandler(table));
        header.setDefaultRenderer(new CellRenderer());
        header.setReorderingAllowed(false);
        header.setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));


        // EVENT HANDLERS
        // General selection
        table.getSelectionModel().addListSelectionListener(new SelectionHandler(table));
        table.getColumnModel().getSelectionModel().addListSelectionListener(new SelectionHandler(table));

        // Scroll Pane setup
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        // General window settings
        setTitle("\uD83D\uDCDD Simple Table Editor (by Luka Nedimović)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 1024);

        // Show the window!
        setVisible(true);
    }
}