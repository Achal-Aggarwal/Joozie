package com.joozie.core;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class NodeList extends TransitiveNode {
  public static final boolean DONT_OVERWITE = false;
  public static final boolean OVERWRITE = true;
  private List<Node> nodes = new ArrayList<Node>();

  public NodeList(Node nextNode, Node errorNode) {
    setNextNode(nextNode);
    setErrorNode(errorNode);
  }

  public NodeList firstDo(Node node) {
    addNodeAndSetTransitionNodes(node);
    return this;
  }

  public NodeList thenDo(Node node) {
    updateLastNodeTransitionNodes(node, null);

    addNodeAndSetTransitionNodes(node);
    return this;
  }

  private void addNodeAndSetTransitionNodes(Node node){
    nodes.add(node);
    setTransitionNodes(node, getNextNode(), getErrorNode(), DONT_OVERWITE);
  }

  public void updateLastNodeTransitionNodes(Node nextNode, Node errorNode) {
    Node lastAddedNode = nodes.get(nodes.size() - 1);
    setTransitionNodes(lastAddedNode, nextNode, errorNode, OVERWRITE);
  }

  private void setTransitionNodes(Node node, Node endNode, Node killNode, boolean overwrite){
    if (node instanceof TransitiveNode){
      TransitiveNode transitiveNode = (TransitiveNode) node;

      if (endNode != null && (overwrite || transitiveNode.isNextNodeNotSet())){
        transitiveNode.setNextNode(endNode);
      }

      if (killNode != null && (overwrite || transitiveNode.isErrorNodeNotSet())){
        transitiveNode.setErrorNode(killNode);
      }
    }
  }

  public void updateErrorNodeOfEveryNode(Node errorNode) {
    for (Node node : nodes) {
      if (node instanceof TransitiveNode){
        ((TransitiveNode) node).setErrorNode(errorNode);
      }
    }
  }

  public String getFirstNodeName(){
    if (nodes.isEmpty()){
      return "";
    }

    return nodes.get(0).getName();
  }

  public String build(){
    StringBuilder result = new StringBuilder();

    for (Node node : nodes) {
      result.append(node.build());
    }

    return result.toString();
  }

  public Node getNode(int index) {
    return nodes.get(index);
  }
}
