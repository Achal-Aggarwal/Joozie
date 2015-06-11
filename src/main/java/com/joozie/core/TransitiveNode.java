package com.joozie.core;

public abstract class TransitiveNode extends Node {
  private Node nextNode, errorNode;

  protected TransitiveNode(){
    super();
  }

  protected TransitiveNode(String nodeName){
    super(nodeName);
  }


  public Node getNextNode() {
    return nextNode;
  }

  public void setNextNode(Node nextNode) {
    this.nextNode = nextNode;
  }

  public Node getErrorNode() {
    return errorNode;
  }

  public void setErrorNode(Node errorNode) {
    this.errorNode = errorNode;
  }

  public boolean isNextNodeNotSet() {
    return nextNode == null;
  }

  public boolean isErrorNodeNotSet() {
    return errorNode == null;
  }
}
