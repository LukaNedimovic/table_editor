package com.lnedimovic.table_editor.expression;

import com.lnedimovic.table_editor.dtype.DTypeFactory;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeString;
import com.lnedimovic.table_editor.expression.operation.Operation;
import com.lnedimovic.table_editor.expression.function.Function;

import com.lnedimovic.table_editor.expression.operation.OperationSet;
import com.lnedimovic.table_editor.expression.token.Token;
import com.lnedimovic.table_editor.expression.token.TokenType;

import java.util.ArrayList;

/**
 * <code>Tokenizer</code> used for tokenizing the given expression.
 * It could've been omitted, however, due to scalability reasons, it was explicitly implemented, so the {@link Parser} can focus only on the parsing part.
 * <p>
 * As of now, tokenizer supports token types that can be found in {@link TokenType}.
 */
public class Tokenizer {
    /**
     * Set of operations used by the instance of tokenizer.
     * It is possible to create different versions of tokenizers with totally different operation sets - it is totally customizable.
     */
    private OperationSet operations;

    /**
     * Set of functions used by the instance of tokenizer.
     * It is possible to create different versions of tokenizers with totally different function sets - it is totally customizable.
     */
    private Function[]  functions  = new Function[]{};

    /**
     * Current position inside the expression String. Created as a class-level variable, to omit passing between functions.
     */
    private int pos;

    /**
     * Creates an instance of <code>Tokenizer</code>, provided set of operations and functions.
     * @param operations Set of operations to be used during parsing.
     * @param functions  Set of functions to be used during parsing.
     */
    public Tokenizer(OperationSet operations, Function[] functions) {
        this.operations = operations;
        this.functions = functions;
    }
    /*
    /**
     * Creates an instance of <code>Tokenizer</code>, provided set of operations.
     * It is possible to create a tokenizer without functions.
     *
     * @param operations Set of operations to be used during tokenization.
     *
    public Tokenizer(OperationSet[] operations) {
        this.operations = operations;
    }*/

    /**
     * Creates an instance of <code>Tokenizer</code>, provided set of functions.
     * It is possible to create a tokenizer without operations.
     *
     * @param functions Set of functions to be used during tokenization.
     */
    public Tokenizer(Function[] functions) {
        this.functions = functions;
    }

