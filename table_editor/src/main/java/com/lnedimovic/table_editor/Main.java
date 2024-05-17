package main.java.com.lnedimovic.table_editor;

// Utilities
import java.util.ArrayList;
import java.util.List;

// Operations and Functions
import main.java.com.lnedimovic.table_editor.expression.operation.Operation;
import main.java.com.lnedimovic.table_editor.expression.operation.operations.unary.*;
import main.java.com.lnedimovic.table_editor.expression.operation.operations.binary.*;

import main.java.com.lnedimovic.table_editor.expression.function.Function;
import main.java.com.lnedimovic.table_editor.expression.function.functions.*;

// Parser and Tokenizer
import main.java.com.lnedimovic.table_editor.expression.Parser;
import main.java.com.lnedimovic.table_editor.expression.Tokenizer;

// Table
import javax.swing.*;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import main.java.com.lnedimovic.table_editor.table.view.TableView;

/**
 * Main class. Class that contains entry point of the project.
 */

public class Main {
    /**
     * Main method - entry point of the program.
     * Used for setting up exemplary operations, functions, then providing them to the tokenizer and the parser.
     * Additionally, setting up table's exemplary data also occurs here.
     * After everything is ready, new <code>TableView</code> is created and shown.
     *
     * @param  args       Command-line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // Setup exemplary operations and functions
        Operation[] operations = setupOperations();
        Function[]  functions  = setupFunctions();

        // Create tokenizers and parsers for given set of operations
        Tokenizer tokenizer = new Tokenizer(operations, functions);
        Parser parser       = new Parser(operations, functions);

        // Using modern custom UI
        FlatMacDarkLaf.setup();

        SwingUtilities.invokeLater(() -> {
            List<List<Object>> data = new ArrayList<>();
            // Exemplary values: 1 <= ROW_COUNT <= 99, 1 <= COL_COUNT <= 26
            final int ROW_COUNT = 10;
            final int COL_COUNT = 26;

            // Setup values in each row
            // In this example, we just set the values from 1 until the number of cells
            int cnt = 1;
            for (int row = 1; row <= ROW_COUNT; row++) {
                data.add(new ArrayList<>());
                data.getLast().add(row); // The first element in the row is its index

                for (int col = 1; col <= 26; col++) {
                    data.getLast().add(String.valueOf((double) cnt++)); // For each, set a new exemplary value
                }
            }

            // Setup column names - columns are named '', 'A' ... 'A' + (COL_COUNT - 1) (i.e. COL_COUNT = 3 -> ''', A, B, C)
            String[] columnNames = new String[COL_COUNT + 1];
            columnNames[0] = "";// The first column is blank
            for (int idx = 1; idx <= COL_COUNT; idx++) {
                columnNames[idx] = String.valueOf((char) ('A' + idx - 1));
            }

            // Create a new Table!
            new TableView(data, columnNames, tokenizer, parser);
        });
    }

    // HELPER FUNCTIONS

    /**
     * Creates the exemplary set of operations, namely:
     *   (1) Identity       - unary "+p", takes no effect
     *   (2) Negation       - unary "-p", negates the number
     *   (3) Addition       - binary "p + ", adds two numbers
     *   (4) Subtraction    - binary "p - q", subtracts two numbers
     *   (5) Multiplication - binary "p * q", multiplies two numbers
     *   (6) Division       - binary "p / q", divides two numbers
     *   (7) Exponentiation - binary "p ^ q ", calculates p to the power of q
     * <p>
     * It is possible to expand upon this set of operations with custom ones.
     * They are expected to be created by the developer themselves, keeping track of whether any have same operators.
     *
     * @return Array of operations created.
     */
    public static Operation[] setupOperations() {
        // Unary operations
        Operation id   = new Identity("+", 300);
        Operation neg  = new Negation("-", 300);

        // Binary operations
        Operation add  = new Addition("+", 100);
        Operation sub  = new Subtraction("-", 100);

        Operation mul  = new Multiplication("*", 200);
        Operation div  = new Division("/", 200);

        Operation exp  = new Exponentiation("^", 400);

        return new Operation[]{id, neg, add, sub, mul, div, exp};
    }

    /**
     * Creates the exemplary set of functions, namely:
     *   (1) ConstE  - e(),        returns constant Math.E
     *   (2) ConstPI - pi(),       returns constant Math.PI
     *   (3) Abs     - abs(p),     returns absolute value of p
     *   (4) Sqrt    - sqrt(p),    returns the square root of p
     *   (5) Pow     - pow(p, q),  returns p to the power of q
     *   (6) Min     - min(p, q),  returns the smaller element among p and q
     *   (7) Max     - max(p, q),  returns the greater element among p and q
     * <p>
     * It is possible to expand upon this set of functions with custom ones.
     * Functions may have any number of parameters, however their number is expected to be constant.
     * They are expected to be created by the developer themselves, keeping track of whether any have same operators.
     *
     * @return Array of functions created.
     */
    public static Function[] setupFunctions() {
        Function[] functions = null;

        try {
            // Functions without arguments - can be useful for constants!
            Function e    = new ConstE("e");
            Function pi   = new ConstPI("pi");

            // Unary functions
            Function abs  = new Abs("abs");
            Function sqrt = new Sqrt("sqrt");

            // Binary functions
            Function pow  = new Pow("pow");

            Function min  = new Min("min");
            Function max  = new Max("max");

            functions = new Function[]{e, pi, abs, sqrt, pow, min, max};
        }
        catch (Exception e) {
            System.out.println("Error while setting up operations: " + e.getMessage());
        }
        /*
        TODO: Support non-unique N-ary functions.
        It is easy to modify the approach to take into consideration more functions of same name, but different parameters.
        Additionally, parameter length doesn't have to be fixed.

        Due to very little time available to implement all of this (travelling, college...), I could not manage to do it,
        but the idea is to:
          (1) findFunction - change to take into consideration the number of parameter each function has, maybe even parameter types
          (2) parser       - change to take into consideration the fact that some functions can have variable length input
                              - function is correctly entered if there exists a function such that it has the same number of parameters,
                                (who are of the same types), and the existing function identifier (name) already holds.
                              - in case there is no function that fulfills this fixed-parameter check, check for functions
                                with variable-length parameter count

        This should not take very long and is quite achievable.
        */

        return functions;
    }
}