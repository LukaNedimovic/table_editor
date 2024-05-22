package com.lnedimovic.table_editor;

// Utilities
import java.util.ArrayList;
import java.util.List;

// DTypes
import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeInteger;

// Operations and Functions
import com.lnedimovic.table_editor.expression.operation.Operation;
import com.lnedimovic.table_editor.expression.operation.OperationSet;

import com.lnedimovic.table_editor.expression.function.Function;
import com.lnedimovic.table_editor.expression.function.functions.*;

// Parser and Tokenizer
import com.lnedimovic.table_editor.expression.Parser;
import com.lnedimovic.table_editor.expression.Tokenizer;

// Table
import javax.swing.*;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.lnedimovic.table_editor.table.view.TableView;

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
        OperationSet operations = setupOperations();
        Function[]  functions   = setupFunctions();

        // Create tokenizers and parsers for given set of operations
        Tokenizer tokenizer = new Tokenizer(operations, functions);
        Parser parser       = new Parser(operations, functions);

        // Using modern custom UI
        FlatMacDarkLaf.setup();

        SwingUtilities.invokeLater(() -> {
            List<List<DType<?>>> data = new ArrayList<>();
            // Exemplary values: 1 <= ROW_COUNT <= 99, 1 <= COL_COUNT <= 26
            final int ROW_COUNT = 10;
            final int COL_COUNT = 26;

            // Setup values in each row
            // In this example, we just set the values from 1 until the number of cells
            int cnt = 1;
            for (int row = 1; row <= ROW_COUNT; row++) {
                data.add(new ArrayList<>());
                data.get(row - 1).add(new DTypeInteger(row)); // The first element in the row is its index

                for (int col = 1; col <= 26; col++) {
                    data.get(row - 1).add(new DTypeInteger(cnt++)); // For each, set a new exemplary value
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
     *   (01) Identity       - unary "+p", takes no effect
     *   (02) Negation       - unary "-p", negates the number
     *   (03) Addition       - binary "p + ", adds two numbers
     *   (04) Subtraction    - binary "p - q", subtracts two numbers
     *   (05) Multiplication - binary "p * q", multiplies two numbers
     *   (06) Division       - binary "p / q", divides two numbers
     *   (07) Exponentiation - binary "p ^ q", calculates p to the power of q
     *   (08) Modulo         - binary "p % q", calculates p modulo q
     *   (09) LessThan       - binary "p < q", returns 1.0 if p < q, otherwise 0.0
     *   (10) GreaterThan    - binary "p > q", returns 0.0 if p > q, otherwise 0.0
     * <p>
     * It is possible to expand upon this set of operations with custom ones.
     * They are expected to be created by the developer themselves, keeping track of whether any have same operators.
     *
     * @return Array of operations created.
     */
    public static OperationSet setupOperations() {
        // Unary operations
        Operation id   = new Operation("+", 300, true);
        Operation neg  = new Operation("-", 300, true);

        // Binary operations
        Operation add  = new Operation("+", 100, false);
        Operation sub  = new Operation("-", 100, false);

        Operation mul  = new Operation("*", 200, false);
        Operation div  = new Operation("/", 200, false);

        Operation exp  = new Operation("^", 400, false);
        Operation mod  = new Operation("%", 400, false);

        // Logical operations are also supported
        Operation lt   = new Operation("<", 50, false);
        Operation gt   = new Operation(">", 50, false);

        Operation[] operations = new Operation[]{id, neg, add, sub, mul, div, exp, mod, lt, gt};
        return new OperationSet(operations);
    }

    /**
     * Creates the exemplary set of functions, namely:
     *   (01) ConstE     - e(),                   returns constant Math.E
     *   (02) ConstPI    - pi(),                  returns constant Math.PI
     *   (03) Abs        - abs(p),                returns absolute value of p
     *   (04) Sqrt       - sqrt(p),               returns the square root of p
     *   (05) Pow        - pow(p, q),             returns p to the power of q
     *   (06) GCD        - gcd(p, q),             returns the greatest common divisor of p and q
     *   (07) LCM        - lcm(p, q),             returns the lowest common multiple of p and q
     *   (08) Min        - min(p, q),             returns the smaller element among p and q
     *   (09) Max        - max(p, q),             returns the greater element among p and q
     *   (10) IFEQ       - ifeq(p, q),            returns whether p and q are equivalent (DType included)
     *   (11) Sum        - sum(p0, p1, ...),      returns the summation of all the values provided
     *   (12) Average    - average(p0, p1, ...),  returns the average of all the values provided
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
            Function e       = new ConstE("e");
            Function pi      = new ConstPI("pi");

            // Unary functions
            Function abs     = new Abs("abs");
            Function sqrt    = new Sqrt("sqrt");

            // Binary functions
            Function pow     = new Pow("pow");
            Function gcd     = new GCD("gcd");
            Function lcm     = new LCM("lcm");

            Function min     = new Min("min");
            Function max     = new Max("max");

            // Conditional functions also exist
            Function ifeq    = new IFEQ("ifeq");

            // Arbitrary number of parameter functions
            Function sum     = new Sum("sum");
            Function average = new Average("average");

            functions = new Function[]{e, pi, abs, sqrt, pow, gcd, lcm, min, max, ifeq, sum, average};
        }
        catch (Exception e) {
            System.out.println("Error while setting up operations: " + e.getMessage());
        }

        return functions;
    }
}