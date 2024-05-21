package com.lnedimovic.table_editor.expression;

// Operation and Function
import com.lnedimovic.table_editor.dtype.dtypes.DTypeBoolean;
import com.lnedimovic.table_editor.dtype.dtypes.DTypeString;
import com.lnedimovic.table_editor.expression.operation.Operation;
import com.lnedimovic.table_editor.expression.function.Function;

// Tokenization
import com.lnedimovic.table_editor.expression.operation.OperationSet;
import com.lnedimovic.table_editor.expression.token.Token;
import com.lnedimovic.table_editor.expression.token.TokenType;
import com.lnedimovic.table_editor.table.model.TableModel;

// AST Building
import com.lnedimovic.table_editor.expression.ast.ASTree;
import com.lnedimovic.table_editor.expression.ast.node.*;
import com.lnedimovic.table_editor.expression.ast.node.nodes.*;

import java.util.*;

/**
 * <code>Parser</code> used for parsing already tokenized expressions.
 * Parser aims to create an <a href="https://en.wikipedia.org/wiki/Abstract_syntax_tree">Abstract Syntax Tree</a>.
 * Then, the AST created is evaluated in standard post-order traversal fashion.
 */
public class Parser {
    /**
     * Set of operations used by the instance of parser.
     * It is possible to create different versions of parsers with totally different operation sets - it is totally customizable.
     */
    private OperationSet operations;

    /**
     * Set of functions used by the instance of parser.
     * It is possible to create different versions of parsers with totally different function sets - it is totally customizable.
     * Parser doesn't use this array. It has been added for future flexibility use.
     */
    private Function[]  functions  = new Function[]{};

    /**
     * Array of Token objects, used during the parsing. Implemented this way, instead of ArrayList, so the Iterators are bypassed, and the parsing is a bit more natural.
     */
    private Token[] tokens;

    /**
     * Variable holding the current token looked at.
     */
    private int     tokensIdx;

    /**
     * Creates an instance of <code>Parser</code>, provided set of operations and functions.
     * @param operations Set of operations to be used during parsing.
     * @param functions  Set of functions to be used during parsing.
     * */
    public Parser(OperationSet operations, Function[] functions) {
        this.operations = operations;
        this.functions = functions;
    }

    /**
     * Creates an instance of <code>Parser</code>, provided set of operations.
     * It is possible to create a parser without operations.
     *
     * @param operations Set of operations to be used during parsing.
     * */
    public Parser(OperationSet operations) {
        this.operations = operations;
    }

    /**
     * Creates an instance of <code>Parser</code>, provided set of functions.
     * It is possible to create a parser without operations.
     *
     * @param functions Set of functions to be used during parsing.
     * */
    public Parser(Function[] functions) {
        this.functions = functions;
    }

    /**
     * Creates an instance of <code>Parser</code>, provided set of operations and functions. Additionally, table model is provided.
     * @param operations Set of operations used during parsing.
     * @param functions  Set of functions used during parsing.
     * @param model      Table model, whose data is to be referenced.
     * */
    public Parser(OperationSet operations, Function[] functions, TableModel model) {
        this.operations = operations;
        this.functions = functions;
    }

    /**
     * Parses given list of tokens.
     * The aim of the function is to create a complete <a href="https://en.wikipedia.org/wiki/Abstract_syntax_tree">Abstract Syntax Tree</a>.
     * Implemented parsing method is a modification of well known <b><a href="https://en.wikipedia.org/wiki/Recursive_descent_parser">Recursive Descent Parser</a></b>.
     * <a href="https://en.wikipedia.org/wiki/Shunting_yard_algorithm">Shunting yard algorithm</a> was also tried out, but didn't produce enough flexibility and is not a wise choice in the long run.
     *
     * @param  tokens    List of tokens in a given expression, in order.
     * @return           Abstract syntax tree of given tokens (expression).
     * @throws Exception In case there is a parsing error (invalid formula).
     */
    public ASTree parseTokens(ArrayList<Token> tokens) throws Exception {
        // Convert ArrayList of tokens into a fixed size array
        // Easier for traversal while parsing
        Token[] tokensArray = new Token[tokens.size()];
        tokensArray = tokens.toArray(tokensArray);
        this.tokens = tokensArray;

        tokensIdx = -1; // parse() increments `tokensIdx` by itself, so to start with the first token - this is needed

        // Print tokenized expression
        System.out.println("Tokenized expression: ");
        for (Token token : tokens) {
            System.out.print(token + " :: ");
        }
        System.out.println();

        // Create new Abstract Syntax Tree and parse the tokenized expression into nodes of one
        ASTree tree = new ASTree();
        tree.setRoot(parse(0));

        System.out.println(tree);
        return tree;
    }

