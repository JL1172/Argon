package main.Parser;

import java.util.List;

public class ClassDeclarationNode extends Node {
    private List<Node> classBody;
    private String className;
    private List<Node> parentNode;

    public ClassDeclarationNode(String className, List<Node> classBody, List<Node> parentNode, int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
        this.classBody = classBody;
        this.className = className;
        this.parentNode = parentNode;
    }

    public List<Node> getClassBody() {
        return classBody;
    }
    public List<Node> getParentNode() {
        return parentNode;
    }
    public String getClassName() {
        return className;
    }

    public void setClassBody(List<Node> classBody) {
        this.classBody = classBody;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    public void setParentNode(List<Node> parentNode) {
        this.parentNode = parentNode;
    }
}
