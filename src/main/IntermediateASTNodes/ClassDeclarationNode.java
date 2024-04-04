package main.IntermediateASTNodes;

import java.util.List;

import main.Lexer.LexerMain.TokenType;

public class ClassDeclarationNode {
    public List<FieldNameObjectNode> fieldNameList;
    public List<MethodDeclarationObjectNode> methodList;
    public TokenType accessModifier;
    public String identifier;

    public ClassDeclarationNode(List<FieldNameObjectNode> fieldNameList, List<MethodDeclarationObjectNode> methodList,
            TokenType accessModifier, String identifier) {
        this.fieldNameList = fieldNameList;
        this.methodList = methodList;
        this.accessModifier = accessModifier;
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "\n" +  this.fieldNameList.toString() + "\n" + this.methodList.toString() + "\n" + "access modifier: "
                + accessModifier + "\n" + "identifier: " + identifier;
    }
}
