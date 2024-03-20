
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Token {
    private final Main.TokenType type;
    private final String value;

    // setting those above values
    Token(Main.TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    // getter function
    Main.TokenType getType() {
        return type;
    }

    // getter function
    String getValue() {
        return value;
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
    List<Token> tokenize(String input) {

        // this is the token list ill be returning, its just a dyanamic array with class
        // Token as its type
        List<Token> tokens = new ArrayList<>();
        int position = 0;

        // grabbing length of input and patterns so it doesnt have to recompute each
        // iteration
        int patternLength = Main.PATTERNS.length;
        int inputLength = input.length();
        // going to instantiate a while loop that runs as long as position is not
        // greater than the length of input
        // this is going to have a match variable that will ensure extraneous iterations
        // do not happen
        // then a for look looping through the pattern and javas stupid matching
        // functionality will do the rest

        while (position < inputLength) {
            boolean match = false;
            for (int i = 0; i < patternLength; i++) {
                Pattern currentPattern = Main.PATTERNS[i];
                Matcher currentMatcher = currentPattern.matcher(input.substring(position));
                if (currentMatcher.lookingAt()) {
                    match = true;
                    String tokenValue = currentMatcher.group();
                    Main.TokenType tokenType = Main.TokenType.values()[i];
                    tokens.add(new Token(tokenType, tokenValue));
                    position += tokenValue.length();
                    break;
                }
            }
            if (!match) {
                position++;
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
        COLON,
        DOUBLE_COLON,
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

        // output statement
        CONSOLE_OUT,

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
            Pattern.compile(":"), // Colon
            Pattern.compile("::"), // Double colon
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
            Pattern.compile("."),
            Pattern.compile("console\\\\.out\\\\((.*?)\\\\)"),
            Pattern.compile(
                    "[a-zA-Z_][a-zA-Z0-9_]*::ArrayS\\\\[.*?\\\\]\\\\s*=\\\\s*new\\\\s+ArrayS\\\\(.*?\\\\);|[a-zA-Z_][a-zA-Z0-9_]*::ArrayS\\\\[.*?\\\\]\\\\s*=\\\\s*\\\\[.*?\\\\];"),
            Pattern.compile(
                    "[a-zA-Z_][a-zA-Z0-9_]*::ArrayD\\[.*?\\]\\s*=\\s*new\\s+ArrayD\\(\\);|[a-zA-Z_][a-zA-Z0-9_]*::ArrayD\\[.*?\\]\\s*=\\s*\\[.*?\\];")
    };

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String source_code = "pub cls MainClass {stat mainClassMethod::void() {console.out(\"hello world\");}}";
        List<Token> tokens = lex.tokenize(source_code);
        System.out.println(tokens);
    }
}
