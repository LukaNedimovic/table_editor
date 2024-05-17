package main.java.com.lnedimovic.table_editor.table.model;

import main.java.com.lnedimovic.table_editor.expression.token.Token;
import main.java.com.lnedimovic.table_editor.expression.Tokenizer;
import main.java.com.lnedimovic.table_editor.expression.Parser;
import main.java.com.lnedimovic.table_editor.expression.ast.ASTree;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>TableModel</code> is a simple table model, whose main purpose is to be able to check for formula and cleanly communicate with other parts of the project.
 */
public class TableModel extends AbstractTableModel {
    /**
     * Row data
     */
    private List<List<Object>> data;
    /**
     * Names of columns.
     */
    private String[]           columnNames;

    /**
     * Tokenizer used with the model.
     */
    private Tokenizer tokenizer;
    /**
     * Parser used with the model.
     */
    private Parser    parser;

    /**
     * Creates an instance of new <code>TableModel</code>.
     *
     * @param data        Row data
     * @param columnNames Column names
     * @param tokenizer   Tokenizer used with the model
     * @param parser      Parser used with the model
     */
    public TableModel(List<List<Object>> data, String[] columnNames, Tokenizer tokenizer, Parser parser) {
        this.data        = data;
        this.columnNames = columnNames;
        this.tokenizer   = tokenizer;
        this.parser      = parser;
    }

    /**
     * Checks for formula and evaluates it if true.
     * Before evaluation, it is mandatory to fill the abstract syntax tree with actual cell values.
     * @param expression Complete expression to tokenize, parse and evaluate
     * @return           String representation of evaluation result, if formula; null, otherwise.
     * @throws Exception In case of invalid expression
     */
    public String checkForFormula(String expression) throws Exception {
        if (expression.startsWith("=")) {
            // First tokenize the expression
            ArrayList<Token> tokens = tokenizer.tokenize(expression);

            // Parse the expression
            ASTree expressionTree = parser.parseTokens(tokens);

            // Before evaluation, cell references need to be filled according to the table model's data
            expressionTree.fillReferences(expressionTree.getRoot(), this);

            // Evaluate and return the value
            return String.valueOf(expressionTree.evaluate());
        }
        else {
            return null;
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        data.get(rowIndex).set(columnIndex, value);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex > 0); // Index row is not editable.
    }
    public Tokenizer getTokenizer() {
        return tokenizer;
    }
    public void setTokenizer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }
    public Parser getParser() {
        return parser;
    }
    public void setParser(Parser parser) {
        this.parser = parser;
    }
}

