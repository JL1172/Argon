package main.Lexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Parser.ParserMain;

class Lexer {

    // this wont be static because i am using an instance of this class
    // this wont be public because i want it to be package private

    List<LexerToken> tokenizer(String input) {
        List<LexerToken> tokens = new ArrayList<>();

        int currentPosition = 0;
        int inputLength = input.length();
        int patternLength = LexerMain.PATTERNS.length;

        // looping through the code
        while (currentPosition < inputLength) {
            boolean match = false;

            for (int i = 0; i < patternLength; i++) {
                // now i need to match the patterns and then create tokens
                Pattern currentPattern = LexerMain.PATTERNS[i]; // grab the pattern at index i
                Matcher currentMatch = currentPattern.matcher(input.substring(currentPosition));
                if (currentMatch.lookingAt()) {
                    // now i am verifying match
                    match = true;
                    String tokenValue = currentMatch.group();
                    // i am then grabbing the value of the matched pattern which is 1:1 with the
                    // compiled regex patterns in the PATTERNS field
                    LexerMain.TokenType tokenType = LexerMain.TokenType.values()[i];
                    tokens.add(new LexerToken(tokenType, tokenValue));
                    currentPosition += tokenValue.length();
                    break;
                }
            }
            if (!match)
                currentPosition++;
        }
        return tokens;
    }
}

public class LexerMain {
    public static enum TokenType {

        // keywords
        CLS, // short for class
        PUB, // short for public (access modifier)
        PROT, // short for protected (access modifier)
        RESERVED, // aka private
        STAT, // short for static
        VOID, // used if a method returns nothing
        THIS, // used as "this"
        NEW, // used as new
        CONST,

        // singular and multiple methods
        THIS_METHOD,

        // type names
        NULL_IDENTIFIER,
        INT_IDENTIFIER,
        FLOAT_IDENTIFIER,
        DOUBLE_IDENTIFIER,
        BOOLEAN_IDENTIFIER,
        STRING_IDENTIFIER,

        // output statement
        CONSOLE_OUT,

        // primitive types
        NUMERIC_TYPE,
        STRING_TYPE, // anything surrounded by single or double quotes
        BOOLEAN_TYPE, // true or false,
        // identifier for name of method or class or property
        IDENTIFIER,


        // assignment operators
        ASSIGNMENT, // =
        DIVIDED_EQUALS_SIGN, // /=
        MULTIPLIED_EQUALS_SIGN, // *=
        EXPONENTIATE_EQUALS_SIGN, // **=
        MODULUS_EQUALS_SIGN, // %=
        PLUS_EQUALS_SIGN, // +=
        MINUS_EQUALS_SIGN, // -=
        INCREMENT_EQUALS_SIGN, // ++=
        DECREMENT_EQUALS_SIGN, // --=

        // operators
        PLUS, // +
        INCREMENT, // ++
        MINUS, // -
        DECREMENT, // --
        MODULUS, // %
        DIVISION, // /
        MULTIPLICATION, // *
        EXPONENTIATE, // **
        SQUARE_ROOT, // /**

        // equality assignment
        STRICTLY_EQUALS, // ===
        STRICTLY_NOT_EQUALS, // !==
        AND, // &&
        OR, // ||
        NOT, // !
        GREATER_THAN, // >
        LESS_THAN, // <
        GREATER_THAN_OR_EQUAL_TO, // >=
        LESS_THAN_OR_EQUAL_TO, // <=,

        // punctuation
        SEMICOLON, // ;
        DOUBLE_COLON, // ::
        COLON, // :
        LPAREN, // (
        RPAREN, // )
        LBRACE, // {
        RBRACE, // }
        LBRACKET, // [
        RBRACKET, // ]
        COMMA, // ,
        DOLLAR_SIGN, // $
        BACK_TICK, // `
        QUOTE, // "
        SINGLE_QUOTE, // '
        DOT, // .

        // composite data types
        STATIC_ARRAY, // instantiated exactly like this: myArrayList::ArrayS[string] = new ArrayS(5);
                      // or: myArrayList::ArrayS[string] = ["hello", "jacob"];
        DYNAMIC_ARRAY, // instantiated like: myArrayList::ArrayD[string] = new ArrayD(); or:
                       // myArrayList::ArrayD[string] = ["jacob", "jacob"];
        // OBJECT, // instantiated like: myObj::Object[string,number] = {};
        // STACK,
        // LINKED_LIST,
        // HASH_MAP,
        // QUEUE,
        // SET,
        // GRAPH,
        // TREE,
        // PRIORITY_QUEUE,
        // REGEX,

        EOF,
    }