    /**
     * Recursively parses given expression. The only relevant piece of information used is precedence at the current level.
     *
     * @param  precedence Precedence of given part of the expression (i.e. it's operation)
     * @return            Abstract syntax tree node (to be root)
     * @throws Exception  In case there is a parsing error (invalid formula).
     */
    public Node parse(int precedence) throws Exception {
        tokensIdx++; // For the ease of use, `parse()` increments to the next token by itself

        // If the end is reached - return null
        if (tokensIdx == tokens.length) {
            return null;
        }

        Node left; // Left part of the expression - is always returned
        Node node; // Right part of the expression - is used in certain cases

        // Gather information about current token
        Token token         = tokens[tokensIdx];
        TokenType tokenType = token.getType();
        Object related      = token.getRelated();

        // Parse the token according to the grammar
        switch (tokenType) {
            case PARENTHESIS: {
                String value = (String) token.getValue().getValue();

                // Must be a "("
                if (value.equals("(")) {
                    left = parse(0); // Parse what is inside. Parenthesis resets the precedence.

                    // For every open parenthesis, there must come a closed one
                    token = tokens[tokensIdx];
                    if (!token.getValue().getValue().equals(")")) {
                        throw new Exception("Invalid expression. Expected a ')'");
                    }

                    // Skip the ")" token
                    tokensIdx++;
                }
                else {
                    throw new Exception("Expected a value, found: " + value);
                }

                break;
            }

            case OPERATION: {
                // Must be a unary operation. Binary operations are parsed separately below.

                if (((Operation) related).isUnary()) {
                    // Parse what comes after the unary operation
                    int unaryPrecedence = ((Operation) related).getPrecedence();
                    Node unaryValue = parse(unaryPrecedence);

                    // Store the operation itself and  what comes after the unary operation
                    left = new UnaryOpNode((Operation) related, unaryValue);
                } else {
                    throw new Exception("Invalid expression. Expected unary operation.");
                }

                break;
            }

            case NUMERICAL_CONSTANT: {
                // Create a new node, keeping the numerical constant value
                left = new ConstantNode(token.getValue());
                tokensIdx++;

                break;
            }

            case FUNCTION: {
                // Get the function inside the token and the arity of it.
                Function function = (Function) (token.getRelated());

                // Test for the existence of "(" for function parameters
                token = tokens[++tokensIdx];
                if (!token.getValue().getValue().equals("(")) {
                    throw new Exception("Invalid expression. Expected a '(', found: " + token);
                }

                // Check for nullary functions:
                if (tokensIdx < tokens.length - 1 && tokens[tokensIdx + 1].getValue().getValue().equals(")")) {
                    left = new FunctionNode(function, new Node[0]);
                    tokensIdx += 2; // Skip the ")"

                    break;
                }

                // Function has arguments.
                ArrayList<Node> arguments = new ArrayList<>();

                // Parse until ")" is encountered (end of function)
                while (!tokens[tokensIdx].getValue().getValue().equals(")")) {
                    Node parameter = parse(0); // Parse the parameter

                    // Parameters must be separated by commas, or if there are no more, ")" is expected
                    if (tokens[tokensIdx].getType() != TokenType.COMMA &&
                        !tokens[tokensIdx].getValue().getValue().equals(")")) {
                        throw new Exception("Invalid expression. Expected comma.");
                    }

                    arguments.add(parameter);
                }

                Node[] argumentsArray = new Node[arguments.size()];
                argumentsArray = arguments.toArray(argumentsArray);

                // Create a node with corresponding function and all the parameters as separate nodes
                left = new FunctionNode(function, argumentsArray);
                tokensIdx++;

                break;
            }

            case REFERENCE: {
                // Cell reference is handled by simply passing the value of range
                left = new ReferenceNode(token.getValue().toString());
                tokensIdx++;

                break;
            }

            case QUOTATION_MARK: {
                // Whatever comes until next occurence of " symbol, will be rendered as a string.
                tokensIdx++;

                String stringValue = "";
                while (tokensIdx < tokens.length && tokens[tokensIdx].getType() != TokenType.QUOTATION_MARK) {
                    token = tokens[tokensIdx];
                    stringValue += token.getValue();

                    tokensIdx++;
                }

                // Another " is expected after the first one
                if (tokensIdx >= tokens.length || tokens[tokensIdx].getType() != TokenType.QUOTATION_MARK) {
                    throw new Exception("Invalid expression. Expected quotation mark.");
                }

                left = new ConstantNode(new DTypeString(stringValue));
                tokensIdx++;

                if (tokensIdx < tokens.length &&
                    tokens[tokensIdx].getType() != TokenType.OPERATION &&
                    tokens[tokensIdx].getType() != TokenType.COMMA &&
                    !(tokens[tokensIdx].getType() == TokenType.PARENTHESIS && tokens[tokensIdx].getValue().getValue().equals(")"))) {
                    throw new Exception("Invalid expression. Expected an operator, comma, or a closed parenthesis, but found: " + tokens[tokensIdx].getValue());
                }

                break;
            }

            case STRING: {
                throw new Exception("Invalid expression. String must be enclosed within \"");
            }

            case TRUE: {
                left = new ConstantNode(new DTypeBoolean(true));
                tokensIdx++;

                break;
            }

            case FALSE: {
                left = new ConstantNode(new DTypeBoolean(false));
                tokensIdx++;

                break;
            }

            default: {
                throw new Exception("Invalid expression. Expected a value.");
            }
        }

        // Parsing the binary operations
        // It is done recursively due to the cases such as 1+3+5+7

        // Parse until there are no more tokens
        while (tokensIdx < tokens.length) {
            token = tokens[tokensIdx]; // Get current token

            if (token.getType() == TokenType.OPERATION) {
                // Unary operations should've been parsed
                if (((Operation) token.getRelated()).isUnary()) {
                    throw new Exception("Invalid expression. Expected a binary operation.");
                }

                // Continue parsing only if the precedence of current operation is less than the next one
                Operation operation = (Operation) token.getRelated();
                if (precedence < operation.getPrecedence()) {
                    Node right = parse(operation.getPrecedence());

                    // Set values for children nodes
                    node = new BinaryOpNode(operation, left, right);

                    left = node; // Recursively parse next
                }
                else {
                    break;
                }
            }
            else {
                break;
            }
        }

        return left;
    }

    /**
     * @return Set of operations used in the parser.
     */
    public OperationSet getOperations() {
        return operations;
    }

    /**
     * Sets the new set of operations inside the parser.
     * @param operations New set of operation to be used.
     */
    public void setOperations(OperationSet operations) {
        this.operations = operations;
    }

    /**
     * @return Latest array of tokens used in the parser.
     */
    public Token[] getTokens() {
        return tokens;
    }

    /**
     * New array of tokens to use in the parser.
     * @param tokens New set of tokens to be used.
     */
    public void setTokens(Token[] tokens) {
        this.tokens = tokens;
    }

    /**
     * @return Index of the current token perceived by the parser.
     */
    public int getTokensIdx() {
        return tokensIdx;
    }
}
