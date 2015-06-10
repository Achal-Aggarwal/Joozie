package com.joozie.core.action.command;

import com.joozie.core.action.Command;

public class MkdirCommand implements Command {
  protected String path;
  public MkdirCommand(String path) {
    this.path = path;
  }

  @Override
  public String build() {
    return "<mkdir path='" + path + "'/>";
  }
}
