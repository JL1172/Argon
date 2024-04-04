package main.IntermediateASTNodes;

import main.Lexer.LexerMain.TokenType;

public class MethodParamObjectNode {
    public Object parameter;
    public TokenType parameterType;

    public MethodParamObjectNode(TokenType parameterType, int parameter) {
        this.parameter = parameter;
        this.parameterType = parameterType;
    }

    public MethodParamObjectNode(TokenType parameterType, float parameter) {
        this.parameter = parameter;
        this.parameterType = parameterType;
    }

    public MethodParamObjectNode(TokenType parameterType, double parameter) {
        this.parameter = parameter;
        this.parameterType = parameterType;
    }

    public MethodParamObjectNode(TokenType parameterType, boolean parameter) {
        this.parameter = parameter;
        this.parameterType = parameterType;
    }

    public MethodParamObjectNode(TokenType parameterType, String parameter) {
        this.parameter = parameter;
        this.parameterType = parameterType;
    }

    public void clear() {
        this.parameter = null;
        this.parameterType = null;
    }

    @Override
    public String toString() {
        return "\nBody: [\n" + "parameter: " + this.parameter + "\n" + "parameter type: " + this.parameterType
                + " ]";
    }
}
