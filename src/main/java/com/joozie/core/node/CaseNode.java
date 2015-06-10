package com.joozie.core.node;

import com.joozie.core.Node;
import com.joozie.core.NodeList;

public class CaseNode {
  private String predicate;
  private NodeList nodeList;
  private boolean defaultCase;

  public CaseNode(String predicate, NodeList nodeList){
    this(predicate, nodeList, false);
  }

  public CaseNode(String predicate, NodeList nodeList, boolean defaultCase){
    this.predicate = predicate;
    this.nodeList = nodeList;
    this.defaultCase = defaultCase;
  }

  public String getNodeListBuild() {
    return nodeList.build();
  }

  public void updateEndAndErrorNode(Node endNode, Node killNode){
    nodeList.updateLastNodeTransitionNodes(endNode, killNode);
  }

  public String build() {
    if (defaultCase){
      return "<default to='" + nodeList.getFirstNodeName() + "'/>";
    }

    return "<case to='" + nodeList.getFirstNodeName() + "'>" + predicate + "</case>";
  }
}
