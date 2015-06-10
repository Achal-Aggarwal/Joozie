package com.joozie.core.node;

import com.joozie.core.Node;
import com.joozie.core.NodeList;

import java.util.ArrayList;
import java.util.List;

public class Decision extends Node {
  List<CaseNode> caseList = new ArrayList<CaseNode>();

  public Decision(){
    super();
  }

  public Decision(String name){
    super(name);
  }


  public Decision ifTrue(String predicate, NodeList nodeList){
    caseList.add(new CaseNode(predicate, nodeList));

    return this;
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
    caseList.add(new CaseNode(null, nodeList, true));

    return this;
  }

  public Decision otherwise(Node node){
    return otherwise(new NodeList().firstDo(node));
  }

  public void updateTransitionNodes(Node endNode, Node killNode){
    for (CaseNode caseNode : caseList) {
      caseNode.updateEndAndErrorNode(endNode, killNode);
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
