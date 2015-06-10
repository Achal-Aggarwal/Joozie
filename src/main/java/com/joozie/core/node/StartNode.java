package com.joozie.core.node;

import com.joozie.core.Node;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StartNode extends Node {
  public StartNode(String toNodeName){
    super(toNodeName);
  }

  @Override
  public String build() {
    return "<start to='" + getName() + "'/>";
  }
}