    /**
     * Tokenizes given expression, if it is a formula.
     * For the sake of convenience, a formula must start with a "=" symbol. This can easily be modified to anything else.
     * <p>
     * Tokenization implemented is no specific algorithm, yet just following rules of thought grammar.
     *
     * @param  expression Expression to parse
     * @return            List of tokens found inside given expression.
     * @throws Exception  In case of invalid expression.
     */
    public ArrayList<Token> tokenize(String expression) throws Exception {
        /// For the sake of convenience, a formula must start with a "=" symbol.
        if (!expression.startsWith("=")) {
            throw new Exception("A formula must start with a '='");
        }

        // Preprocess expression (i.e. remove whitespaces)
        expression = preprocess(expression);
        pos = 1;

        ArrayList<Token>     tokens          = new ArrayList<>(); // To be returned list of tokens
        ArrayList<Character> characterBuffer = new ArrayList<>(); // Buffers for characters
        ArrayList<Character> digitBuffer     = new ArrayList<>(); // Separate digit buffer - for the sake of convenience

        // Parse for each character in the expression
        while (pos < expression.length()) {
            char curr = expression.charAt(pos); // Current character

            // If current token represents an operation
            if (operations.find(Character.toString(curr)) != null) {
                // Case 1 - a definitive unary operation. Operation is unary if there are no tokens preceding it.
                if (tokens.isEmpty()) {
                    // Try finding a unary operation with given symbol
                    Operation foundOperation = operations.find(Character.toString(curr), true);
                    if (foundOperation != null) {
                        // Create now operation token and add it to the list of tokens
                        tokens.add(new Token(new DTypeString(Character.toString(curr)), foundOperation, TokenType.OPERATION));
                    }
                    else {
                        throw new Exception("Impossible to parse (undefined unary operation).");
                    }
                }

                // Case 2 - possible unary / binary operation
                else {
                    Token lastToken = tokens.get(tokens.size() - 1); // tokens.size() > 0, otherwise would fall into previous condition

                    // Unary operation can come after a "(" (e.g. (-5 + ...)), a "," (e.g. pow(..., -5)), or another operation (e.g. --5)
                    if (lastToken.equals("(") || expression.charAt(pos - 1) == ',' || lastToken.getType() == TokenType.OPERATION) {
                        // Try finding a unary operation with given symbol
                        Operation foundOperation = operations.find(Character.toString(curr), true);
                        if (foundOperation != null) {
                            // Create a new operation token and add it to the list of tokens
                            tokens.add(new Token(new DTypeString(Character.toString(curr)), foundOperation, TokenType.OPERATION));
                        }
                        else {
                            throw new Exception("Impossible to parse (undefined unary operation).");
                        }
                    }

                    // If not unary, binary operation is encountered
                    else  {
                        // Try finding a binary operation with given symbol
                        Operation foundOperation = operations.find(Character.toString(curr), false);
                        if (foundOperation != null) {
                            // Create a new operation token and add it to the list of tokens
                            tokens.add(new Token(new DTypeString(Character.toString(curr)), foundOperation, TokenType.OPERATION));
                        }
                        else {
                            throw new Exception("Impossible to parse (undefined binary operation).");
                        }
                    }
                }

                pos++;
            }
            // Cell references, function identifiers and "True / False" start with letters
            else if (Character.isLetter(curr)) {
                // Cell references are written in uppercase
                if (Character.isUpperCase(curr)) {
                    // Make sure there is nothing unprocessed
                    checkBuffers(characterBuffer, digitBuffer);

                    // Check if "True / False"
                    String possibleBoolean = checkForBooleanValue(expression);
                    if (possibleBoolean != null) {
                        if (possibleBoolean.equals("True")) {
                            tokens.add(new Token(new DTypeString(possibleBoolean), operations, TokenType.TRUE));
                        }
                        else {
                            tokens.add(new Token(new DTypeString(possibleBoolean), operations, TokenType.FALSE));
                        }
                    }
                    else {
                        System.out.println(expression.substring(pos, pos + 4));
                        // Cell references include singular cells (e.g. B2) and cell ranges (e.g. C2:D3)

                        // Parse singular cell of expression (left part)
                        String leftCell = parseCell(expression, characterBuffer, digitBuffer);

                        String rightCell;
                        // In case cell range is referenced
                        if (pos < expression.length() && expression.charAt(pos) == ':') {
                            if (pos + 2 >= expression.length()) {
                                throw new Exception("Invalid cell reference (impossible to parse another cell after ':')");
                            }
                            pos++; // Move from ":" to start of another cell reference

                            rightCell = parseCell(expression, characterBuffer, digitBuffer);
                        }

                        // Single cell references are, for the sake of convenience, turned into ranges
                        // Naturally, a single cell range contains only itself as start and as an end (e.g. A3:A3)
                        else {
                            rightCell = leftCell;
                        }

                        // Test the cell range for validity before continuing.
                        if (!validCellRange(leftCell, rightCell)) {
                            throw new Exception("Invalid cell range provided.");
                        }

                        // Handling two cases in uniform way:
                        // (1) Single-cell reference, e.g. A2:A2
                        // (2) Cell-range reference,  e.g. A2:B4
                        String completeReference = leftCell + ":" + rightCell;

                        // Create a new cell reference token and add it to the list of tokens
                        tokens.add(new Token(new DTypeString(completeReference), "", TokenType.REFERENCE));
                    }
                }

                // Named functions are written in lowercase
                else {
                    // Parse function identifier and check for the existence of such in the list of available functions
                    String   functionName  = parseFunction(expression, characterBuffer);
                    Function foundFunction = findFunction(functionName);

                    // If a function is found ...
                    if (foundFunction != null) {
                        // Create a new function token and add it to the list of tokens
                        tokens.add(new Token(new DTypeString(functionName), foundFunction, TokenType.FUNCTION));
                    }
                    else {
                        tokens.add(new Token(new DTypeString(functionName), null, TokenType.STRING));
                    }
                }
            }

            // Only numerical constants start with a digit
            else if (Character.isDigit(curr)) {
                // Make sure there is nothing unprocessed
                checkBuffers(characterBuffer, digitBuffer);

                // Find the value of numerical constant
                String numericalConstant = parseNumericalConstant(expression, digitBuffer);

                // Create a new numerical constant token and add it to the list of tokens
                tokens.add(new Token(DTypeFactory.create(numericalConstant) , null, TokenType.NUMERICAL_CONSTANT));
            }

            // Everything else is directly stored
            else if (curr == '(') {
                tokens.add(new Token(new DTypeString("("), "", TokenType.PARENTHESIS));
                pos++;
            }

            else if (curr == ')') {
                tokens.add(new Token(new DTypeString(")"), "", TokenType.PARENTHESIS));
                pos++;
            }

            else if (curr == ',') {
                tokens.add(new Token(new DTypeString(","), "", TokenType.COMMA));
                pos++;
            }

            else if (curr == '"') {
                tokens.add(new Token(new DTypeString("\""), "", TokenType.QUOTATION_MARK));
                pos++;
            }

            else {
                throw new Exception("Can't parse character: " + curr);
            }
        }

        return tokens;
    }

