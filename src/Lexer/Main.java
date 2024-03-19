package Lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum TokenType {
    IDENTIFIER, PUNCTUATION, KEYWORD, COMPOSITE_TYPE, PRIMITIVE_TYPE
}

class Token {
    TokenType token;
    String value;

    Token(TokenType token, String value) {
        this.token = token;
        this.value = value;
    }
}

class Lexer {
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
                    tokens.add(new Token(TokenType.values()[i], value));
                    currentPosition += value.length();
                    matched = true;
                    break;
                }
            } 
            if (!matched) {
                throw new RuntimeException("Unexpected character at position " + currentPosition);
            }
        }

        return tokens;
    }
}

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java Main <file_content>");
            System.exit(1);
        }
        String fileContent = args[0];
        System.out.println("File content");
    }
}