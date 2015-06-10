package com.joozie.core.node;

import com.joozie.core.Node;

public class EndNode extends Node {
  public EndNode(){
    super();
  }

  public EndNode(String endNodeName){
    super(endNodeName);
  }

  @Override
  public String build() {
    return "<end name='" + getName() + "'/>";
  }
}
