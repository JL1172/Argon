package main.IntermediateASTNodes;

import main.Lexer.LexerMain.TokenType;

public abstract class ObjectNodeTemplate {
    /*
     * DTO
     * 
     * {nodeType: fieldName, accessModifier: pub || null if blank, keywords: stat ||
     * null, immutability_keyword: const || null, type_annotation: "::" or none
     * type: one, assignment: "=", value:
     * "value"}
     * 
     */
    public NodeTypeEnum nodeType;
    public TokenType accessModifier;
    public TokenType keyword;
    public TokenType immutability_keyword;
    public String identifier;
    public TokenType type;
    public TokenType type_annotations;
    public TokenType assignment;
    public Object value;

    public ObjectNodeTemplate(NodeTypeEnum nodeType, TokenType accessModifier, TokenType keyword,
            TokenType immutability_keyword, TokenType type, TokenType assignment, String value, String identifier,
            TokenType type_annotations) {
        this.nodeType = nodeType;
        this.accessModifier = accessModifier;
        this.keyword = keyword;
        this.immutability_keyword = immutability_keyword;
        this.type = type;
        this.assignment = assignment;
        this.value = value;
        this.identifier = identifier;
        this.type_annotations = type_annotations;
    }

    public ObjectNodeTemplate(int value, NodeTypeEnum nodeType, TokenType accessModifier, TokenType keyword,
            TokenType immutability_keyword, TokenType type, TokenType assignment, String identifier,
            TokenType type_annotations) {
        this.value = value;
        this.nodeType = nodeType;
        this.accessModifier = accessModifier;
        this.keyword = keyword;
        this.immutability_keyword = immutability_keyword;
        this.type = type;
        this.assignment = assignment;
        this.identifier = identifier;
        this.type_annotations = type_annotations;
    }

    public ObjectNodeTemplate(NodeTypeEnum nodeType, TokenType accessModifier, TokenType keyword,
            TokenType immutability_keyword, TokenType type, TokenType assignment, float value, String identifier,
            TokenType type_annotations) {
        this.value = value;
        this.nodeType = nodeType;
        this.accessModifier = accessModifier;
        this.keyword = keyword;
        this.immutability_keyword = immutability_keyword;
        this.type = type;
        this.assignment = assignment;
        this.identifier = identifier;
        this.type_annotations = type_annotations;
    }

    public ObjectNodeTemplate(double value, NodeTypeEnum nodeType, TokenType accessModifier, TokenType keyword,
            TokenType immutability_keyword, TokenType type, TokenType assignment, String identifier,
            TokenType type_annotations) {
        this.value = value;
        this.nodeType = nodeType;
        this.accessModifier = accessModifier;
        this.keyword = keyword;
        this.immutability_keyword = immutability_keyword;
        this.type = type;
        this.assignment = assignment;
        this.identifier = identifier;
        this.type_annotations = type_annotations;
    }

    public ObjectNodeTemplate(boolean value, NodeTypeEnum nodeType, TokenType accessModifier, TokenType keyword,
            TokenType immutability_keyword, TokenType type, TokenType assignment, String identifier,
            TokenType type_annotations) {
        this.value = value;
        this.nodeType = nodeType;
        this.accessModifier = accessModifier;
        this.keyword = keyword;
        this.immutability_keyword = immutability_keyword;
        this.type = type;
        this.assignment = assignment;
        this.identifier = identifier;
        this.type_annotations = type_annotations;
    }

    public void clear() {
        nodeType = null;
        accessModifier = null;
        keyword = null;
        immutability_keyword = null;
        identifier = null;
        type = null;
        type_annotations = null;
        assignment = null;
        value = null;
    }
}
