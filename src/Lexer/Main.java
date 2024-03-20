
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Token {
    private final Main.TokenType type;
    private final String value;

    Token(Main.TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    String getValue() {
        return value;
    }

    Main.TokenType getType() {
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

class Lexer {

    List<Token> tokenizer(String input) {

        List<Token> tokens = new ArrayList<>();

        // current position for input position tracker (index for input)
        int currentPosition = 0;
        int patternLength = Main.PATTERNS.length;
        int inputLength = input.length();

        while (currentPosition < inputLength) {
            boolean match = false;
            for (int i = 0; i < patternLength; i++) {
                Pattern currentPattern = Main.PATTERNS[i];
                Matcher currentMatcher = currentPattern.matcher(input.substring(currentPosition));
                if (currentMatcher.lookingAt()) {
                    match = true;
                    String tokenValue = currentMatcher.group();
                    Main.TokenType tokenType = Main.TokenType.values()[i];
                    tokens.add(new Token(tokenType, tokenValue));
                    currentPosition += tokenValue.length();
                    break;
                }
            }
            if (!match) {
                currentPosition++;
            }
        }
        return tokens;
    }
}

public class Main {
    static enum TokenType {

        // keywords
        CLS, // short for class
        PUB, // short for public (access modifier)
        PROT, // short for protected (access modifier)
        STAT, // short for static
        VOID, // used if a method returns nothing
        THIS, // used as "this"

        // output statement
        CONSOLE_OUT,

        // identifier for name of method or class or property
        IDENTIFIER,

        // primitive types
        NUMBER, // double floating point precision number (javascript's default number)
        STRING, // anything surrounded by single or double quotes
        BOOLEAN, // true or false,
        DATE, // same as javascript's date

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
        SEMICOLON,
        DOUBLE_COLON,
        COLON,
        LPAREN,
        RPAREN,
        LBRACE,
        RBRACE,
        LBRACKET,
        RBRACKET,
        COMMA,
        DOLLAR_SIGN,
        BACK_TICK,
        QUOTE,
        SINGLE_QUOTE,
        DOT,

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
            Pattern.compile("stat"),
            Pattern.compile("void"),
            Pattern.compile("this"),

            Pattern.compile("console\\.out\\((.*?)\\)"),
            Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*"), // Identifier

            Pattern.compile("-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?"), // Number
            Pattern.compile("\"(?:\\\\\"|[^\"])*\"|'(?:\\\\'|[^'])*'"), // String
            Pattern.compile("true|false"), // Boolean
            Pattern.compile("\\d{2}/\\d{2}/\\d{4}"), // Date

            Pattern.compile("\\+"), // Plus
            Pattern.compile("\\+\\+"), // Increment
            Pattern.compile("-"), // Minus
            Pattern.compile("--"), // Decrement
            Pattern.compile("%"), // Modulus
            Pattern.compile("/"), // Division
            Pattern.compile("\\*"), // Multiplication
            Pattern.compile("\\*\\*"), // Exponentiate
            Pattern.compile("\\/\\*\\*"), // Square root

            Pattern.compile("="), // Assignment
            Pattern.compile("/="), // Divided equals sign
            Pattern.compile("\\*="), // Multiplied equals sign
            Pattern.compile("\\*\\*="), // Exponentiate equals sign
            Pattern.compile("%="), // Modulus equals sign
            Pattern.compile("\\+="), // Plus equals sign
            Pattern.compile("-="), // Minus equals sign
            Pattern.compile("\\++="), // Increment equals sign
            Pattern.compile("--="), // Decrement equals sign

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

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String source_code = "pub cls MainClass {stat mainClassMethod::void() {console.out(\"hello world\");}}";
        List<Token> tokens = lex.tokenizer(source_code);
        System.out.println(tokens);
    }
}

/*
 * [Token{type=PUB, value='pub'},
 * Token{type=CLS, value='cls'},
 * Token{type=IDENTIFIER, value='MainClass'},
 * Token{type=LBRACE, value='{'},
 * Token{type=STAT, value='stat'},
 * Token{type=IDENTIFIER, value='mainClassMethod'},
 * Token{type=DOUBLE_COLON, value='::'},
 * Token{type=VOID, value='void'},
 * Token{type=LPAREN, value='('},
 * Token{type=RPAREN, value=')'},
 * Token{type=LBRACE, value='{'},
 * Token{type=CONSOLE_OUT, value='console.out("hello world")'},
 * Token{type=SEMICOLON, value=';'},
 * Token{type=RBRACE, value='}'},
 * Token{type=RBRACE, value='}'}]
 */