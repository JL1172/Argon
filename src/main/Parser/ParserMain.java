package main.Parser;

import main.AST.Node;
import main.IntermediateASTNodes.FieldNameObjectNode;
import main.IntermediateASTNodes.MethodDeclarationObjectNode;
import main.IntermediateASTNodes.NodeTypeEnum;
import main.IntermediateASTNodes.ObjectNodeTemplate;
import main.IntermediateASTNodes.TemporalObjectNode;
import main.Lexer.LexerToken;
import main.Lexer.LexerMain.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//* stack to keep track of the braces brackets and parentheses */
class Stack {
    private HashMap<Integer, String> storage = new HashMap<>();
    private int count = 0;

    void push(String value) {
        this.storage.put(this.count, value);
        this.count++;
    }

    String pop() {
        if (this.count > 0) {
            this.count--;
            String returnValue = this.storage.remove(this.count);
            return returnValue;
        } else {
            return null;
        }
    }

    int size() {
        return this.count;
    }
}

/* main parse method */
public class ParserMain {

    private List<LexerToken> tokenList;
    private int index;
    private HashMap<String, String> matching_symbols = new HashMap<>();
    private Stack stack = new Stack();
    private List<Node> AST = new ArrayList<Node>();
    // ! this is going to keep track of all field names method names and then insert
    // them into a larger container.
    private List<FieldNameObjectNode> fieldNameRecords = new ArrayList<>();
    private List<MethodDeclarationObjectNode> methodNameRecords = new ArrayList<>();
    private List<ObjectNodeTemplate> intermediateASTContainer = new ArrayList<>();
    private TemporalObjectNode temporalContainer;

    public ParserMain(List<LexerToken> tokens) {
        matching_symbols.put("}", "{");
        matching_symbols.put("]", "[");
        matching_symbols.put(")", "(");
        this.temporalContainer = new TemporalObjectNode(null, null, null, null, null, null, null, null, null);
        this.tokenList = tokens;
        this.index = 0;
    }

    public void parseClass() {
        parseClassDeclaration();
        // parseClassBody();
        // parseFieldNameDeclaration();
        // parseThisMethodDeclaration();
        // parseMethodDeclaration();
    }

    /*                                                                                       */
    /* main methods */
    /*                                                                                       */
    private void parseClassDeclaration() {
        // expect pub keyword
        TokenType[] allowedTokens = { TokenType.PUB };
        this.parseAccessModifier(allowedTokens);
        // expect cls keyword
        this.expect(TokenType.CLS);

        // expect identifier name to be legal and returns parsed Identifier name
        this.parseClassName(TokenType.IDENTIFIER);

        // expects "{" and adds to stack to keep track of characters
        this.parseLBrace();

        // List<Node> classBody =
        this.parseClassBody();

        // this.parseRBrace();

    }

    private void parseClassBody() {
        // search for field names
        this.parseFieldNamesOrMethodNames();
        // return classBody;
    }

