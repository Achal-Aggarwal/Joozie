package com.joozie.core.node;

import com.joozie.core.TransitiveNode;

public class EmptyNode extends TransitiveNode {
  @Override
  public String build() {
    return "";
  }

  @Override
  public String getName(){
    return getNextNode().getName();
  }
}
