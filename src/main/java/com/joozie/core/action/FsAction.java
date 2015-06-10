package com.joozie.core.action;

import com.joozie.core.Action;
import com.joozie.core.action.command.*;

import java.util.ArrayList;
import java.util.List;

public class FsAction extends Action {
  private List<Command> commandList = new ArrayList<Command>();

  public FsAction(){
    super();
  }

  public FsAction(String actionName){
    super(actionName);
  }

  public FsAction delete(String path) {
    commandList.add(new DeleteCommand(path));
    return this;
  }

  public FsAction mkdir(String path) {
    commandList.add(new MkdirCommand(path));
    return this;
  }

  public FsAction move(String source, String destination) {
    commandList.add(new MoveCommand(source, destination));
    return this;
  }

  public FsAction chmod(String path, String permissions, boolean dirFiles) {
    commandList.add(new ChmodCommand(path, permissions, dirFiles));
    return this;
  }

  @Override
  protected String getActionXmlString() {
    StringBuilder result = new StringBuilder();

    result.append("<fs>");

    for (Command command : commandList) {
      result.append(command.build());
    }

    result.append("</fs>");

    return result.toString();
  }
}
