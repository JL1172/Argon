package main.IntermediateASTNodes;

import main.Lexer.LexerMain.TokenType;

public class TemporalObjectNode extends ObjectNodeTemplate {
    public TemporalObjectNode(NodeTypeEnum nodeType, TokenType accessModifier, TokenType keyword,
            TokenType immutability_keyword, TokenType type, TokenType assignment, String value, String identifier, TokenType type_annotations) {
        super(nodeType, accessModifier, keyword, immutability_keyword, type, assignment, value, identifier, type_annotations);
    }
}