    /**
     * Preprocesses given expression.
     * @param expression Expression to preprocess.
     * @return           Preprocessed expression.
     */
    public String preprocess(String expression) {
        expression = expression.replaceAll(" ", "");
        expression = expression.replaceAll("\n", "");

        return expression;
    }

    private String checkForBooleanValue(String expression) {
        if (pos < expression.length() - 4 + 1 && expression.startsWith("True", pos)) {
            pos += 4;
            return "True";
        }
        if (pos < expression.length() - 5 + 1 && expression.startsWith("False", pos)) {
            pos += 5;
            return "False";
        }

        return null;
    }

    /**
     * Parses the part of the expression that is a potential cell reference.
     * Cell reference can be in two formats: single-cell reference (e.g. B2), or cell range reference (e.g. B2:D4).
     * For the sake of convenience, single-cell references are transformed into cell range references (where the start and the end cell are equal, e.g. B2:B2).
     * <p>
     *
     * TODO Support for cells whose name has more than a single character.
     * @param expression      Complete expression currently being tokenized.
     * @param characterBuffer Character buffer - used for column processing.
     * @param digitBuffer     Digit buffer - used for row processing.
     * @return                String value of referenced cell, in cell range format.
     * @throws Exception      In case of invalid expression.
     */
    public String parseCell(String expression, ArrayList<Character> characterBuffer, ArrayList<Character> digitBuffer) throws Exception {
        // TODO: Support for cells whose name has more than a single character.
        characterBuffer.add(expression.charAt(pos)); // Add current character
        pos++;

        // After column name, collect all the digits (representing a row)
        while (pos < expression.length() && Character.isDigit(expression.charAt(pos))) {
            digitBuffer.add(expression.charAt(pos));
            pos++;
        }

        if (digitBuffer.isEmpty()) {
            throw new Exception("No row reference." + digitBuffer + " " + pos);
        }

        // Setup left cell and clear the buffers
        String cellColumn = bufferToString(characterBuffer);
        String cellRow    = bufferToString(digitBuffer);

        characterBuffer.clear();
        digitBuffer.clear();

        if (cellColumn.compareTo("A") < 0 || cellColumn.compareTo("Z") > 0 || Integer.parseInt(cellRow) <= 0) {
            throw new Exception("Invalid row / column references (must be greater than 0).");
        }

        // Cell is in format of a capital letter, followed by a row, e.g. B12
        return cellColumn + cellRow;
    }

