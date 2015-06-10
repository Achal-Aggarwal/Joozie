package com.joozie.core.node;

import com.joozie.core.Node;

public class KillNode extends Node {
  public String message;

  public KillNode(String message){
    super();
    this.message = message;
  }

  public KillNode(String endNodeName, String message){
    super(endNodeName);
    this.message = message;
  }

  @Override
  public String build() {
    return "<kill name='" + getName() + "'><message>" + message + "</message></kill>";
  }

  public String getMessage() {
    return message;
  }
}
