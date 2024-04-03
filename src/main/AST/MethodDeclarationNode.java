package main.AST;

import java.util.List;

public class MethodDeclarationNode extends Node {
    private String methodName;
    private String returnType;
    private List<Node> methodBody;

    public MethodDeclarationNode(String methodName, String returnType, List<Node> methodBody, int lineNumber,
            int columnNumber) {
        super(lineNumber, columnNumber);
        this.methodBody = methodBody;
        this.returnType = returnType;
        this.methodName = methodName;
    }

    public List<Node> getMethodBody() {
        return methodBody;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getReturnType() {
        return returnType;
    }
}
