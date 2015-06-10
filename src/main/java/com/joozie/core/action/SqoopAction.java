package com.joozie.core.action;

import com.joozie.core.Action;
import com.joozie.core.Configurable;
import com.joozie.core.Configuration;
import com.joozie.core.action.command.DeleteCommand;
import com.joozie.core.action.command.MkdirCommand;

import java.util.ArrayList;
import java.util.List;

public class SqoopAction extends Action implements Configurable {
  Configuration configuration;
  List<Command> commandList = new ArrayList<Command>();
  String command;
  List<String> args = new ArrayList<String>();

  String filePath;
  String archivePath;

  public SqoopAction(){
    super();
  }

  public SqoopAction(String actionName){
    super(actionName);
  }

  @Override
  public SqoopAction usingConfig(Configuration configuration) {
    this.configuration = configuration;

    return this;
  }

  public SqoopAction prepare(DeleteCommand deleteCommand){
    commandList.add(deleteCommand);

    return this;
  }

  public SqoopAction prepare(MkdirCommand mkdirCommand){
    commandList.add(mkdirCommand);

    return this;
  }

  public SqoopAction command(String command){
    this.command = command;
    return this;
  }

  public SqoopAction withArg(String arg){
    args.add(arg);

    return this;
  }

  public SqoopAction usingFile(String filePath){
    this.filePath = filePath;

    return this;
  }

  public SqoopAction usingArchive(String filePath){
    this.archivePath = filePath;

    return this;
  }

  @Override
  protected String getActionXmlString() {
    StringBuilder result = new StringBuilder();

    result.append("<sqoop  xmlns='uri:oozie:sqoop-action:0.2'>");
    result.append(configuration.build());

    if (!commandList.isEmpty()){
      result.append("<prepare>");
      for (Command command : commandList) {
        result.append(command.build());
      }
      result.append("</prepare>");
    }

    if (command != null){
      result.append("<command>" + command + "</command>");
    } else {
      result.append(getArgsXMLString());
    }

    result.append("<file>" + filePath + "</file>");
    result.append("<archive>" + archivePath + "</archive>");

    result.append("</sqoop>");

    return result.toString();
  }

  private String getArgsXMLString() {
    StringBuilder result = new StringBuilder();

    for (String arg : args) {
      result.append("<arg>" + arg + "</arg>");
    }

    return result.toString();
  }
}