package main.IntermediateASTNodes;


public class MethodParamObjectNode {
    public PrimitiveTypeInterface parameter;
    public PrimitiveTypeEnum parameterType;
    public MethodParamObjectNode(PrimitiveTypeEnum parameterType, PrimitiveTypeInterface parameter) {
        this.parameter = parameter;
        this.parameterType = parameterType;
    } 
}
