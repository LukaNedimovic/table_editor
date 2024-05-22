package com.lnedimovic.table_editor;

import com.lnedimovic.table_editor.dtype.DType;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeBoolean;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeDouble;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeInteger;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeString;
import com.lnedimovic.table_editor.expression.Parser;
import com.lnedimovic.table_editor.expression.Tokenizer;
import com.lnedimovic.table_editor.expression.ast.ASTree;
import com.lnedimovic.table_editor.expression.function.Function;
import com.lnedimovic.table_editor.expression.function.functions.*;
import com.lnedimovic.table_editor.expression.operation.Operation;
import com.lnedimovic.table_editor.expression.operation.OperationSet;
import com.lnedimovic.table_editor.expression.token.Token;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TestParser {
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

    public static Function[]   setupFunctions() {
        Function[] functions = null;

        try {
            // Functions without arguments - can be useful for constants!
            Function e = new ConstE("e");
            Function pi = new ConstPI("pi");

            // Unary functions
            Function abs = new Abs("abs");
            Function sqrt = new Sqrt("sqrt");

            // Binary functions
            Function pow = new Pow("pow");
            Function gcd = new GCD("gcd");
            Function lcm = new LCM("lcm");

            Function min = new Min("min");
            Function max = new Max("max");

            // Conditional functions also exist
            Function ifeq = new IFEQ("ifeq");

            // Arbitrary number of parameter functions
            Function sum = new Sum("sum");
            Function average = new Average("average");

            functions = new Function[]{e, pi, abs, sqrt, pow, gcd, lcm, min, max, ifeq, sum, average};
        } catch (Exception e) {
            System.out.println("Error while setting up operations: " + e.getMessage());
        }

        return functions;
    }

    public static OperationSet operations;
    public static Function[]   functions;

    public static Tokenizer    tokenizer;
    public static Parser       parser;

    @BeforeClass
    public static void setUp() {
        operations = setupOperations();
        functions  = setupFunctions();

        tokenizer = new Tokenizer(operations, functions);
        parser    = new Parser(operations, functions);
    }

    public DType<?> evaluate(String expression) throws Exception {
        ArrayList<Token> tokens = tokenizer.tokenize(expression);
        ASTree ast = parser.parseTokens(tokens);

        return ast.evaluate(operations);
    }

    @Test
    public void testUnaryOp() throws Exception {
        // Identity (+p)
        //// DTypeDouble
        assertEquals(evaluate("=+0.01"),     new DTypeDouble(0.01));
        assertEquals(evaluate("=+1.252626"), new DTypeDouble(1.252626));
        assertEquals(evaluate("=+3.0"),      new DTypeDouble(3.0));
        assertEquals(evaluate("=+0.01"),     new DTypeDouble(0.01));
        assertEquals(evaluate("=+5.5555"),   new DTypeDouble(5.5555));
        assertEquals(evaluate("=+++5.5555"), new DTypeDouble(5.5555));

        //// DTypeInteger
        assertEquals(evaluate("=+0"),          new DTypeInteger(0));
        assertEquals(evaluate("=+1"),          new DTypeInteger(1));
        assertEquals(evaluate("=+125"),        new DTypeInteger(125));
        assertEquals(evaluate("=+1000"),       new DTypeInteger(1000));
        assertEquals(evaluate("=+219039123"),  new DTypeInteger(219039123));
        assertEquals(evaluate("=++219039123"), new DTypeInteger(219039123));

        //// DTypeBoolean
        assertEquals(evaluate("=+True"),     new DTypeBoolean(true));
        assertEquals(evaluate("=+False"),    new DTypeBoolean(false));
        assertEquals(evaluate("=++++False"), new DTypeBoolean(false));

        //// DTypeString
        assertEquals(evaluate("=+\"test\""),                               new DTypeString("test"));
        assertEquals(evaluate("=+\"string with spaces\""),                 new DTypeString("string with spaces"));
        assertEquals(evaluate("=+\"string with spaces and 123 numbers\""), new DTypeString("string with spaces and 123 numbers"));
        assertEquals(evaluate("=+\"1 5 9012 -1230' ; 2321\""),             new DTypeString("1 5 9012 -1230' ; 2321"));
        assertEquals(evaluate("=+\"\""),                                   new DTypeString(""));
        assertEquals(evaluate("=++++\"1 5 9012 -1230' ; 2321\""),          new DTypeString("1 5 9012 -1230' ; 2321"));

        // Negation (-p)
        //// DTypeDouble
        assertEquals(evaluate("=-0.01"),     new DTypeDouble(-0.01));
        assertEquals(evaluate("=-1.252626"), new DTypeDouble(-1.252626));
        assertEquals(evaluate("=-3.0"),      new DTypeDouble(-3.0));
        assertEquals(evaluate("=-0.01"),     new DTypeDouble(-0.01));
        assertEquals(evaluate("=-5.5555"),   new DTypeDouble(-5.5555));
        assertEquals(evaluate("=--5.5555"),   new DTypeDouble(5.5555));
        assertEquals(evaluate("=---5.5555"),   new DTypeDouble(-5.5555));

        //// DTypeInteger
        assertEquals(evaluate("=-0"),         new DTypeInteger(-0));
        assertEquals(evaluate("=-1"),         new DTypeInteger(-1));
        assertEquals(evaluate("=-125"),       new DTypeInteger(-125));
        assertEquals(evaluate("=-1000"),      new DTypeInteger(-1000));
        assertEquals(evaluate("=-219039123"), new DTypeInteger(-219039123));
        assertEquals(evaluate("=--219039123"), new DTypeInteger(219039123));
        assertEquals(evaluate("=---219039123"), new DTypeInteger(-219039123));

        //// DTypeBoolean
        assertEquals(evaluate("=-True"), new DTypeBoolean(false));
        assertEquals(evaluate("=-False"), new DTypeBoolean(true));
        assertEquals(evaluate("=--False"), new DTypeBoolean(false));
        assertEquals(evaluate("=---False"), new DTypeBoolean(true));

        //// DTypeString
        assertThrows(Exception.class, () -> { evaluate("=-\"test\"");});
        assertThrows(Exception.class, () -> {evaluate("=-\"string with spaces\"");});
        assertThrows(Exception.class, () -> {evaluate("=-\"string with spaces and 123 numbers\"");});
        assertThrows(Exception.class, () -> {evaluate("=-\"1 5 9012 -1230' ; 2321\"");});
        assertThrows(Exception.class, () -> {evaluate("=--\"1 5 9012 -1230' ; 2321\"");});
        assertThrows(Exception.class, () -> {evaluate("=---\"1 5 9012 -1230' ; 2321\"");});
    }

    // BINARY OPERATIONS

    @Test
    public void testAddition() throws Exception {
        // Addition (p + q)
        //// DTypeDouble (left) + DType<?> (right)
        assertEquals(evaluate("=1.5 + 2.5"),   new DTypeDouble(4));
        assertEquals(evaluate("=1.5 + 2"),     new DTypeDouble(3.5));
        assertEquals(evaluate("=1.5 + True"),  new DTypeDouble(2.5));
        assertEquals(evaluate("=1.5 + False"), new DTypeDouble(1.5));
        assertThrows(Exception.class,                   () -> {evaluate("=1.5 + \"123\"");});
        assertThrows(Exception.class,                   () -> {evaluate("=1.5 + \"asd\"");});

        //// DTypeInteger (left) + DType<?> (right)
        assertEquals(evaluate("=5 + 2.5"),   new DTypeDouble(7.5));
        assertEquals(evaluate("=5 + 2"),     new DTypeInteger(7));
        assertEquals(evaluate("=5 + True"),  new DTypeInteger(6));
        assertEquals(evaluate("=5 + False"), new DTypeInteger(5));
        assertThrows(Exception.class,                 () -> {evaluate("=1 + \"123\"");});
        assertThrows(Exception.class,                 () -> {evaluate("=1 + \"asd\"");});

        //// DTypeBoolean (left) + DType<?> (right)
        assertEquals(evaluate("=True + 1.5"),    new DTypeDouble(2.5));
        assertEquals(evaluate("=False + 10.5"),  new DTypeDouble(10.5));
        assertEquals(evaluate("=True + 8"),      new DTypeInteger(9));
        assertEquals(evaluate("=False + 8"),     new DTypeInteger(8));
        assertEquals(evaluate("=True + True"),   new DTypeBoolean(true));
        assertEquals(evaluate("=True + False"),  new DTypeBoolean(true));
        assertEquals(evaluate("=False + True"),  new DTypeBoolean(true));
        assertEquals(evaluate("=False + False"), new DTypeBoolean(false));
        assertThrows(Exception.class,                     () -> {evaluate("=True + \"test\"");});
        assertThrows(Exception.class,                     () -> {evaluate("=False + \"test\"");});

        //// DTypeString (left) + DType<?> (right)
        assertEquals(evaluate("=\"string 1\" + \"string 2\""), new DTypeString("string 1string 2"));
        assertEquals(evaluate("=\"string 1\" + \"\""),         new DTypeString("string 1"));
        assertThrows(Exception.class,                                   () -> {evaluate("=\"string 1\" + 1.5");});
        assertThrows(Exception.class,                                   () -> {evaluate("=\"string 1\" + 10");});
        assertThrows(Exception.class,                                   () -> {evaluate("=\"string 1\" + True");});
        assertThrows(Exception.class,                                   () -> {evaluate("=\"string 1\" + False");});
    }

    @Test
    public void testSubtraction() throws Exception {
        // Subtraction (p - q)
        //// DTypeDouble (left) - DType<?> (right)
        assertEquals(evaluate("=1.5 - 2.5"),   new DTypeDouble(-1));
        assertEquals(evaluate("=1.5 - 2"),     new DTypeDouble(-0.5));
        assertEquals(evaluate("=1.5 - True"),  new DTypeDouble(0.5));
        assertEquals(evaluate("=1.5 - False"), new DTypeDouble(1.5));
        assertThrows(Exception.class,                   () -> {evaluate("=1.5 - \"123\"");});
        assertThrows(Exception.class,                   () -> {evaluate("=1.5 - \"asd\"");});

        //// DTypeInteger (left) - DType<?> (right)
        assertEquals(evaluate("=5 - 2.5"),   new DTypeDouble(2.5));
        assertEquals(evaluate("=5 - 2"),     new DTypeInteger(3));
        assertEquals(evaluate("=5 - True"),  new DTypeInteger(4));
        assertEquals(evaluate("=5 - False"), new DTypeInteger(5));
        assertThrows(Exception.class,                 () -> {evaluate("=1 - \"123\"");});
        assertThrows(Exception.class,                 () -> {evaluate("=1 - \"asd\"");});

        //// DTypeBoolean (left) - DType<?> (right)
        assertEquals(evaluate("=True - 1.5"),   new DTypeDouble(-0.5));
        assertEquals(evaluate("=False - 10.5"), new DTypeDouble(-10.5));
        assertEquals(evaluate("=True - 8"),     new DTypeInteger(-7));
        assertEquals(evaluate("=False - 8"),    new DTypeInteger(-8));
        assertThrows(Exception.class,                    () -> {evaluate("=True - True");});
        assertThrows(Exception.class,                    () -> {evaluate("=True - False");});
        assertThrows(Exception.class,                    () -> {evaluate("=False - True");});
        assertThrows(Exception.class,                    () -> {evaluate("=False - False");});
        assertThrows(Exception.class,                    () -> {evaluate("=True - \"123\"");});
        assertThrows(Exception.class,                    () -> {evaluate("=False - \"asd\"");});

        //// DTypeString (left) - DType<?> (right)
        assertThrows(Exception.class, () -> {evaluate("=\"string 1\" - \"string 2\"");});
        assertThrows(Exception.class, () -> {evaluate("=\"string 1\" - \"\"");});
        assertThrows(Exception.class, () -> {evaluate("=\"string 1\" - 1.5");});
        assertThrows(Exception.class, () -> {evaluate("=\"string 1\" - 10");});
        assertThrows(Exception.class, () -> {evaluate("=\"string 1\" - True");});
        assertThrows(Exception.class, () -> {evaluate("=\"string 1\" - False");});
    }

    @Test
    public void testMultiplication() throws Exception {
        // Multiplication (p * q)
        //// DTypeDouble (left) * DType<?> (right)
        assertEquals(evaluate("=1.5 * 2.5"),   new DTypeDouble(3.75));
        assertEquals(evaluate("=1.5 * 2"),     new DTypeDouble(3));
        assertEquals(evaluate("=1.5 * True"),  new DTypeDouble(1.5));
        assertEquals(evaluate("=1.5 * False"), new DTypeDouble(0));
        assertThrows(Exception.class,                   () -> {evaluate("=1.5 * \"123\"");});
        assertThrows(Exception.class,                   () -> {evaluate("=1.5 * \"asd\"");});

        //// DTypeInteger (left) * DType<?> (right)
        assertEquals(evaluate("=5 * 2.5"),      new DTypeDouble(12.5));
        assertEquals(evaluate("=5 * 2"),        new DTypeInteger(10));
        assertEquals(evaluate("=5 * True"),     new DTypeInteger(5));
        assertEquals(evaluate("=5 * False"),    new DTypeInteger(0));
        assertEquals(evaluate("=5 * \"test\""), new DTypeString("testtesttesttesttest"));
        assertThrows(Exception.class,                    () -> {evaluate("=-5 * \"test\"");});

        //// DTypeBoolean (left) * DType<?> (right)
        assertEquals(evaluate("=True * 1.5"),       new DTypeDouble(1.5));
        assertEquals(evaluate("=False * 10.5"),     new DTypeDouble(0));
        assertEquals(evaluate("=True * 8"),         new DTypeInteger(8));
        assertEquals(evaluate("=False * 8"),        new DTypeInteger(0));
        assertEquals(evaluate("=False * 8"),        new DTypeInteger(0));
        assertEquals(evaluate("=True * True"),      new DTypeBoolean(true));
        assertEquals(evaluate("=True * False"),     new DTypeBoolean(false));
        assertEquals(evaluate("=False * True"),     new DTypeBoolean(false));
        assertEquals(evaluate("=False * False"),    new DTypeBoolean(false));
        assertEquals(evaluate("=True * \"test\""),  new DTypeString("test"));
        assertEquals(evaluate("=False * \"test\""), new DTypeString(""));

        //// DTypeString (left) * DType<?> (right)
        assertEquals(evaluate("=\"test\" * 5"), new DTypeString("testtesttesttesttest"));
        assertEquals(evaluate("=\"test\" * True"), new DTypeString("test"));
        assertEquals(evaluate("=\"test\" * False"), new DTypeString(""));
        assertThrows(Exception.class, () -> {evaluate("=\"test\" * 1.5");});
        assertThrows(Exception.class, () -> {evaluate("=\"test\" * -5");});
        assertThrows(Exception.class, () -> {evaluate("=\"test\" * \"test 2\"");});
    }

    @Test
    public void testDivision() throws Exception {
        // Division (p / q)
        //// DTypeDouble (left) / DType<?> (right)
        assertEquals(evaluate("=10.50 / 2.5"),   new DTypeDouble(4.2));
        assertEquals(evaluate("=10.50 / 2"),     new DTypeDouble(5.25));
        assertEquals(evaluate("=10.50 / True"),  new DTypeDouble(10.50));
        assertThrows(Exception.class,                     () -> {evaluate("=1.5 / False");});
        assertThrows(Exception.class,                     () -> {evaluate("=1.5 / \"test\"");});

        //// DTypeInteger (left) / DType<?> (right)
        assertEquals(evaluate("=5 / 2.5"),      new DTypeDouble(2));
        assertEquals(evaluate("=5 / 2"),        new DTypeInteger(2.5));
        assertEquals(evaluate("=5 / True"),     new DTypeInteger(5));
        assertThrows(Exception.class,                    () -> {evaluate("=5 / False");});
        assertThrows(Exception.class,                    () -> {evaluate("=-5 / \"test\"");});

        //// DTypeBoolean (left) / DType<?> (right)
        assertEquals(evaluate("=True / 2.5"),    new DTypeDouble(0.4));
        assertEquals(evaluate("=False / 2.5"),   new DTypeDouble(0));
        assertEquals(evaluate("=True / 2"),      new DTypeInteger(0));
        assertEquals(evaluate("=False / 2"),     new DTypeInteger(0));
        assertThrows(Exception.class, () -> {evaluate("=True / True");});
        assertThrows(Exception.class, () -> {evaluate("=True / False");});
        assertThrows(Exception.class, () -> {evaluate("=False / True");});
        assertThrows(Exception.class, () -> {evaluate("=False / False");});
        assertThrows(Exception.class, () -> {evaluate("=True / \"test\"");});
        assertThrows(Exception.class, () -> {evaluate("=False / \"test\"");});

        //// DTypeString (left) / DType<?> (right)
        assertThrows(Exception.class, () -> {evaluate("=\"test\" / 2.5");});
        assertThrows(Exception.class, () -> {evaluate("=\"test\" / 2");});
        assertThrows(Exception.class, () -> {evaluate("=\"test\" / True");});
        assertThrows(Exception.class, () -> {evaluate("=\"test\" / False");});
        assertThrows(Exception.class, () -> {evaluate("=\"test\" / \"test 2\"");});
    }

    @Test
    public void testExponentiation() throws Exception {
        // Division (p ^ q)
        //// DTypeDouble (left) ^ DType<?> (right)
        assertEquals(evaluate("=10.50 ^ 2.5"),   new DTypeDouble(357.2508309997333));
        assertEquals(evaluate("=10.50 ^ 2"),     new DTypeDouble(110.25));
        assertEquals(evaluate("=10.50 ^ True"),  new DTypeDouble(10.50));
        assertEquals(evaluate("=10.50 ^ False"), new DTypeDouble(1));
        assertThrows(Exception.class,                     () -> {evaluate("=10.50 ^ \"test\"");});

        //// DTypeInteger (left) ^ DType<?> (right)
        assertEquals(evaluate("=5 ^ 2.5"),   new DTypeDouble(55.90169943749474));
        assertEquals(evaluate("=5 ^ 2"),     new DTypeInteger(25));
        assertEquals(evaluate("=5 ^ True"),  new DTypeInteger(5));
        assertEquals(evaluate("=5 ^ False"), new DTypeInteger(1));
        assertThrows(Exception.class,                 () -> {evaluate("=5 ^ \"test\"");});

        //// DTypeBoolean (left) ^ DType<?> (right)
        assertEquals(evaluate("=True ^ 2.5"),    new DTypeBoolean(true));
        assertEquals(evaluate("=False ^ 2.5"),   new DTypeBoolean(false));
        assertEquals(evaluate("=True ^ 2"),      new DTypeBoolean(true));
        assertEquals(evaluate("=False ^ 2"),     new DTypeBoolean(false));
        assertEquals(evaluate("=True ^ True"),   new DTypeBoolean(true));
        assertEquals(evaluate("=True ^ False"),  new DTypeBoolean(true));
        assertEquals(evaluate("=False ^ True"),  new DTypeBoolean(false));
        assertThrows(Exception.class,                     () -> {evaluate("=False ^ False");});
        assertThrows(Exception.class,                     () -> {evaluate("=True ^ \"test\"");});
        assertThrows(Exception.class,                     () -> {evaluate("=False ^ \"test\"");});

        //// DTypeString (left) ^ DType<?> (right)
        assertThrows(Exception.class, () -> {evaluate("=\"test\" ^ 2.5");});
        assertThrows(Exception.class, () -> {evaluate("=\"test\" ^ 2");});
        assertThrows(Exception.class, () -> {evaluate("=\"test\" ^ True");});
        assertThrows(Exception.class, () -> {evaluate("=\"test\" ^ False");});
        assertThrows(Exception.class, () -> {evaluate("=\"test\" ^ \"test2\"");});
    }

    @Test
    public void testModulo() throws Exception {
        // Division (p % q)
        //// DTypeDouble (left) % DType<?> (right)
        assertThrows(Exception.class, () -> {evaluate("=10.50 % 2.5");});
        assertThrows(Exception.class, () -> {evaluate("=10.50 % 2");});
        assertThrows(Exception.class, () -> {evaluate("=10.50 % True");});
        assertThrows(Exception.class, () -> {evaluate("=10.50 % False");});
        assertThrows(Exception.class, () -> {evaluate("=10.50 % \"test\"");});

        //// DTypeInteger (left) % DType<?> (right)
        assertEquals(evaluate("=5 % 2"),     new DTypeInteger(1));
        assertEquals(evaluate("=5 % True"),  new DTypeInteger(0));
        assertThrows(Exception.class,                 () -> {evaluate("=5 % 2.5");});
        assertThrows(Exception.class,                 () -> {evaluate("=5 % False");});
        assertThrows(Exception.class,                 () -> {evaluate("=5 ^ \"test\"");});

        //// DTypeBoolean (left) % DType<?> (right)
        assertEquals(evaluate("=True % 2"),     new DTypeBoolean(true));
        assertEquals(evaluate("=False % 2"),    new DTypeBoolean(false));
        assertEquals(evaluate("=True % True"),  new DTypeBoolean(false));
        assertEquals(evaluate("=False % True"), new DTypeBoolean(false));
        assertThrows(Exception.class,                    () -> {evaluate("=True % 2.5");});
        assertThrows(Exception.class,                    () -> {evaluate("=False % 2.5");});
        assertThrows(Exception.class,                    () -> {evaluate("=True % False");});
        assertThrows(Exception.class,                    () -> {evaluate("=False % False");});
        assertThrows(Exception.class,                    () -> {evaluate("=True % \"test\"");});
        assertThrows(Exception.class,                    () -> {evaluate("=False % \"test\"");});

        //// DTypeString (left) % DType<?> (right)
        assertThrows(Exception.class, () -> {evaluate("=\"test\" % 2.5");});
        assertThrows(Exception.class, () -> {evaluate("=\"test\" % 2");});
        assertThrows(Exception.class, () -> {evaluate("=\"test\" % True");});
        assertThrows(Exception.class, () -> {evaluate("=\"test\" % False");});
        assertThrows(Exception.class, () -> {evaluate("=\"test\" % \"test2\"");});
    }

    @Test
    public void testLT() throws Exception {
        // LessThan (p < q)
        //// DTypeDouble (left) < DType<?> (right)
        assertEquals(evaluate("=10.50 < 2.5"), new DTypeBoolean(false));
        assertEquals(evaluate("=2.5 < 10"),    new DTypeBoolean(true));
        assertEquals(evaluate("=1.5 < True"),  new DTypeBoolean(false));
        assertEquals(evaluate("=0.2 < False"), new DTypeBoolean(false));
        assertThrows(Exception.class,                   () -> {evaluate("=1.5 < \"test\"");});

        //// DTypeInteger (left) < DType<?> (right)
        assertEquals(evaluate("=5 < 2.5"),      new DTypeBoolean(false));
        assertEquals(evaluate("=5 < 10"),       new DTypeBoolean(true));
        assertEquals(evaluate("=5 < True"),     new DTypeBoolean(false));
        assertEquals(evaluate("=0 < True"),     new DTypeBoolean(true));
        assertThrows(Exception.class,                    () -> {evaluate("=5 < \"test\"");});

        //// DTypeBoolean (left) < DType<?> (right)
        assertEquals(evaluate("=True < 2.5"),    new DTypeBoolean(true));
        assertEquals(evaluate("=False < -5"),    new DTypeBoolean(false));
        assertEquals(evaluate("=True < 2"),      new DTypeBoolean(true));
        assertEquals(evaluate("=False < 2"),     new DTypeBoolean(true));
        assertEquals(evaluate("=True < True"),   new DTypeBoolean(false));
        assertEquals(evaluate("=True < False"),  new DTypeBoolean(false));
        assertEquals(evaluate("=False < True"),  new DTypeBoolean(true));
        assertEquals(evaluate("=False < False"), new DTypeBoolean(false));
        assertThrows(Exception.class,                      () -> {evaluate("=True < \"test\"");});
        assertThrows(Exception.class,                      () -> {evaluate("=False < \"test\"");});

        //// DTypeString (left) < DType<?> (right)
        assertEquals(evaluate("=\"abc\" < \"dddd\""),  new DTypeBoolean(true));
        assertEquals(evaluate("=\"dddd\" < \"aaaa\""), new DTypeBoolean(false));
        assertThrows(Exception.class,                           () -> {evaluate("=\"test\" < 2.5");});
        assertThrows(Exception.class,                           () -> {evaluate("=\"test\" < 2");});
        assertThrows(Exception.class,                           () -> {evaluate("=\"test\" < True");});
        assertThrows(Exception.class,                           () -> {evaluate("=\"test\" < False");});
    }

    @Test
    public void testGT() throws Exception {
        // GreaterThan (p > q)
        //// DTypeDouble (left) > DType<?> (right)
        assertEquals(evaluate("=10.50 > 2.5"), new DTypeBoolean(true));
        assertEquals(evaluate("=2.5 > 10"),    new DTypeBoolean(false));
        assertEquals(evaluate("=1.5 > True"),  new DTypeBoolean(true));
        assertEquals(evaluate("=0.2 > False"), new DTypeBoolean(true));
        assertThrows(Exception.class,                   () -> {evaluate("=1.5 < \"test\"");});

        //// DTypeInteger (left) > DType<?> (right)
        assertEquals(evaluate("=5 > 2.5"),      new DTypeBoolean(true));
        assertEquals(evaluate("=5 > 10"),       new DTypeBoolean(false));
        assertEquals(evaluate("=5 > True"),     new DTypeBoolean(true));
        assertEquals(evaluate("=0 > True"),     new DTypeBoolean(false));
        assertThrows(Exception.class,                    () -> {evaluate("=5 < \"test\"");});

        //// DTypeBoolean (left) > DType<?> (right)
        assertEquals(evaluate("=True > 2.5"),    new DTypeBoolean(false));
        assertEquals(evaluate("=False > -5"),    new DTypeBoolean(true));
        assertEquals(evaluate("=True > 2"),      new DTypeBoolean(false));
        assertEquals(evaluate("=False > 2"),     new DTypeBoolean(false));
        assertEquals(evaluate("=True > True"),   new DTypeBoolean(false));
        assertEquals(evaluate("=True > False"),  new DTypeBoolean(true));
        assertEquals(evaluate("=False > True"),  new DTypeBoolean(false));
        assertEquals(evaluate("=False > False"), new DTypeBoolean(false));
        assertThrows(Exception.class,                      () -> {evaluate("=True > \"test\"");});
        assertThrows(Exception.class,                      () -> {evaluate("=False > \"test\"");});

        //// DTypeString (left) > DType<?> (right)
        assertEquals(evaluate("=\"abc\" > \"dddd\""),  new DTypeBoolean(false));
        assertEquals(evaluate("=\"dddd\" > \"aaaa\""), new DTypeBoolean(true));
        assertThrows(Exception.class,                           () -> {evaluate("=\"test\" > 2.5");});
        assertThrows(Exception.class,                           () -> {evaluate("=\"test\" > 2");});
        assertThrows(Exception.class,                           () -> {evaluate("=\"test\" > True");});
        assertThrows(Exception.class,                           () -> {evaluate("=\"test\" > False");});
    }

    @Test
    public void testFunctions() throws Exception {
        // ConstE & ConstPI
        assertEquals(evaluate("=e()"), new DTypeDouble(Math.E));
        assertEquals(evaluate("=pi()"), new DTypeDouble(Math.PI));

        // Abs
        assertEquals(evaluate("=abs(16.0)"), new DTypeDouble(16));
        assertEquals(evaluate("=abs(16)"), new DTypeInteger(16));
        assertEquals(evaluate("=abs(True)"), new DTypeBoolean(true));
        assertEquals(evaluate("=abs(False)"), new DTypeBoolean(false));

        // Sqrt
        assertEquals(evaluate("=sqrt(16.0)"), new DTypeDouble(4));
        assertEquals(evaluate("=sqrt(16)"), new DTypeInteger(4));
        assertEquals(evaluate("=sqrt(True)"), new DTypeBoolean(true));
        assertEquals(evaluate("=sqrt(False)"), new DTypeBoolean(false));

        // Pow
        assertEquals(evaluate("=pow(2.0, 3.0)"), new DTypeDouble(8));
        assertEquals(evaluate("=pow(2.0, 3)"), new DTypeDouble(8));
        assertEquals(evaluate("=pow(2.0, True)"), new DTypeDouble(2));
        assertEquals(evaluate("=pow(2.0, False)"), new DTypeDouble(1));

        assertEquals(evaluate("=pow(2, 3.0)"), new DTypeDouble(8));
        assertEquals(evaluate("=pow(2, 3)"), new DTypeInteger(8));
        assertEquals(evaluate("=pow(2, True)"), new DTypeInteger(2));
        assertEquals(evaluate("=pow(2, False)"), new DTypeInteger(1));

        assertEquals(evaluate("=pow(True, 3.0)"), new DTypeBoolean(true));
        assertEquals(evaluate("=pow(False, 3.0)"), new DTypeBoolean(false));
        assertEquals(evaluate("=pow(True, 2)"), new DTypeBoolean(true));
        assertEquals(evaluate("=pow(False, 2)"), new DTypeBoolean(false));
        assertEquals(evaluate("=pow(True, True)"), new DTypeBoolean(true));
        assertThrows(Exception.class,                     () -> {evaluate("=pow(False, False)");});
        assertThrows(Exception.class,                     () -> {evaluate("=pow(\"test\", 2)");});

        // Min & Max
        assertEquals(evaluate("=min(2.0, 3.0)"),     new DTypeDouble(2));
        assertEquals(evaluate("=min(2, 3)"), new DTypeInteger(2));
        assertEquals(evaluate("=min(True, True)"),     new DTypeBoolean(true));
        assertEquals(evaluate("=min(True, False)"),     new DTypeBoolean(false));
        assertEquals(evaluate("=min(False, True)"),     new DTypeBoolean(false));
        assertEquals(evaluate("=min(False, False)"),     new DTypeBoolean(false));;
        assertThrows(Exception.class,                     () -> {evaluate("=min(2.0, 3)");});
        assertThrows(Exception.class,                     () -> {evaluate("=min(True, 3)");});
        assertThrows(Exception.class,                     () -> {evaluate("=min(2.0, False)");});

        assertEquals(evaluate("=max(2.0, 3.0)"),     new DTypeDouble(3));
        assertEquals(evaluate("=max(2, 3)"), new DTypeInteger(3));
        assertEquals(evaluate("=max(True, True)"),     new DTypeBoolean(true));
        assertEquals(evaluate("=max(True, False)"),     new DTypeBoolean(true));
        assertEquals(evaluate("=max(False, True)"),     new DTypeBoolean(true));
        assertEquals(evaluate("=max(False, False)"),     new DTypeBoolean(false));;
        assertThrows(Exception.class,                     () -> {evaluate("=max(2.0, 3)");});
        assertThrows(Exception.class,                     () -> {evaluate("=max(True, 3)");});
        assertThrows(Exception.class,                     () -> {evaluate("=max(2.0, False)");});

        // GCD & LCM
        assertEquals(evaluate("=gcd(1, 2)"),     new DTypeInteger(1));
        assertEquals(evaluate("=lcm(2, 10)"),    new DTypeInteger(10));
        assertEquals(evaluate("=lcm(-13, 3)"),   new DTypeInteger(39));
        assertThrows(Exception.class,                     () -> {evaluate("=lcm(10.5, 2)");});
        assertThrows(Exception.class,                     () -> {evaluate("=lcm(\"test\", \"test\")");});
        assertThrows(Exception.class,                     () -> {evaluate("=lcm(2, False)");});
        assertThrows(Exception.class,                     () -> {evaluate("=lcm(2, True)");});


        // IFEQ
        assertEquals(evaluate("=ifeq(3, 3.0)"),             new DTypeBoolean(false));
        assertEquals(evaluate("=ifeq(3, 3)"),               new DTypeBoolean(true));
        assertEquals(evaluate("=ifeq(\"test\", \"test\")"), new DTypeBoolean(true));
        assertEquals(evaluate("=ifeq(\"test\", 3)"),        new DTypeBoolean(false));
        assertEquals(evaluate("=ifeq(False, 0)"),           new DTypeBoolean(false));

        // Sum
        assertEquals(evaluate("=sum(1, 2, 3, 4)"),   new DTypeInteger(10));
        assertEquals(evaluate("=sum(1, 2, 3.0, 4)"), new DTypeDouble(10));
        assertThrows(Exception.class,                          () -> {evaluate("=sum(1, \"test\", 3)");});

        // Average
        assertEquals(evaluate("=average(1, 2, 3)"), new DTypeDouble(2.0));
        assertEquals(evaluate("=average(1)"),       new DTypeDouble(1.0));
        assertThrows(Exception.class,                         () -> {evaluate("=sum(1, \"test\", 3)");});
    }

    @Test
    public void testGeneral() throws Exception {
        // Tests that contain cell references are exempt.

        assertEquals(evaluate("=sqrt(abs(-16))"),                            new DTypeInteger(4));
        assertEquals(evaluate("=pow(max(1++1, ---1000), 3)"),                new DTypeInteger(8));
        assertEquals(evaluate("=5 + e() - pi()^2"),                          new DTypeDouble(-2.1513225726303133));
        assertEquals(evaluate("=ifeq(pow(5, 2), 25)"),                       new DTypeBoolean(true));
        assertEquals(evaluate("=ifeq(2 < 10, 1.0)"),                         new DTypeBoolean(false));
        assertEquals(evaluate("=ifeq(\"w\" * 3 + \"123\", \"www123\") * 5"), new DTypeInteger(5));
        assertEquals(evaluate("=sum(1, 2, 3, 4)"),                           new DTypeInteger(10));
    }
}
