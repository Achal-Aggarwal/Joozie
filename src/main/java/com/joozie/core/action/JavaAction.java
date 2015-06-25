package com.joozie.core.action;

import com.joozie.core.Action;
import com.joozie.core.Configurable;
import com.joozie.core.Configuration;
import com.joozie.core.action.command.DeleteCommand;
import com.joozie.core.action.command.MkdirCommand;
import org.codehaus.plexus.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JavaAction extends Action implements Configurable {
  Configuration configuration;
  List<Command> commandList = new ArrayList<Command>();
  String mainClass;
  List<String> javaOpts = new ArrayList<String>();
  HashMap<String, String> javaOptsMap = new HashMap<String, String>();
  List<String> args = new ArrayList<String>();

  String filePath;
  String archivePath;

  boolean captureOutput;

  public JavaAction(){
    super();
  }

  public JavaAction(String actionName){
    super(actionName);
  }

  @Override
  public JavaAction usingConfig(Configuration configuration) {
    this.configuration = configuration;

    return this;
  }

  public JavaAction prepare(DeleteCommand deleteCommand){
    commandList.add(deleteCommand);

    return this;
  }

  public JavaAction prepare(MkdirCommand mkdirCommand){
    commandList.add(mkdirCommand);

    return this;
  }

  public JavaAction mainClass(String mainClass){
    this.mainClass = mainClass;
    return this;
  }

  public JavaAction withJavaOpt(String option){
    javaOpts.add(option);

    return this;
  }

  public JavaAction withJavaOpt(String key, String value){
    javaOptsMap.put(key, value);

    return this;
  }

  public JavaAction withArg(String arg){
    args.add(arg);

    return this;
  }

  public JavaAction withArg(String key, String value){
    args.add("-" + key);
    args.add(value);

    return this;
  }

  public JavaAction usingFile(String filePath){
    this.filePath = filePath;

    return this;
  }

  public JavaAction usingArchive(String filePath){
    this.archivePath = filePath;

    return this;
  }

  public JavaAction doCaptureOutput(){
    captureOutput = true;

    return this;
  }

  @Override
  protected String getActionXmlString() {
    StringBuilder result = new StringBuilder();

    result.append("<java>");
    result.append(configuration.build());

    if (!commandList.isEmpty()){
      result.append("<prepare>");
      for (Command command : commandList) {
        result.append(command.build());
      }
      result.append("</prepare>");
    }

    result.append("<main-class>" + mainClass + "</main-class>");
    result.append("<java-opts>" + getJavaOptsXMLString() + "</java-opts>");
    result.append("<arg>" + getArgsXMLString() + "</arg>");
    result.append("<file>" + filePath + "</file>");
    result.append("<archive>" + archivePath + "</archive>");

    if (captureOutput){
      result.append("<capture-output/>");
    }

    result.append("</java>");

    return result.toString();
  }

  private String getJavaOptsXMLString() {
    StringBuilder result = new StringBuilder();

    result.append(StringUtils.join(javaOpts.iterator(), " ") + " ");

    for (String javaOpt : javaOptsMap.keySet()) {
      result.append("-D" + javaOpt + "=" + javaOptsMap.get(javaOpt));
    }

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
