package main.IntermediateASTNodes;

import main.Lexer.LexerMain.TokenType;

public class MethodVariableNode {
    public String identifier;
    public TokenType type;
    public TokenType assignment;
    public TokenType semi_colon;
    public Object value;

    public MethodVariableNode(String identifier, TokenType type, TokenType assignment, TokenType semi_colon,
            float value) {
        this.identifier = identifier;
        this.type = type;
        this.assignment = assignment;
        this.semi_colon = semi_colon;
        this.value = value;
    }

    public MethodVariableNode(String identifier, TokenType type, TokenType assignment, TokenType semi_colon,
            int value) {
        this.identifier = identifier;
        this.type = type;
        this.assignment = assignment;
        this.semi_colon = semi_colon;
        this.value = value;
    }

    public MethodVariableNode(String identifier, TokenType type, TokenType assignment, TokenType semi_colon,
            double value) {
        this.identifier = identifier;
        this.type = type;
        this.assignment = assignment;
        this.semi_colon = semi_colon;
        this.value = value;
    }

    public MethodVariableNode(String identifier, TokenType type, TokenType assignment, TokenType semi_colon,
            String value) {
        this.identifier = identifier;
        this.type = type;
        this.assignment = assignment;
        this.semi_colon = semi_colon;
        this.value = value;
    }

    public MethodVariableNode(String identifier, TokenType type, TokenType assignment, TokenType semi_colon,
            boolean value) {
        this.identifier = identifier;
        this.type = type;
        this.assignment = assignment;
        this.semi_colon = semi_colon;
        this.value = value;
    }

    public TokenType getType() {
        return this.type;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setValue(boolean value) {
        this.value = value;

    }
}
