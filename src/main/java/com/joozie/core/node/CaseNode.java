package com.joozie.core.node;

import com.joozie.core.Node;
import com.joozie.core.NodeList;

public class CaseNode {
  public static final boolean DEFAULT_CASE = true;
  public static final boolean IF_CASE = false;
  private String predicate;
  private NodeList nodeList;
  private boolean defaultCase;
  private boolean emptyDefaultCase;

  private CaseNode(String predicate, NodeList nodeList, boolean defaultCase){
    this.predicate = predicate;
    this.nodeList = nodeList;
    this.defaultCase = defaultCase;
  }

  public CaseNode(String predicate, NodeList nodeList){
    this(predicate, nodeList, IF_CASE);
  }

  public static CaseNode getDefaultNode(NodeList nodeList){
    return new CaseNode(null, nodeList, DEFAULT_CASE);
  }

  public static CaseNode getEmptyDefaultNode(){
    CaseNode caseNode = new CaseNode(null, new NodeList().firstDo(new EmptyNode()), DEFAULT_CASE);
    caseNode.emptyDefaultCase = true;

    return caseNode;
  }

  public String getNodeListBuild() {
    if (emptyDefaultCase){
      return "";
    }

    return nodeList.build();
  }

  public void updateLastNodeTransitionNodes(Node endNode, Node killNode){
    nodeList.updateLastNodeTransitionNodes(endNode, killNode);
  }

  public void updateErrorNodeOfEveryNodeInNodeList(Node errorNode){
    nodeList.updateErrorNodeOfEveryNode(errorNode);
  }

  public String build() {
    if (defaultCase){
      return "<default to='" + nodeList.getFirstNodeName() + "'/>";
    }

    return "<case to='" + nodeList.getFirstNodeName() + "'>" + predicate + "</case>";
  }
}
