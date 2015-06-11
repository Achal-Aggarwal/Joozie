package com.joozie.core.node;

import com.joozie.core.Node;
import com.joozie.core.NodeList;
import com.joozie.core.TransitiveNode;

import java.util.ArrayList;
import java.util.List;

public class Decision extends TransitiveNode {
  public static final boolean DEFAULT_CASE = true;
  List<CaseNode> caseList = new ArrayList<CaseNode>();
  private Node nextNode;
  private Node errorNode;

  public Decision(){
    super();
  }

  public Decision(String name){
    super(name);
  }

  public Decision(Node nextNode, Node errorNode) {
    this.nextNode = nextNode;
    this.errorNode = errorNode;
  }

  public Decision ifTrue(String predicate, NodeList nodeList){
    updateNodeListTransitionNodes(nodeList);

    caseList.add(new CaseNode(predicate, nodeList));

    return this;
  }

  private void updateNodeListTransitionNodes(NodeList nodeList) {
    if (nextNode != null && nodeList.getNextNode() == null){
      nodeList.updateLastNodeTransitionNodes(nextNode, null);
    }

    if (errorNode != null && nodeList.getErrorNode() == null){
      nodeList.updateErrorNodeOfEveryNode(errorNode);
    }
  }

  public Decision ifTrue(String predicate, Node node){
    return ifTrue(predicate, new NodeList().firstDo(node));
  }

  public Decision elseIf(String predicate, NodeList nodeList){
    return ifTrue(predicate, nodeList);
  }

  public Decision elseIf(String predicate, Node node){
    return elseIf(predicate, new NodeList().firstDo(node));
  }

  public Decision otherwise(NodeList nodeList){
    updateNodeListTransitionNodes(nodeList);

    caseList.add(new CaseNode(null, nodeList, DEFAULT_CASE));

    return this;
  }

  public Decision otherwise(Node node){
    return otherwise(new NodeList().firstDo(node));
  }

  public void updateLastNodeTransitionNodes(Node endNode, Node killNode){
    for (CaseNode caseNode : caseList) {
      caseNode.updateLastNodeTransitionNodes(endNode, killNode);
    }
  }

  @Override
  public String build() {
    StringBuilder result = new StringBuilder();

    result.append("<decision name='" + getName() + "'><switch>");

    for (CaseNode caseNode : caseList) {
      result.append(caseNode.build());
    }

    result.append("</switch></decision>");

    for (CaseNode caseNode : caseList) {
      result.append(caseNode.getNodeListBuild());
    }

    return result.toString();
  }
}
