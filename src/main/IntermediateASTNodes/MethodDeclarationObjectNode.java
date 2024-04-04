package main.IntermediateASTNodes;

import main.Lexer.LexerMain.TokenType;

public class MethodDeclarationObjectNode extends ObjectNodeTemplate {
    /*
     * DTO
     * 
     * {type: fieldName, accessModifier: pub || "none" if blank, keywords: static ||
     * "none", immutability_keyword: const || "none", type: one, assignment: "=",
     * value: "value"}
     * 
     */
    public MethodDeclarationObjectNode(NodeTypeEnum nodeType, TokenType accessModifier, TokenType keyword,
            TokenType immutability_keyword, TokenType type, TokenType assignment, String value, String identifier,
            TokenType type_annotations) {
        super(nodeType, accessModifier, keyword, immutability_keyword, type, assignment, value, identifier,
                type_annotations);
    }

    public void returnMethodDeclarationValues() {
        TokenType[] values = { accessModifier, keyword, immutability_keyword, type, assignment, type_annotations };
        for (TokenType value : values) {
            System.out.println(value);
        }
        System.out.println(nodeType);
        System.out.println(identifier);
        System.out.println(value);
    }

    // overriding the toString() method to be concatenation of type and value;
    @Override
    public String toString() {
        return "Node {\n" +
                "nodeType='" + nodeType + "'," + '\n' +
                "access modifier='" + accessModifier + "'," + '\n' +
                "keyword='" + keyword + "'," + '\n' +
                "immutability keyword='" + immutability_keyword + "'," + '\n' +
                "identifier='" + identifier + "'," + '\n' +
                "type_annotation='" + type_annotations + "'," + '\n' +
                "type='" + type + "'," + '\n' +
                "assignment='" + assignment + "'," + '\n' +
                "value='" + value + "'," + '\n' +
                accessModifier + " " + keyword + " " + immutability_keyword + " " + identifier + " "
                + type_annotations + " " + type + " " + assignment + " " + value + " "
                + '}';
    }
}
