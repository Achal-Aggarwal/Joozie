package com.joozie.core.node;

import com.joozie.core.Node;

public class JoinNode extends Node {
  private Node nextNode;

  public JoinNode(String name){
    super(name);
  }

  @Override
  public String build() {
    return "<join name='" + getName() + "' to='" + nextNode.getName() + "'/>";
  }

  public void setNextNode(Node nextNode) {
    this.nextNode = nextNode;
  }
}