    /**
     * Parses the part of the expression that is a potential numerical constant (e.g. 2.5).
     * Returns a decimal representation of a given number (e.g. 1 -> 1.0, 2.5 -> 2.5).
     * @param expression  Complete expression currently being tokenized.
     * @param digitBuffer Digit buffer - used in value processing.
     * @return            String representation of given number in decimal format.
     * @throws Exception  In case of invalid expression.
     */
    public String parseNumericalConstant(String expression, ArrayList<Character> digitBuffer) throws Exception {
        // Collect all the digits
        while (pos < expression.length() && Character.isDigit(expression.charAt(pos))) {
            digitBuffer.add(expression.charAt(pos));
            pos++;
        }

        // Convert the buffer into a concrete value and clear the buffer
        String integerPart = bufferToString(digitBuffer);
        digitBuffer.clear();

        // In case there is a decimal point
        if (pos < expression.length() && expression.charAt(pos) == '.') {
            pos++; // Skip the ".", then collect all the digits
            while (pos < expression.length() && Character.isDigit(expression.charAt(pos))) {
                digitBuffer.add(expression.charAt(pos));
                pos++;
            }

            if (digitBuffer.isEmpty()) {
                throw new Exception("Invalid decimal part.");
            }

            // Again, convert the buffer and clear it
            String decimalPart = bufferToString(digitBuffer);
            digitBuffer.clear();

            // Return complete representation of decimal number
            return integerPart + "." + decimalPart;
        }
        else {
            // Return complete representation of integer number
            return integerPart;
        }
    }

    /**
     * Converts given buffer into a String.
     * Used while tokenizing the expression and needing to create a complete token, or a part of the token, out of certain amount of characters.
     * @param buffer Buffer to convert into a String.
     * @return       The String representation of content of buffer.
     */
    public String bufferToString(ArrayList<Character> buffer) {
        String repr = "";
        for (Character c : buffer) {
            repr += c;
        }

        return repr;
    }

    /**
     * Checks whether given buffers (<code>characterBuffer</code>, <code>digitBuffer</code>) have any content inside.
     * @param characterBuffer Character buffer.
     * @param digitBuffer     Digit buffer.
     * @throws Exception      If any of buffers is not empty.
     */
    public void checkBuffers(ArrayList<Character> characterBuffer, ArrayList<Character> digitBuffer) throws Exception {
        if (!characterBuffer.isEmpty()) {
            throw new Exception("Character buffer is not empty.");
        }
        if (!digitBuffer.isEmpty()) {
            throw new Exception("Digit buffer is not empty.");
        }
    }

    /**
     * Checks whether the provided cell range is valid.
     * @param leftCell  Left cell.
     * @param rightCell Right cell.
     * @return          True, if cell range is referenced in a valid way; false, otherwise.
     */
    public boolean validCellRange(String leftCell, String rightCell) {
        // A range is alright if left cell's column comes before (or is the same) right cell's column AND
        // left cell's is comes before (or is the same) right cell's row.
        return leftCell.charAt(0) <= rightCell.charAt(0) &&
                Integer.parseInt(leftCell.substring(1)) <= Integer.parseInt(rightCell.substring(1));
    }

    /**
     * Checks whether the given character is valid as a part of function identifier.
     * Function identifiers may only contain letters and digits
     * @param chr Character inspected.
     * @return    Whether the character inspected is valid part of function identifier.
     */
    public boolean validNamedFunctionCharacter(Character chr) {
        // Function identifiers may only contain letters and digits
        return (Character.isLetterOrDigit(chr));
    }

    /**
     * Parses the part of the expression that is a potential function identifier (e.g. pow).
     *
     * @param expression      Complete expression currently being tokenized.
     * @param characterBuffer Character buffer - used in identifier processing.
     * @return                Function identifier, as a String.
     * @throws Exception      In case of invalid expression.
     */
    public String parseFunction(String expression, ArrayList<Character> characterBuffer) throws Exception {
        // Collect the valid characters for function representation
        while (pos < expression.length() && validNamedFunctionCharacter(expression.charAt(pos))) {
            characterBuffer.add(expression.charAt(pos));
            pos++;
        }

        // Store the function identifier and flush the buffer
        String functionName = bufferToString(characterBuffer);
        characterBuffer.clear();

        // After a function identifier, "(" must come
//        if (pos >= expression.length() || expression.charAt(pos) != '(') {
//            throw new Exception("Improperly named function token.");
//        }

        return functionName;
    }

    /**
     * @param token Token that is being checked for being a function.
     * @return      Function instance in case function is found; null, otherwise.
     */
    public Function findFunction(String token) {
        for (Function function : functions) {
            if (function.equals(token)) {
                return function;
            }
        }
        return null;
    }
}
