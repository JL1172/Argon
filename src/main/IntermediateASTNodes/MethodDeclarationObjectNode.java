package main.IntermediateASTNodes;

import java.util.List;

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
    public List<MethodParamObjectNode> parameterBody;
    public List<MethodVariableNode> variableNodes;
    public List<StatementNode> statementNodes;

    public MethodDeclarationObjectNode(NodeTypeEnum nodeType, TokenType accessModifier, TokenType keyword,
            TokenType immutability_keyword, TokenType type, TokenType assignment, String value, String identifier,
            TokenType type_annotations, List<MethodParamObjectNode> parameterBody,
            List<MethodVariableNode> variableNodes, List<StatementNode> statementNodes) {
        super(nodeType, accessModifier, keyword, immutability_keyword, type, assignment, value, identifier,
                type_annotations);
        this.parameterBody = parameterBody;
        this.variableNodes = variableNodes;
        this.statementNodes = statementNodes;
    }
    /*
     * nodeType
     * accessmodifer
     * keyword
     * identifier
     * parameter body
     * type annotation
     * type of method
     * code body in method split to variables and couts (make variable node and cout
     * node)
     * end of input }
     */
    // overriding the toString() method to be concatenation of type and value;
    // access modifier? + stat? + methodName + ( + ...args + ) + :: + type + {
    // (varName :: type = value) ?
    // cout(varName) cout("hello world"); + }

    @Override
    public String toString() {
        return "Node {\n" +
                "nodeType='" + nodeType + "'," + '\n' +
                "access modifier='" + accessModifier + "'," + '\n' +
                "keyword='" + keyword + "'," + '\n' +
                "identifier='" + identifier + "'," + '\n' +
                "parameterbody'" + this.parameterBody.toString() + "'," + "\n" +
                "type='" + type + "'," + '\n' +
                "statement='" + this.statementNodes.toString() + "'," + '\n' +
                accessModifier + " " + keyword + " " + identifier + " "
                + " " + type + " "
                + '}';
    }
}
