package main.Parser;

public abstract class Node {
    private int lineNumber;
    private int columnNumber;

    public Node(int lineNumber, int columnNumber) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }
    public int getLineNumber() {
        return this.lineNumber;
    }
    public int getColumnNumber() {
        return this.columnNumber;
    }
    @Override
    public String toString() {
        return String.format("(%d:%d)", lineNumber, columnNumber);
    }
}
