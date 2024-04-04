package main.IntermediateASTNodes;

import java.util.List;

public class IntermediateAST {
    public List<ClassDeclarationNode> classNode;
    public IntermediateAST(List<ClassDeclarationNode> classNode) {
        this.classNode = classNode;
    }
    @Override
    public String toString() {
        return this.classNode.toString();
    }
}
