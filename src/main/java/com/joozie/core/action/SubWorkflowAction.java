package com.joozie.core.action;

import com.joozie.core.Action;
import com.joozie.core.Configurable;
import com.joozie.core.Configuration;

public class SubWorkflowAction extends Action implements Configurable {
  Configuration configuration;
  private String appPath;
  private boolean propogateConfig;

  public SubWorkflowAction(){
    super();
  }

  public SubWorkflowAction(String actionName){
    super(actionName);
  }

  public SubWorkflowAction usingAppPath(String appPath){
    this.appPath = appPath;
    return this;
  }

  public SubWorkflowAction doPropogateConfig(){
    propogateConfig = true;

    return this;
  }

  @Override
  public SubWorkflowAction usingConfig(Configuration configuration) {
    this.configuration = configuration;

    return this;
  }

  @Override
  protected String getActionXmlString() {
    StringBuilder result = new StringBuilder();

    result.append("<sub-workflow>");
    result.append("<app-path>" + appPath + "</app-path>");

    if (propogateConfig){
      result.append("<propagate-configuration/>");
    }

    if (configuration != null){
      result.append(configuration.build());
    }

    result.append("</sub-workflow>");

    return null;
  }
}