    /*                                                                                                               */
    /* These methods are for parsing access modifiers */
    /*                                                                                                               */
    private void parseFieldNamesOrMethodNames() {
        TokenType[] allowedTokensForAccessModifiers = { TokenType.PUB, TokenType.PROT, TokenType.RESERVED };
        // look for access modifier
        TokenType accessModifier = this.parseAccessModifier(allowedTokensForAccessModifiers);
        if (accessModifier != null) {
            this.temporalContainer.accessModifier = accessModifier;
        }
        // look for stat keyword
        TokenType statKeyword = this.parseStaticKeyword();
        if (statKeyword != null) {
            this.temporalContainer.keyword = statKeyword;
        }
        // look for const keyword
        TokenType constKeyword = this.parseConstImmutabilityKeyword();
        if (constKeyword != null) {
            this.temporalContainer.immutability_keyword = constKeyword;
        }
        // look for identifier
        String identifier = this.parseIdentifier(TokenType.IDENTIFIER);
        this.temporalContainer.identifier = identifier;

        // look for type, if parentheses then move to method function
        NodeTypeEnum nodeType = this.parseMethodOrFieldNameDirection();

        // then load method or field name nodes with the values then clear temporal
        // container
        if (nodeType == NodeTypeEnum.METHOD) {

            // this takes care of if a method is given the wrong access modifier
            if (accessModifier == TokenType.PROT) {
                this.reportError(String.format(
                        "Syntax Error: Illegal Access Modifier For Method, Only Allow 'reserved', 'public', or leave blank for 'package-private'. Received: %s",
                        TokenType.PROT), constKeyword);
            }
            if (constKeyword == TokenType.CONST) {
                this.reportError(String.format(
                        "Syntax Error: Illegal Keyword Usage For Method, Only Allow 'stat' or leave blank for non-static functionality. Received: %s",
                        TokenType.CONST), constKeyword);
            }

            // if the correct access modifier and keywrod is used then the node type of
            // temporalconatiner is set to method

            this.temporalContainer.nodeType = NodeTypeEnum.METHOD;

            // this now specifies the node and inserts all the values from temporary
            // container to methodnode
            MethodDeclarationObjectNode methodDeclarationNode = new MethodDeclarationObjectNode(
                    this.temporalContainer.nodeType, this.temporalContainer.accessModifier,
                    this.temporalContainer.keyword, null, null, null, null, this.temporalContainer.identifier, null);

            // clearing for memory purposes
            this.temporalContainer.clear();

            methodDeclarationNode.returnMethodDeclarationValues();

        } else if (nodeType == NodeTypeEnum.FIELD_NAME) {

            this.temporalContainer.nodeType = NodeTypeEnum.FIELD_NAME;

            this.temporalContainer.type_annotations = TokenType.DOUBLE_COLON;

            FieldNameObjectNode fieldNameNode = new FieldNameObjectNode(this.temporalContainer.nodeType,
                    this.temporalContainer.accessModifier, this.temporalContainer.keyword,
                    this.temporalContainer.immutability_keyword, null, null, null, this.temporalContainer.identifier,
                    this.temporalContainer.type_annotations, null, false);

            // clearing for memory purposes
            this.temporalContainer.clear();

            TokenType[] allowedTypes = { TokenType.STRING_IDENTIFIER, TokenType.INT_IDENTIFIER,
                    TokenType.FLOAT_IDENTIFIER, TokenType.DOUBLE_IDENTIFIER, TokenType.BOOLEAN_IDENTIFIER };

            TokenType asserted_type = this.parseFieldNameStaticType(allowedTypes);

            if (asserted_type != null) {
                fieldNameNode.type = asserted_type;
            }

            TokenType isEndOfFieldNameOrValueIsAssigned = this.parseIsEndOfFieldNameOrValueIsAssigned();
            if (isEndOfFieldNameOrValueIsAssigned == null) {

                TokenType assignment_operator = this.parseAssignmentOperator();
                if (assignment_operator == null) {
                    this.reportError("Syntax Error: Unexpected End of Input, expecting '=' Token.",
                            assignment_operator);
                } else {
                    fieldNameNode.assignment = assignment_operator;

                    String fieldNameValue = this.parseFieldNameValue(fieldNameNode.type);

                    LexerToken lastValue = this.peekLastValue();
                    if (lastValue.getType() == TokenType.NUMERIC_TYPE) {
                        TokenType expectedType = fieldNameNode.type;
                        if (expectedType == TokenType.INT_IDENTIFIER) {
                            if (Integer.valueOf(lastValue.getValue()) instanceof Integer) {
                                System.out.print("Integer detected ");
                                System.out.println(lastValue.getValue());
                            }
                        }
                        if (expectedType == TokenType.FLOAT_IDENTIFIER) {
                            if (Float.valueOf(lastValue.getValue()) instanceof Float) {
                                System.out.print("Float detected ");
                                System.out.println(lastValue.getValue());
                            } 
                        }
                        if (expectedType == TokenType.DOUBLE_IDENTIFIER) {
                            if (Double.valueOf(lastValue.getValue()) instanceof Double) {
                                System.out.print("Double detected ");
                                System.out.println(lastValue.getValue());
                            }
                        }
                        fieldNameNode.value = fieldNameValue;
                        fieldNameNode.is_assigned_a_value = true;
                    } else {
                        fieldNameNode.value = fieldNameValue;
                    }
                   TokenType semi = this.parseSemiColon();
                   fieldNameNode.semi_colon = semi;
                }

            } else {
                fieldNameNode.semi_colon = isEndOfFieldNameOrValueIsAssigned;
            }
            fieldNameNode.returnMethodDeclarationValues();
        }
    }

