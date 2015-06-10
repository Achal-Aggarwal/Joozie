package com.joozie.core;

import com.joozie.core.node.Decision;
import com.joozie.core.node.Fork;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
public class NodeList extends ArrayList<Node> {
  public static final boolean DONT_OVERWITE = false;
  public static final boolean OVERWRITE = true;
  private Node endNode;
  private Node killNode;

  public NodeList(Node endNode, Node killNode) {
    this.endNode = endNode;
    this.killNode = killNode;
  }

  private void setTransitionNodes(Node node, Node endNode, Node killNode, boolean overwrite){
    if (node instanceof Action){
      Action action = (Action) node;

      if (endNode != null && (overwrite || action.isSuccessNotSet())){
        action.onSuccess(endNode.getName());
      }

      if (killNode != null && (overwrite || action.isErrorNotSet())){
        action.onError(killNode.getName());
      }
    } else if (node instanceof Decision){
      ((Decision) node).updateTransitionNodes(endNode, killNode);
    } else if (node instanceof Fork){
      ((Fork) node).updateTransitionNodes(endNode);
    }
  }

  private void addNodeAndSetTransitionNodes(Node node){
    add(node);
    setTransitionNodes(node, this.endNode, this.killNode, DONT_OVERWITE);
  }

  public NodeList firstDo(Node node) {
    addNodeAndSetTransitionNodes(node);
    return this;
  }

  public NodeList thenDo(Node node) {
    updateLastNodeTransitionNodes(node, this.killNode);

    addNodeAndSetTransitionNodes(node);
    return this;
  }

  public void updateLastNodeTransitionNodes(Node endNode, Node killNode) {
    Node lastAddedNode = get(size() - 1);
    setTransitionNodes(lastAddedNode, endNode, killNode, OVERWRITE);
  }

  public String getFirstNodeName(){
    if (isEmpty()){
      return "";
    }

    return get(0).getName();
  }

  public String build(){
    StringBuilder result = new StringBuilder();

    for (Node node : this) {
      result.append(node.build());
    }

    return result.toString();
  }
}
