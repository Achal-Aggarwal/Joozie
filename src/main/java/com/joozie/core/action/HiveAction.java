package com.joozie.core.action;

import com.joozie.core.Action;
import com.joozie.core.Configurable;
import com.joozie.core.Configuration;
import com.joozie.core.action.command.DeleteCommand;
import com.joozie.core.action.command.MkdirCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HiveAction extends Action implements Configurable {
  Configuration configuration;
  List<Command> commandList = new ArrayList<Command>();
  String script;
  HashMap<String, String> param = new HashMap<String, String>();

  String jobXML;
  String filePath;
  String archivePath;

  public HiveAction(){
    super();
  }

  public HiveAction(String actionName){
    super(actionName);
  }

  @Override
  public HiveAction usingConfig(Configuration configuration) {
    this.configuration = configuration;

    return this;
  }

  public HiveAction prepare(DeleteCommand deleteCommand){
    commandList.add(deleteCommand);

    return this;
  }

  public HiveAction prepare(MkdirCommand mkdirCommand){
    commandList.add(mkdirCommand);

    return this;
  }

  public HiveAction script(String script){
    this.script = script;
    return this;
  }

  public HiveAction withParameter(String key, String value){
    param.put(key, value);

    return this;
  }

  public HiveAction withParameter(HashMap<String, String> params){
    param.putAll(params);

    return this;
  }

  public HiveAction usingFile(String filePath){
    this.filePath = filePath;

    return this;
  }

  public HiveAction usingArchive(String filePath){
    this.archivePath = filePath;

    return this;
  }

  public HiveAction usingJobXML(String filePath){
    this.jobXML = filePath;

    return this;
  }

  @Override
  protected String getActionXmlString() {
    StringBuilder result = new StringBuilder();

    result.append("<hive  xmlns='uri:oozie:hive-action:0.2'>");
    result.append(configuration.build());

    if (!commandList.isEmpty()){
      result.append("<prepare>");
      for (Command command : commandList) {
        result.append(command.build());
      }
      result.append("</prepare>");
    }

    result.append("<script>" + script + "</script>");
    result.append(getParamXMLString());
    result.append("<file>" + filePath + "</file>");
    result.append("<archive>" + archivePath + "</archive>");

    result.append("</hive>");

    return result.toString();
  }

  private String getParamXMLString() {
    StringBuilder result = new StringBuilder();
    for (String key : param.keySet()) {
      result.append("<param>"+ key + "=" + param.get(key) + "</param>");
    }

    return result.toString();
  }
}