    /*                                                                                                               */
    /* These methods are for parsing field or method declarations */
    /*                                                                                                               */
    private NodeTypeEnum parseMethodOrFieldNameDirection() {
        LexerToken token = this.peek();
        TokenType isFieldName = TokenType.DOUBLE_COLON;
        TokenType isMethodDeclaration = TokenType.LPAREN;
        if (token.getType() != isFieldName && token.getType() != isMethodDeclaration) {
            this.reportError(String.format(
                    "Syntax Error: Improper Construction Of Either FieldName or Method Declaration, expected either %s or %s",
                    isFieldName, isMethodDeclaration), isMethodDeclaration);
        }
        if (token.getType() == isFieldName) {
            this.index++;
            return NodeTypeEnum.FIELD_NAME;
        } else if (token.getType() == isMethodDeclaration) {
            this.index++;
            return NodeTypeEnum.METHOD;
        }
        this.reportError(String.format(
                "Syntax Error: Improper Construction Of Either FieldName or Method Declaration, expected either %s or %s",
                isFieldName, isMethodDeclaration), isMethodDeclaration);
        return null;
    }

    private TokenType parseAccessModifier(TokenType[] allowedAccessModifiers) {
        LexerToken token = this.peek();
        for (int i = 0; i < allowedAccessModifiers.length; i++) {
            if (token.getType() == allowedAccessModifiers[i]) {
                // TODO for AST need to assign access modifier for that portion of the tree
                this.index++;
                return allowedAccessModifiers[i];
            }
        }
        return null;
    }

    private TokenType parseStaticKeyword() {
        LexerToken token = this.peek();
        if (token.getType() == TokenType.STAT) {
            this.index++;
            return TokenType.STAT;
        }
        return null;
    }

    private TokenType parseConstImmutabilityKeyword() {
        LexerToken token = this.peek();
        if (token.getType() == TokenType.CONST) {
            this.index++;
            return TokenType.CONST;
        }
        return null;
    }

    private String parseIdentifier(TokenType expectedType) {
        LexerToken token = this.peek();
        Pattern firstCharIsLetter = Pattern.compile("[a-z]");
        String firstChar = String.valueOf(token.getValue().charAt(0));
        Matcher matcher = firstCharIsLetter.matcher(firstChar);
        if (token.getType() != expectedType) {
            this.reportError(
                    String.format("Compilation Error: Unexpected Token, Received: %s, Expected:", token.getType()),
                    expectedType);
        }
        if (!(matcher.lookingAt())) {
            this.reportError("Syntax Error: Identifier Must Start With a lower case letter.", expectedType);
        }
        this.index++;
        return token.getValue();
    }

    private TokenType parseFieldNameStaticType(TokenType[] expectedTypes) {
        LexerToken token = this.peek();
        for (TokenType expectedType : expectedTypes) {
            if (token.getType() == expectedType) {
                this.index++;
                return expectedType;
            }
        }
        this.reportError("Syntax Error: Type Expected Following '::' Token, Received:", null);
        return null;
    }

    private TokenType parseIsEndOfFieldNameOrValueIsAssigned() {
        LexerToken token = this.peek();
        if (token.getType() == TokenType.SEMICOLON) {
            this.index++;
            return TokenType.SEMICOLON;
        }
        return null;
    }
    private TokenType parseSemiColon() {
        LexerToken token = this.peek();
        if (token.getType() != TokenType.SEMICOLON) {
            this.reportError("Syntax Error: Expected ';' Token At End Of Input", TokenType.SEMICOLON);
        } else if (token.getType() == TokenType.SEMICOLON) {
            this.index++;
            return TokenType.SEMICOLON;
        }
        this.reportError("Syntax Error: Expected ';' Token At End Of Input", TokenType.SEMICOLON);
        return null;
    }
    private TokenType parseAssignmentOperator() {
        LexerToken token = this.peek();
        if (token.getType() != TokenType.ASSIGNMENT) {
            this.reportError("Syntax Error: Unexpected End Of Input, Expected Assignment Operator", token.getType());
        }
        if (token.getType() == TokenType.ASSIGNMENT) {
            this.index++;
            return TokenType.ASSIGNMENT;
        }
        return null;
    }