    static final Pattern[] PATTERNS = {
            Pattern.compile("cls"),
            Pattern.compile("pub"),
            Pattern.compile("prot"),
            Pattern.compile("reserved"),
            Pattern.compile("stat"),
            Pattern.compile("void"),
            Pattern.compile("this"),
            Pattern.compile("new"),
            Pattern.compile("const"),

            // this (same as constructor) methods
            Pattern.compile("@this"),

            // type indentifiers
            Pattern.compile("null"),
            Pattern.compile("int"), // -2147483648 -> 2147483647
            Pattern.compile("flt"), // -3.4E-38 to 3.4E+38
            Pattern.compile("dbl"), // 4.94065645841246544e-324 to 1.79769313486231570e+308
            Pattern.compile("boolean"),
            Pattern.compile("string"),

            Pattern.compile("cout\\((.*?)\\)"),

            Pattern.compile("-?\\d+(\\.\\d+)?([eE][-+]?\\d+)?"), // number identifier
            Pattern.compile("\"(?:\\\\\"|[^\"])*\"|'(?:\\\\'|[^'])*'"), // String
            Pattern.compile("true|false"), // Boolean
            
            Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*"), // Identifier


            Pattern.compile("="), // Assignment
            Pattern.compile("/="), // Divided equals sign
            Pattern.compile("\\*="), // Multiplied equals sign
            Pattern.compile("\\*\\*="), // Exponentiate equals sign
            Pattern.compile("%="), // Modulus equals sign
            Pattern.compile("\\+="), // Plus equals sign
            Pattern.compile("-="), // Minus equals sign
            Pattern.compile("\\++="), // Increment equals sign
            Pattern.compile("--="), // Decrement equals sign

            Pattern.compile("\\+"), // Plus
            Pattern.compile("\\+\\+"), // Increment
            Pattern.compile("-"), // Minus
            Pattern.compile("--"), // Decrement
            Pattern.compile("%"), // Modulus
            Pattern.compile("/"), // Division
            Pattern.compile("\\*"), // Multiplication
            Pattern.compile("\\*\\*"), // Exponentiate
            Pattern.compile("\\/\\*\\*"), // Square root

            Pattern.compile("==="), // Strictly equals
            Pattern.compile("!=="), // Strictly not equals
            Pattern.compile("&&"), // AND
            Pattern.compile("\\|\\|"), // OR
            Pattern.compile("!"), // NOT
            Pattern.compile(">"), // Greater than
            Pattern.compile("<"), // Less than
            Pattern.compile(">="), // Greater than or equal to
            Pattern.compile("<="), // Less than or equal to

            Pattern.compile(";"), // Semicolon
            Pattern.compile("::"), // Double colon
            Pattern.compile(":"), // Colon
            Pattern.compile("\\("), // Left parenthesis
            Pattern.compile("\\)"), // Right parenthesis
            Pattern.compile("\\{"), // Left brace
            Pattern.compile("\\}"), // Right brace
            Pattern.compile("\\["), // Left bracket
            Pattern.compile("\\]"), // Right bracket
            Pattern.compile(","), // Comma
            Pattern.compile("\\$"), // Dollar sign
            Pattern.compile("`"), // Back tick
            Pattern.compile("\""), // Quote
            Pattern.compile("'"), // Single quote
            Pattern.compile("\\."),
            Pattern.compile(
                    "[a-zA-Z_][a-zA-Z0-9_]*::ArrayS\\\\[.*?\\\\]\\\\s*=\\\\s*new\\\\s+ArrayS\\\\(.*?\\\\);|[a-zA-Z_][a-zA-Z0-9_]*::ArrayS\\\\[.*?\\\\]\\\\s*=\\\\s*\\\\[.*?\\\\];"),
            Pattern.compile(
                    "[a-zA-Z_][a-zA-Z0-9_]*::ArrayD\\[.*?\\]\\s*=\\s*new\\s+ArrayD\\(\\);|[a-zA-Z_][a-zA-Z0-9_]*::ArrayD\\[.*?\\]\\s*=\\s*\\[.*?\\];")
    };

    private static String readSourceCodeFromFile(String filename) {
        StringBuilder sourceCodeBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sourceCodeBuilder.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sourceCodeBuilder.toString();
    }

    public static void main(String[] args) {
        String source_code = LexerMain.readSourceCodeFromFile("/media/jacoblang11/working-dir/0-personal-work/dev/Argon/src/main/Lexer/source-code.ar");
        Lexer lex = new Lexer();
        List<LexerToken> tokens = lex.tokenizer(source_code);
        ParserMain parser = new ParserMain(tokens);
        parser.parseClass();
    }
}
