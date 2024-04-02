package main.Lexer;

public class LexerToken {

    // private final because encapsulation
    private final LexerMain.TokenType type;
    private final String value;

    public LexerToken(LexerMain.TokenType type, String value) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public LexerMain.TokenType getType() {
        return type;
    }

    // overriding the toString() method to be concatenation of type and value;
    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' + '}';
    }
}
