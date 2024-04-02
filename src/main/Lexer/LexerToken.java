package main.Lexer;

public class LexerToken {

    // private final because encapsulation
    private final LexerMain.TokenType type;
    private final String value;

    LexerToken(LexerMain.TokenType type, String value) {
        this.value = value;
        this.type = type;
    }

    String getValue() {
        return value;
    }

    LexerMain.TokenType getType() {
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
