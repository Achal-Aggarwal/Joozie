package com.joozie.core;

public abstract class Action extends TransitiveNode {
  protected Action(){
    super();
  }

  protected Action(String actionName){
    super(actionName);
  }

  public Action onSuccess(Node successNodeName){
    setNextNode(successNodeName);

    return this;
  }

  public Action onError(Node errorNodeName){
    setErrorNode(errorNodeName);

    return this;
  }

  @Override
  public String build() {
    return
      "<action name='" + getName() + "'>"
        + getActionXmlString()
        + "<ok to='" + getNextNode().getName() + "'/>"
        + "<error to='" + getErrorNode().getName() + "'/>"
      + "</action>";
  }

  protected abstract String getActionXmlString();
}
