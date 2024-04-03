package main.IntermediateASTNodes;

import main.Lexer.LexerMain.TokenType;

public class FieldNameObjectNode extends ObjectNodeTemplate {
    /*
     * DTO
     * 
     * {type: fieldName, accessModifier: pub || "none" if blank, keywords: static ||
     * "none", immutability_keyword: const || "none", type: one, assignment: "=",
     * value: "value"}
     * 
     */
    public TokenType semi_colon;
    public boolean is_assigned_a_value;

    public FieldNameObjectNode(NodeTypeEnum nodeType, TokenType accessModifier, TokenType keyword,
            TokenType immutability_keyword, TokenType type, TokenType assignment, String value, String identifier,
            TokenType type_annotations, TokenType semi_colon, boolean is_assigned_a_value) {
        super(nodeType, accessModifier, keyword, immutability_keyword, type, assignment, value, identifier,
                type_annotations);
        this.semi_colon = semi_colon;
        this.is_assigned_a_value = is_assigned_a_value;
    }

    public FieldNameObjectNode(NodeTypeEnum nodeType, TokenType accessModifier, TokenType keyword,
            TokenType immutability_keyword, TokenType type, TokenType assignment, int value, String identifier,
            TokenType type_annotations, TokenType semi_colon, boolean is_assigned_a_value) {
        super(nodeType, accessModifier, keyword, immutability_keyword, type, assignment, value, identifier,
                type_annotations);
        this.semi_colon = semi_colon;
        this.is_assigned_a_value = is_assigned_a_value;
    }

    public FieldNameObjectNode(NodeTypeEnum nodeType, TokenType accessModifier, TokenType keyword,
            TokenType immutability_keyword, TokenType type, TokenType assignment, float value, String identifier,
            TokenType type_annotations, TokenType semi_colon, boolean is_assigned_a_value) {
        super(nodeType, accessModifier, keyword, immutability_keyword, type, assignment, value, identifier,
                type_annotations);
        this.semi_colon = semi_colon;
        this.is_assigned_a_value = is_assigned_a_value;
    }

    public FieldNameObjectNode(NodeTypeEnum nodeType, TokenType accessModifier, TokenType keyword,
            TokenType immutability_keyword, TokenType type, TokenType assignment, double value, String identifier,
            TokenType type_annotations, TokenType semi_colon, boolean is_assigned_a_value) {
        super(value, nodeType, accessModifier, keyword, immutability_keyword, type, assignment, identifier,
                type_annotations);
        this.semi_colon = semi_colon;
        this.is_assigned_a_value = is_assigned_a_value;
    }

    public FieldNameObjectNode(NodeTypeEnum nodeType, TokenType accessModifier,
            TokenType keyword,
            TokenType immutability_keyword, TokenType type, TokenType assignment, boolean value, String identifier,
            TokenType type_annotations, TokenType semi_colon, boolean is_assigned_a_value) {
        super(value, nodeType, accessModifier, keyword, immutability_keyword, type,
                assignment, identifier,
                type_annotations);
        this.semi_colon = semi_colon;
        this.is_assigned_a_value = is_assigned_a_value;
    }

    public void returnMethodDeclarationValues() {
        // TokenType[] values = { accessModifier, keyword, immutability_keyword, type, assignment, type_annotations };
        System.out.print("node type :");
        System.out.println(nodeType);
        System.out.print("access modifier :");
        System.out.println(accessModifier);
        System.out.print("keyword :");
        System.out.println(keyword);
        System.out.print("immutability_keyword :");
        System.out.println(immutability_keyword);
        System.out.print("identifier :");
        System.out.println(identifier);
        System.out.print("type_annotation :");
        System.out.println(type_annotations);
        System.out.print("type :");
        System.out.println(type);
        System.out.print("assignment :");
        System.out.println(assignment);
        System.out.print("semi colon :");
        System.out.println(semi_colon);
        System.out.print("value :");
        System.out.println(value);
        System.out.print("is assigned a value :");
        System.out.println(is_assigned_a_value);
    }
}
