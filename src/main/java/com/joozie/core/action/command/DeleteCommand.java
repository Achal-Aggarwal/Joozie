package com.joozie.core.action.command;

import com.joozie.core.action.Command;

public class DeleteCommand implements Command {
  protected String path;
  public DeleteCommand(String path) {
    this.path = path;
  }

  @Override
  public String build() {
    return "<delete path='" + path + "'/>";
  }
}
