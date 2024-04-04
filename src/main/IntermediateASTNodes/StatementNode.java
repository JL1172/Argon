package main.IntermediateASTNodes;

import main.Lexer.LexerMain.TokenType;

public class StatementNode {
    public TokenType coutStatement;
    public Object coutParameters;
    public StatementNode(TokenType coutStatement, int coutParameters) {
        this.coutParameters = coutParameters;
        this.coutStatement = coutStatement;
    }
    public StatementNode(TokenType coutStatement, float coutParameters) {
        this.coutParameters = coutParameters;
        this.coutStatement = coutStatement;
    }
    public StatementNode(TokenType coutStatement, double coutParameters) {
        this.coutParameters = coutParameters;
        this.coutStatement = coutStatement;
    }
    public StatementNode(TokenType coutStatement, boolean coutParameters) {
        this.coutParameters = coutParameters;
        this.coutStatement = coutStatement;
    }
    public StatementNode(TokenType coutStatement, String coutParameters) {
        this.coutParameters = coutParameters;
        this.coutStatement = coutStatement;
    }
    @Override
    public String toString() {
        return "Cout Statement: [\n" +  
                "cout statement= '" + coutStatement + "\n" +
                "cout value= '" + coutParameters;  
    }
}
