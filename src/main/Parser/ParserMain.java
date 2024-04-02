package main.Parser;

import main.Lexer.LexerToken;
import main.Lexer.LexerMain.TokenType;

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

//* main parse method */
public class ParserMain {

    private List<LexerToken> tokenList;
    private int index;
    private HashMap<String, String> matching_symbols = new HashMap<>();
    private Stack stack = new Stack();

    public ParserMain(List<LexerToken> tokens) {
        matching_symbols.put("}", "{");
        matching_symbols.put("]", "[");
        matching_symbols.put(")", "(");
        this.tokenList = tokens;
        this.index = 0;
    }

    public void parseClass() {
        parseClassDeclaration();
        parseClassBody();
        // parseFieldNameDeclaration();
        // parseThisMethodDeclaration();
        // parseMethodDeclaration();
    }

    // !class declaration methods
    //* entry */
    private void parseClassDeclaration() {
        // optional access modifier + cls keyword + PascalCase IDENTIFIER + { ...
        // methods ,field names, etc. + }

        // expect pub keyword
        this.expect(TokenType.PUB);

        // expect cls keyword
        this.expect(TokenType.CLS);

        // expect identifier name to be legal and returns parsed Identifier name
        this.parseClassName(TokenType.IDENTIFIER);
        
        this.parseLBrace();
    }

    private void parseClassName(TokenType expectToken) {
        LexerToken peek = this.peek();
        if (peek.getType() != expectToken) {
            this.reportError("Compilation Error: Illegal Class Name. Must Be Pascal Case", expectToken);
        }
        this.parsePascalCase(peek.getValue());
        index++;
    }

    private void parsePascalCase(String identifier) {
        String firstLetter = String.valueOf(identifier.charAt(0));
        Pattern letterMatcher = Pattern.compile("[A-Z]");
        Matcher matcher = letterMatcher.matcher(firstLetter);
        if (!(matcher.lookingAt())) {
            this.reportError("Compilation Error: Illegal Class Name. Must Be Pascal Case And Start With Letters Only",
                    null);
        }
    }
    // ! end of class methods

    //! class body methods
    //* entry */
    private void parseClassBody() {

    }

    //! field name methods 

    private void parseFieldNameDeclaration() {
        // optional access modifier + optional stat keyword + optional const keyword +
        // IDENTIFIER + :: + TYPE + optional "=" or "; (if this end)" + if = then
        // respective value that matches type.
        
    }

    //! field name methods 
    private void parseThisMethodDeclaration() {
        // optional access modifier + optional stat keyword + IDENTIFIER + ( optional
        // parameter:: type) :: type {...code, optional return if type is not void}
    }

    private void parseType() {

    }

    private void parseParameter() {
        // IDENTIFIER :: TYPE
    }

    private void parseMethodDeclaration() {
        // access + identifier + () + :: + type + {}
    }

    private void parseVariableDeclaration() {
        // optional const keyword + IDENTIFIER camelCase or snake case + :: + Type = +
        // respective value according to type
        // or
        // optional const keyword + IDENTIFIER + :: + (Type === className ? className +
        // = new + className + ( + ); : typename + = + respective type )
    }

    // ! global utility methods
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
    /* error handling */
    private void reportError(String message, TokenType token) {
        // System.err.println(message + " " + token);
        throw new RuntimeException(String.format(message + " %s", token));
    }

    // ! safe utility fn to return the current token
    private LexerToken peek() {
        if (this.tokenList.size() > 0) {
            return this.tokenList.get(index);
        } else {
            return new LexerToken(TokenType.EOF, "EOF");
        }
    }

    // ! test function
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
