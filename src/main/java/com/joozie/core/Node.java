package com.joozie.core;

public abstract class Node {
  private String name;

  private static int nodeCounter = 0;

  protected Node(){
    this("NODE-" + nodeCounter);
    nodeCounter += 1;
  }

  protected Node(String nodeName){
    name = nodeName;
  }

  public String getName() {
    return name;
  }

  public abstract String build();
}
