package com.joozie.core.function;

public class WorkflowEL {
  public static String workflowId(){
    return "wf:id()";
  }

  public static String workflowName(){
    return "wf:name()";
  }

  public static String workflowAppPath(){
    return "wf:appPath()";
  }

  public static String wfConf(String name){
    return Function.generate("wf:conf", name);
  }
}
