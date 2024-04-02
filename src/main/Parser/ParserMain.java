package main.Parser;

import main.Lexer.LexerToken;
import java.util.List;

public class ParserMain {
    private List<LexerToken> tokenList;

    public ParserMain(List<LexerToken> tokens) {
        System.out.println(tokens);
        this.tokenList = tokens;
    }

    public List<LexerToken> getTokenList() {
        return tokenList;
    }

    public static void main(String[] args) {
        System.out.println("hello world from parse");
    }
}
