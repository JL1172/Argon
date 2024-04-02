package main.Parser;

import main.Lexer.LexerToken;
import main.Lexer.LexerMain.TokenType;

import java.util.List;

public class ParserMain {
    private List<LexerToken> tokenList;
    private int index;

    public ParserMain(List<LexerToken> tokens) {
        this.tokenList = tokens;
        this.index = 0;
    }

    public void parseClass() {
        parseClassName();
        parseFieldNameDeclaration();
        parseThisMethodDeclaration();
        parseThisMethodDeclaration();
        parseVariableDeclaration();
    }

    private void parseClassName() {
        // optional access modifier + cls keyword + PascalCase IDENTIFIER + { ...
        // methods ,field names, etc. + }
    }
    private void parsePascalCase() {
        // char firstLetter = 
    }
    private void parseType() {

    }

    private void parseFieldNameDeclaration() {
        // optional access modifier + optional stat keyword + optional const keyword +
        // IDENTIFIER + :: + TYPE + optional "=" or "; (if this end)" + if = then
        // respective value that matches type.
    }

    private void parseThisMethodDeclaration() {
        // optional access modifier + optional stat keyword + IDENTIFIER + ( optional
        // parameter:: type) :: type {...code, optional return if type is not void}
    }

    private void parseParameter() {
        // IDENTIFIER :: TYPE
    }

    private void parseVariableDeclaration() {
        // optional const keyword + IDENTIFIER camelCase or snake case + :: + Type = +
        // respective value according to type
        // or
        // optional const keyword + IDENTIFIER + :: + (Type === className ? className +
        // = new + className + ( + ); : typename + = + respective type )
    }

    private void reportError(String message, TokenType token) {
        // System.err.println(message + " " + token);
        throw new RuntimeException(String.format(message + " %s", token));
    }

    public void match(TokenType values) {
        System.out.println(values);
    }

    public static void main(String[] args) {
        System.out.println("hello world from parse");
    }
}
