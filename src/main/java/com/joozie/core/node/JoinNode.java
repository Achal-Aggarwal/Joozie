package com.joozie.core.node;

import com.joozie.core.TransitiveNode;

public class JoinNode extends TransitiveNode {
  public JoinNode(String name){
    super(name);
  }

  @Override
  public String build() {
    return "<join name='" + getName() + "' to='" + getNextNode().getName() + "'/>";
  }
}
