package Lexer;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Define token types
enum TokenType {
    KEYWORD, IDENTIFIER, PUNCTUATION, PRIMITIVE_TYPE, COMPOSITE_TYPE,
}

// Define token class
class Token {

    TokenType type;
    String value;

    Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }
}

class Analysis {

    private static final Pattern[] PATTERNS = {
            Pattern.compile("^(pub|prot|stat|cls|singular|multiple)$"),
            Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*$"),
            Pattern.compile("^[{}(),.;=:]"),
            Pattern.compile("^\\d+$"),
            Pattern.compile("^\"[^\"]*\"$"),
            Pattern.compile("^::?$"), // Type declaration
            Pattern.compile("^\\+{1,2}$"), // Addition operator (+ or ++)
            Pattern.compile("^-{1,2}$"), // Subtraction operator (- or --)
            Pattern.compile("^\\*$"), // Multiplication operator (*)
            Pattern.compile("^\\*{2}$"), // Exponentiation operator (**)
            Pattern.compile("^/$"), // Division operator (/)
            Pattern.compile("^\\*{2}/$"), // Square root operator (/**)
            Pattern.compile("^%$"), // Modulus operator (%)
            Pattern.compile("^\\+=|-=|\\*=|\\*\\*=|/=|/\\*=|%/$"),
    };

    static List<Token> tokenize(String sourceCode) {
        List<Token> tokens = new ArrayList<>();
        int currentPosition = 0;
        while (currentPosition < sourceCode.length()) {
            boolean matched = false;

            for (int i = 0; i < PATTERNS.length; i++) {
                Matcher matcher = PATTERNS[i].matcher(sourceCode.substring(currentPosition));
                if (matcher.find()) {
                    String value = matcher.group(0);
                    TokenType tokenType = getTokenTypeForPatternIndex(i);
                    tokens.add(new Token(tokenType, value));
                    currentPosition += value.length();
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                // Handle unrecognized characters or tokens
                throw new RuntimeException("Unexpected character at position " + currentPosition);
            }
        }

        return tokens;
    }

    private static TokenType getTokenTypeForPatternIndex(int index) {
        switch (index) {
            case 0:
                return TokenType.KEYWORD;
            case 1:
                return TokenType.IDENTIFIER;
            case 2:
                return TokenType.PUNCTUATION;
            case 3:
                return TokenType.COMPOSITE_TYPE;
            // Add cases for other token types as needed
            default:
                throw new IllegalArgumentException("Invalid pattern index: " + index);
        }
    }
}

// Main class for testing
public class Main {

    public static void main(String[] args) {
        String sourceCode = "pub cls Main {stat main<void>() {console.out(\"hello world\");}}";

        List<Token> tokens = Analysis.tokenize(sourceCode);
        for (Token token : tokens) {
            System.out.println(token.type + ": " + token.value);
        }
    }
}
