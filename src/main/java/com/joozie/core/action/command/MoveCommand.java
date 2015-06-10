package com.joozie.core.action.command;

import com.joozie.core.action.Command;

public class MoveCommand implements Command {
  private String source;
  private String destination;

  public MoveCommand(String source, String destination) {
    this.source = source;
    this.destination = destination;
  }

  @Override
  public String build() {
    return "<move source='" + source + "' target='" + destination + "'/>";
  }
}
