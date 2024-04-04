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
import java.util.Arrays;
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

        // call method to parse body of class
        this.parseClassBody();

        // this.parseRBrace();

    }

    private void parseClassBody() {
        // this method deals with the two directions this can go a field name or method
        // name, fortunately field names can only be initialized before methods, after
        // or between
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
        // this doesnt have to have an access modifier because default is
        // package-private
        if (accessModifier != null) {
            // if there is an access modifier, the the temporary container, which is just
            // the container that is filled with values before the parser can decide if it
            // is a method or field name being instantiated
            this.temporalContainer.accessModifier = accessModifier;
        }
        // look for stat keyword
        TokenType statKeyword = this.parseStaticKeyword();
        // same thing as access modifier method about
        if (statKeyword != null) {
            this.temporalContainer.keyword = statKeyword;
        }
        // look for const keyword
        // same thing as access modifier method
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
            // this just transfers all the values from the amorphus temporary container to
            // the semantic method container
            MethodDeclarationObjectNode methodDeclarationNode = new MethodDeclarationObjectNode(
                    this.temporalContainer.nodeType, this.temporalContainer.accessModifier,
                    this.temporalContainer.keyword, null, null, null, null, this.temporalContainer.identifier, null);

            // clearing for memory purposes, because i no longer need that space being
            // occupied in memory
            this.temporalContainer.clear();

            // this is just a console log for debugging
            // todo get rid of this shit this is just for debugging
            System.out.println(methodDeclarationNode.toString());

        } else if (nodeType == NodeTypeEnum.FIELD_NAME) {

            this.temporalContainer.nodeType = NodeTypeEnum.FIELD_NAME;

            this.temporalContainer.type_annotations = TokenType.DOUBLE_COLON;

            FieldNameObjectNode fieldNameNode = new FieldNameObjectNode(this.temporalContainer.nodeType,
                    this.temporalContainer.accessModifier, this.temporalContainer.keyword,
                    this.temporalContainer.immutability_keyword, null, null, null, this.temporalContainer.identifier,
                    this.temporalContainer.type_annotations, null, false);

            // clearing for memory purposes
            this.temporalContainer.clear();

            // allowed primitive types
            TokenType[] allowedTypes = { TokenType.STRING_IDENTIFIER, TokenType.INT_IDENTIFIER,
                    TokenType.FLOAT_IDENTIFIER, TokenType.DOUBLE_IDENTIFIER, TokenType.BOOLEAN_IDENTIFIER };

            // ensures statically typed type is correct
            TokenType asserted_type = this.parseFieldNameStaticType(allowedTypes);

            // if its not null, a little overkill bc it wouldnt make it to null bc of the
            // error but whatever.
            if (asserted_type != null) {
                fieldNameNode.type = asserted_type;
            }

            // this is deciding if there is a value assigned to this, or if there an
            // assignment operator
            TokenType isEndOfFieldNameOrValueIsAssigned = this.parseIsEndOfFieldNameOrValueIsAssigned();

            // if it is null that signals there is an assignment operator
            if (isEndOfFieldNameOrValueIsAssigned == null) {

                TokenType assignment_operator = this.parseAssignmentOperator();

                if (assignment_operator == null) {
                    // overkill but just in case
                    this.reportError("Syntax Error: Unexpected End of Input, expecting '=' Token.",
                            assignment_operator);
                }
                    // if there is an assignment operator
                    fieldNameNode.assignment = assignment_operator;

                    String fieldNameValue = this.parseFieldNameValue(fieldNameNode.type);

                    // this grabs the most recent value
                    LexerToken lastValue = this.peekLastValue();

                    if (lastValue.getType() == TokenType.NUMERIC_TYPE) {
                        // grabbing the statically typed type
                        TokenType expectedType = fieldNameNode.type;
                        // if int type, then parse int to see if it is an instance of an integer
                        if (expectedType == TokenType.INT_IDENTIFIER) {
                            try {
                                boolean isInteger = Integer.valueOf(lastValue.getValue()) instanceof Integer;
                            } catch (NumberFormatException e) {
                                this.reportError(e.getMessage(), expectedType);
                            }
                        }
                        if (expectedType == TokenType.FLOAT_IDENTIFIER) {
                            try {
                                boolean isFloat = Float.valueOf(lastValue.getValue()) instanceof Float;
                            } catch (NumberFormatException e) {
                                this.reportError(e.getMessage(), expectedType);
                            }
                        }
                        if (expectedType == TokenType.DOUBLE_IDENTIFIER) {
                            try {
                                boolean isDouble = Double.valueOf(lastValue.getValue()) instanceof Double;
                            } catch (NumberFormatException e) {
                                this.reportError(e.getMessage(), expectedType);
                            }
                        }
                    }
                    fieldNameNode.value = fieldNameValue;
                    fieldNameNode.is_assigned_a_value = true;
                    TokenType semi = this.parseSemiColon();
                    fieldNameNode.semi_colon = semi;

            } else {
                // this is if there is a semi colon
                fieldNameNode.semi_colon = isEndOfFieldNameOrValueIsAssigned;
            }
            // TODO this is where i left off
            //add it to field name records
            this.fieldNameRecords.add(fieldNameNode);
            System.out.println(this.fieldNameRecords.toString());
            //clear fieldname node for spatial complexity purposes
            fieldNameNode.clear();
            //end of else if block if its fieldname
        }
    }

    /*                                                                                                               */
    /* These methods are for parsing field or method declarations */
    /*                                                                                                               */
    private NodeTypeEnum parseMethodOrFieldNameDirection() {
        LexerToken token = this.peek();
        // double colon signal field name instantiation
        TokenType isFieldName = TokenType.DOUBLE_COLON;
        // left paren signals method declaration
        TokenType isMethodDeclaration = TokenType.LPAREN;
        // if the current token's type is not equal to the fieldname expected token or
        // the method declaration's expected token throw the error
        if (token.getType() != isFieldName && token.getType() != isMethodDeclaration) {
            this.reportError(String.format(
                    "Syntax Error: Improper Construction Of Either FieldName or Method Declaration, expected either %s or %s",
                    isFieldName, isMethodDeclaration), isMethodDeclaration);
        }
        // if it equal to field name increment and return that enum value
        if (token.getType() == isFieldName) {
            this.index++;
            return NodeTypeEnum.FIELD_NAME;
        } else if (token.getType() == isMethodDeclaration) {
            this.index++;
            return NodeTypeEnum.METHOD;
        }
        // catch all error
        this.reportError(String.format(
                "Syntax Error: Improper Construction Of Either FieldName or Method Declaration, expected either %s or %s",
                isFieldName, isMethodDeclaration), isMethodDeclaration);
        return null;
    }

    private TokenType parseAccessModifier(TokenType[] allowedAccessModifiers) {
        // this grabs the current token with the peek method
        LexerToken token = this.peek();
        // this loops through the array of TokenType acccess modifiers and sees if the
        // current token equals one of them, if it does then then it increments the
        // index and returns access modifier
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
        // grabs current token
        LexerToken token = this.peek();
        // creates pattern to enfore snake or camel Casing
        Pattern firstCharIsLetter = Pattern.compile("[a-z]");
        String firstChar = String.valueOf(token.getValue().charAt(0));
        // TODO have to throw error for identifiers with anything but numbers letters
        // and underscores
        Matcher matcher = firstCharIsLetter.matcher(firstChar);
        // if its not the right type
        if (token.getType() != expectedType) {
            this.reportError(
                    String.format("Compilation Error: Unexpected Token, Received: %s, Expected:", token.getType()),
                    expectedType);
        }
        // if it doesnt start with lowercase a
        if (!(matcher.lookingAt())) {
            this.reportError("Syntax Error: Identifier Must Start With a lower case letter.", expectedType);
        }
        Pattern enforceIdentifierRules = Pattern.compile("^[A-Za-z0-9_]*$");
        Matcher matcher2 = enforceIdentifierRules.matcher(token.getValue());
        if (!matcher2.matches()) {
            this.reportError("Syntax Error: Identifier Must Only Consist of Numbers, Letters, and Underscores.",
                    expectedType);
        }
        this.index++;
        return token.getValue();
    }

    private TokenType parseFieldNameStaticType(TokenType[] expectedTypes) {
        LexerToken token = this.peek();
        // if the type that is statically declared is not one of the expected types,
        // then throw the error else increment
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
        if (token.getType() != TokenType.SEMICOLON && token.getType() != TokenType.ASSIGNMENT) {
            this.reportError("Syntax Error, Expected Either '=' or ';' not", token.getType());
        }
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
        // if token is stringtype and it has the asserted type as string then all good
        // else failure
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
        // notice i did not parse whether it was an int float or double, as long as the
        // identifier was one of the three it returns the currentTokenValue
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
        // catch all error handler
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
        // grabs first letter of given identifier
        String firstLetter = String.valueOf(identifier.charAt(0));
        // creates regex pattern
        Pattern letterMatcher = Pattern.compile("[A-Z]");
        // creates matcher to apply patter
        Matcher matcher = letterMatcher.matcher(firstLetter);
        // if matcher cannot find pattern, then throw an error
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
        try {
            throw new RuntimeException(String.format(message + " %s", token));
        } catch (RuntimeException e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
            System.exit(0);
        }
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
            return new LexerToken(TokenType.EOF, "EOF");
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
