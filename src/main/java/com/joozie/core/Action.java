package com.joozie.core;

public abstract class Action extends Node {
  protected String okNodeName;
  protected String errorNodeName;

  protected Action(){
    super();
  }

  protected Action(String actionName){
    super(actionName);
  }

  public Action onSuccess(String successNodeName){
    okNodeName = successNodeName;

    return this;
  }

  public Action onError(String errorNodeName){
    this.errorNodeName = errorNodeName;

    return this;
  }

  @Override
  public String build() {
    return
      "<action name='" + getName() + "'>"
        + getActionXmlString()
        + "<ok to='" + okNodeName + "'/>"
        + "<error to='" + errorNodeName + "'/>"
      + "</action>";
  }

  protected abstract String getActionXmlString();

  public boolean isSuccessNotSet() {
    return okNodeName == null;
  }

  public boolean isErrorNotSet() {
    return errorNodeName == null;
  }
}
