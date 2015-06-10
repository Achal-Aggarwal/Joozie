package com.joozie.core.node;

import com.joozie.core.Node;
import com.joozie.core.NodeList;

import java.util.ArrayList;
import java.util.List;

public class Fork extends Node {
  List<NodeList> nodeLists = new ArrayList<NodeList>();
  JoinNode joinNode;

  public Fork(){
    super();
    joinNode = new JoinNode(joinNodeName());
  }

  public Fork(String name){
    super(name);
    joinNode = new JoinNode(joinNodeName());
  }

  public Fork doThis(NodeList nodeList){
    nodeLists.add(nodeList);

    return this;
  }

  public Fork doThis(Node node){
    return doThis(new NodeList().firstDo(node));
  }

  private String joinNodeName(){
    return getName() + "-JOIN";
  }

  public void updateTransitionNodes(Node endNode){
    joinNode.setNextNode(endNode);
  }

  @Override
  public String build() {
    StringBuilder result = new StringBuilder();

    result.append("<fork name='" + getName() + "'>");

    for (NodeList nodeList : nodeLists) {
      result.append("<path start='" + nodeList.getFirstNodeName() + "'/>");
      nodeList.updateLastNodeTransitionNodes(joinNode, null);
    }

    result.append(joinNode.build());

    for (NodeList nodeList : nodeLists) {
      result.append(nodeList.build());
    }

    return result.toString();
  }
}
