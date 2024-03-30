
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//util class
class Token {

    // private final because encapsulation
    private final Main.TokenType type;
    private final String value;

    Token(Main.TokenType type, String value) {
        this.value = value;
        this.type = type;
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

    // this wont be static because i am using an instance of this class
    // this wont be public because i want it to be package private

    List<Token> tokenizer(String input) {
        List<Token> tokens = new ArrayList<>();

        int currentPosition = 0;
        int inputLength = input.length();
        int patternLength = Main.PATTERNS.length;

        // looping through the code
        while (currentPosition < inputLength) {
            boolean match = false;

            for (int i = 0; i < patternLength; i++) {
                // now i need to match the patterns and then create tokens
                Pattern currentPattern = Main.PATTERNS[i]; // grab the pattern at index i
                Matcher currentMatch = currentPattern.matcher(input.substring(currentPosition));
                if (currentMatch.lookingAt()) {
                    // now i am verifying match
                    match = true;
                    String tokenValue = currentMatch.group();
                    // i am then grabbing the value of the matched pattern which is 1:1 with the
                    // compiled regex patterns in the PATTERNS field
                    Main.TokenType tokenType = Main.TokenType.values()[i];
                    tokens.add(new Token(tokenType, tokenValue));
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

public class Main {
    static enum TokenType {

        // keywords
        CLS, // short for class
        PUB, // short for public (access modifier)
        PROT, // short for protected (access modifier)
        RESERVED, // aka private
        STAT, // short for static
        VOID, // used if a method returns nothing
        THIS, // used as "this"
        NEW, // used as new

        // singular and multiple methods
        THIS_METHOD,
        // type names
        NULL_IDENTIFIER,
        DATE_IDENTIFIER,
        NUMBER_IDENTIFIER,
        BOOLEAN_IDENTIFIER,
        STRING_IDENTIFIER,

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
            Pattern.compile("reserved"),
            Pattern.compile("stat"),
            Pattern.compile("void"),
            Pattern.compile("this"),
            Pattern.compile("new"),

            // this (same as constructor) methods
            Pattern.compile("@this"),

            // type indentifiers
            Pattern.compile("null"),
            Pattern.compile("Date"),
            Pattern.compile("number"),
            Pattern.compile("boolean"),
            Pattern.compile("string"),

            Pattern.compile("cout\\((.*?)\\)"),
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
        String source_code = "class SecondaryClass { reserved dob::Date; reserved age::number; @this(age::number,dob::Date) { this.age = age; this.dob = dob; } addYearToAge(age::number) :: void { this.age += age; } changeDob(date::Date) :: void { this.dob = date; } pub viewDob() :: Date { return this.dob; } } pub class Main { stat mainMethod() :: void { secondary::SecondaryClass = new SecondaryClass(22, 07/18/01); secondInstanceOfSecondary::SecondaryClass = new SecondaryClass(22, 11/08/2001); name::string=\"jacob lang\"; cout(name); cout(secondary.age); secondary.addYearToAge(1); cout(secondary.age); secondary.changeDob(\"07/18/2001\"); cout(secondary.viewDob); } }";
        List<Token> tokens = lex.tokenizer(source_code);
        System.out.println(tokens);
    }
}

/*
 * Token{type=IDENTIFIER, value='class'}, Token{type=IDENTIFIER,
 * value='SecondaryClass'}, Token{type=LBRACE, value='{'}, Token{type=RESERVED,
 * value='reserved'}, Token{type=IDENTIFIER, value='dob'},
 * Token{type=DOUBLE_COLON, value='::'}, Token{type=DATE_IDENTIFIER,
 * value='Date'}, Token{type=SEMICOLON, value=';'}, Token{type=RESERVED,
 * value='reserved'}, Token{type=IDENTIFIER, value='age'},
 * Token{type=DOUBLE_COLON, value='::'}, Token{type=NUMBER_IDENTIFIER,
 * value='number'}, Token{type=SEMICOLON, value=';'}, Token{type=THIS_METHOD,
 * value='@this'}, Token{type=LPAREN, value='('}, Token{type=IDENTIFIER,
 * value='age'}, Token{type=DOUBLE_COLON, value='::'},
 * Token{type=NUMBER_IDENTIFIER, value='number'}, Token{type=COMMA, value=','},
 * Token{type=IDENTIFIER, value='dob'}, Token{type=DOUBLE_COLON, value='::'},
 * Token{type=DATE_IDENTIFIER, value='Date'}, Token{type=RPAREN, value=')'},
 * Token{type=LBRACE, value='{'}, Token{type=THIS, value='this'},
 * Token{type=DOT, value='.'}, Token{type=IDENTIFIER, value='age'},
 * Token{type=ASSIGNMENT, value='='}, Token{type=IDENTIFIER, value='age'},
 * Token{type=SEMICOLON, value=';'}, Token{type=THIS, value='this'},
 * Token{type=DOT, value='.'}, Token{type=IDENTIFIER, value='dob'},
 * Token{type=ASSIGNMENT, value='='}, Token{type=IDENTIFIER, value='dob'},
 * Token{type=SEMICOLON, value=';'}, Token{type=RBRACE, value='}'},
 * Token{type=IDENTIFIER, value='addYearToAge'}, Token{type=LPAREN, value='('},
 * Token{type=IDENTIFIER, value='age'}, Token{type=DOUBLE_COLON, value='::'},
 * Token{type=NUMBER_IDENTIFIER, value='number'}, Token{type=RPAREN, value=')'},
 * Token{type=DOUBLE_COLON, value='::'}, Token{type=VOID, value='void'},
 * Token{type=LBRACE, value='{'}, Token{type=THIS, value='this'},
 * Token{type=DOT, value='.'}, Token{type=IDENTIFIER, value='age'},
 * Token{type=PLUS, value='+'}, Token{type=ASSIGNMENT, value='='},
 * Token{type=IDENTIFIER, value='age'}, Token{type=SEMICOLON, value=';'},
 * Token{type=RBRACE, value='}'}, Token{type=IDENTIFIER, value='changeDob'},
 * Token{type=LPAREN, value='('}, Token{type=IDENTIFIER, value='date'},
 * Token{type=DOUBLE_COLON, value='::'}, Token{type=DATE_IDENTIFIER,
 * value='Date'}, Token{type=RPAREN, value=')'}, Token{type=DOUBLE_COLON,
 * value='::'}, Token{type=VOID, value='void'}, Token{type=LBRACE, value='{'},
 * Token{type=THIS, value='this'}, Token{type=DOT, value='.'},
 * Token{type=IDENTIFIER, value='dob'}, Token{type=ASSIGNMENT, value='='},
 * Token{type=IDENTIFIER, value='date'}, Token{type=SEMICOLON, value=';'},
 * Token{type=RBRACE, value='}'}, Token{type=PUB, value='pub'},
 * Token{type=IDENTIFIER, value='viewDob'}, Token{type=LPAREN, value='('},
 * Token{type=RPAREN, value=')'}, Token{type=DOUBLE_COLON, value='::'},
 * Token{type=DATE_IDENTIFIER, value='Date'}, Token{type=LBRACE, value='{'},
 * Token{type=IDENTIFIER, value='return'}, Token{type=THIS, value='this'},
 * Token{type=DOT, value='.'}, Token{type=IDENTIFIER, value='dob'},
 * Token{type=SEMICOLON, value=';'}, Token{type=RBRACE, value='}'},
 * Token{type=RBRACE, value='}'}, Token{type=PUB, value='pub'},
 * Token{type=IDENTIFIER, value='class'}, Token{type=IDENTIFIER, value='Main'},
 * Token{type=LBRACE, value='{'}, Token{type=STAT, value='stat'},
 * Token{type=IDENTIFIER, value='mainMethod'}, Token{type=LPAREN, value='('},
 * Token{type=RPAREN, value=')'}, Token{type=DOUBLE_COLON, value='::'},
 * Token{type=VOID, value='void'}, Token{type=LBRACE, value='{'},
 * Token{type=IDENTIFIER, value='secondary'}, Token{type=DOUBLE_COLON,
 * value='::'}, Token{type=IDENTIFIER, value='SecondaryClass'},
 * Token{type=ASSIGNMENT, value='='}, Token{type=NEW, value='new'},
 * Token{type=IDENTIFIER, value='SecondaryClass'}, Token{type=LPAREN,
 * value='('}, Token{type=NUMBER, value='22'}, Token{type=COMMA, value=','},
 * Token{type=NUMBER, value='0'}, Token{type=NUMBER, value='7'},
 * Token{type=DIVISION, value='/'}, Token{type=NUMBER, value='18'},
 * Token{type=DIVISION, value='/'}, Token{type=NUMBER, value='0'},
 * Token{type=NUMBER, value='1'}, Token{type=RPAREN, value=')'},
 * Token{type=SEMICOLON, value=';'}, Token{type=IDENTIFIER,
 * value='secondInstanceOfSecondary'}, Token{type=DOUBLE_COLON, value='::'},
 * Token{type=IDENTIFIER, value='SecondaryClass'}, Token{type=ASSIGNMENT,
 * value='='}, Token{type=NEW, value='new'}, Token{type=IDENTIFIER,
 * value='SecondaryClass'}, Token{type=LPAREN, value='('}, Token{type=NUMBER,
 * value='22'}, Token{type=COMMA, value=','}, Token{type=NUMBER, value='11'},
 * Token{type=DIVISION, value='/'}, Token{type=NUMBER, value='0'},
 * Token{type=NUMBER, value='8'}, Token{type=DIVISION, value='/'},
 * Token{type=NUMBER, value='2001'}, Token{type=RPAREN, value=')'},
 * Token{type=SEMICOLON, value=';'}, Token{type=IDENTIFIER, value='name'},
 * Token{type=DOUBLE_COLON, value='::'}, Token{type=STRING_IDENTIFIER,
 * value='string'}, Token{type=ASSIGNMENT, value='='}, Token{type=STRING,
 * value='"jacob lang"'}, Token{type=SEMICOLON, value=';'},
 * Token{type=CONSOLE_OUT, value='cout(name)'}, Token{type=SEMICOLON,
 * value=';'}, Token{type=CONSOLE_OUT, value='cout(secondary.age)'},
 * Token{type=SEMICOLON, value=';'}, Token{type=IDENTIFIER, value='secondary'},
 * Token{type=DOT, value='.'}, Token{type=IDENTIFIER, value='addYearToAge'},
 * Token{type=LPAREN, value='('}, Token{type=NUMBER, value='1'},
 * Token{type=RPAREN, value=')'}, Token{type=SEMICOLON, value=';'},
 * Token{type=CONSOLE_OUT, value='cout(secondary.age)'}, Token{type=SEMICOLON,
 * value=';'}, Token{type=IDENTIFIER, value='secondary'}, Token{type=DOT,
 * value='.'}, Token{type=IDENTIFIER, value='changeDob'}, Token{type=LPAREN,
 * value='('}, Token{type=STRING, value='"07/18/2001"'}, Token{type=RPAREN,
 * value=')'}, Token{type=SEMICOLON, value=';'}, Token{type=CONSOLE_OUT,
 * value='cout(secondary.viewDob)'}, Token{type=SEMICOLON, value=';'},
 * Token{type=RBRACE, value='}'}, Token{type=RBRACE, value='}'}]
 * 
 */