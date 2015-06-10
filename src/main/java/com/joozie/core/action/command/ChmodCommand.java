package com.joozie.core.action.command;

import com.joozie.core.action.Command;

public class ChmodCommand implements Command {
  private String path;
  private String permissions;
  private boolean dirFiles;

  public ChmodCommand(String path, String permissions, boolean dirFiles) {
    this.path = path;
    this.permissions = permissions;
    this.dirFiles = dirFiles;
  }

  @Override
  public String build() {
    return "<chmod path='" + path + "' permissions='" + permissions
      + "' dir-files='" + String.valueOf(dirFiles) + "'/>";
  }
}