    private String parseFieldNameValue(TokenType expectedTypeOfValue) {
        LexerToken token = this.peek();
        String currentTokenValue = token.getValue();
        TokenType currentTokenType = token.getType();
        if (token.getType() == TokenType.STRING_TYPE) {
            if (expectedTypeOfValue == TokenType.STRING_IDENTIFIER) {
                this.index++;
                return currentTokenValue;
            } else {
                this.reportError(String.format(
                        "Syntax Error: Unable To Resolve Type, There is a type mismatch from what type was expected. Expected Type: %s Received Type %s.",
                        expectedTypeOfValue, currentTokenType), currentTokenType);
            }
        }
        if (token.getType() == TokenType.NUMERIC_TYPE) {
            if (expectedTypeOfValue == TokenType.INT_IDENTIFIER || expectedTypeOfValue == TokenType.FLOAT_IDENTIFIER
                    || expectedTypeOfValue == TokenType.DOUBLE_IDENTIFIER) {
                this.index++;
                return currentTokenValue;
            } else {
                this.reportError(String.format(
                        "Syntax Error: Unable To Resolve Type, There is a type mismatch from what type was expected. Expected Type: %s Received Type %s.",
                        expectedTypeOfValue, currentTokenType), currentTokenType);
            }
        }
        if (token.getType() == TokenType.BOOLEAN_TYPE) {
            if (expectedTypeOfValue == TokenType.BOOLEAN_IDENTIFIER) {
                this.index++;
                return currentTokenValue;
            } else {
                this.reportError(String.format(
                        "Syntax Error: Unable To Resolve Type, There is a type mismatch from what type was expected. Expected Type: %s Received Type %s.",
                        expectedTypeOfValue, currentTokenType), currentTokenType);
            }
        }
        if (token.getValue().equals("null")) {
            this.index++;
            return currentTokenValue;
        }
        this.reportError(String.format(
                "Syntax Error: Unable To Resolve Type, There is a type mismatch from what type was expected. Expected Type: %s Received Type %s.",
                expectedTypeOfValue, currentTokenType), currentTokenType);
        return null;
    }

    private TokenType parseNumberType(String numberValueToParse) {
        return null;
    }

    /*                                                                                                               */
    /*
     * These are for parsing the class name, ensuring its an identifier and follows
     * the correct casing requirements
     */
    /*                                                                                                               */
    private void parseClassName(TokenType expectToken) {
        LexerToken peek = this.peek();
        if (peek.getType() != expectToken) {
            this.reportError("Syntax Error: Illegal Class Name. Must Be Pascal Case", expectToken);
        }
        this.parsePascalCase(peek.getValue());
        index++;
    }

    private void parsePascalCase(String identifier) {
        String firstLetter = String.valueOf(identifier.charAt(0));
        Pattern letterMatcher = Pattern.compile("[A-Z]");
        Matcher matcher = letterMatcher.matcher(firstLetter);
        if (!(matcher.lookingAt())) {
            this.reportError("Syntax Error: Illegal Class Name. Must Be Pascal Case And Start With Letters Only",
                    null);
        }
    }

    /*                                                                                           */
    /* This uses a stack to keep track of braces, brackets, and parentheses */
    /*                                                                                           */
    private void parseSequenceBracketsBraceParenthesis(String symbol) {
        String returnValueFromStackPop = this.stack.pop();
        if (!(this.matching_symbols.get(symbol).equals(returnValueFromStackPop))) {
            this.reportError(
                    String.format("Compilation Error: Improper Sequence %s, Expect Adjacent Closing %s at index %o",
                            symbol, returnValueFromStackPop, index),
                    null);
        }
    }

    private void parseLBrace() {
        expect(TokenType.LBRACE);
        this.stack.push("{");
    }

    private void parseLParen() {
        expect(TokenType.LPAREN);
        this.stack.push("(");
    }

    private void parseRBrace() {
        expect(TokenType.RBRACE);
        this.parseSequenceBracketsBraceParenthesis("}");
    }

    private void parseRParen() {
        expect(TokenType.RPAREN);
        this.parseSequenceBracketsBraceParenthesis(")");
    }

    /*                                                                                           */
    /* error handling */
    /*                                                                                           */
    private void reportError(String message, TokenType token) {
        throw new RuntimeException(String.format(message + " %s", token));
    }

    private boolean hasMoreTokens() {
        return this.index < this.tokenList.size();
    }

    private LexerToken peek() {
        if (this.tokenList.size() > 0) {
            return this.tokenList.get(index);
        } else {
            return new LexerToken(TokenType.EOF, "EOF");
        }
    }
    private LexerToken peekLastValue() {
        if (this.tokenList.size() > 0) {
            return this.tokenList.get(index - 1);
        } else {
            return new LexerToken(TokenType.EOF,"EOF");
        }
    }
    private void expect(TokenType expectedType) {
        LexerToken token = this.peek();
        if (token.getType() != expectedType) {
            this.reportError(
                    String.format("Compilation Error: Expected Token: %s, Recieved %s", expectedType, token.getType()),
                    expectedType);
        }
        this.index++;
    }

    public static void main(String[] args) {
        System.out.println("hello world from parse");
    }
}
