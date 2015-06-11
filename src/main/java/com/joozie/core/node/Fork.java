package com.joozie.core.node;

import com.joozie.core.Node;
import com.joozie.core.NodeList;
import com.joozie.core.TransitiveNode;

import java.util.ArrayList;
import java.util.List;

public class Fork extends TransitiveNode {
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
    nodeList.updateLastNodeTransitionNodes(joinNode, null);
    nodeList.updateErrorNodeOfEveryNode(getErrorNode());

    return this;
  }

  public Fork doThis(Node node){
    return doThis(new NodeList().firstDo(node));
  }

  public void updateErrorNodeOfEveryNodeInNodeList(){
    for (NodeList nodeList : nodeLists) {
      nodeList.updateErrorNodeOfEveryNode(getErrorNode());
    }
  }

  private String joinNodeName(){
    return getName() + "-JOIN";
  }

  @Override
  public void setNextNode(Node nextNode) {
    super.setNextNode(nextNode);
    joinNode.setNextNode(nextNode);
  }

  @Override
  public String build() {
    StringBuilder result = new StringBuilder();

    result.append("<fork name='" + getName() + "'>");

    for (NodeList nodeList : nodeLists) {
      result.append("<path start='" + nodeList.getFirstNodeName() + "'/>");
    }

    result.append("</fork>");

    result.append(joinNode.build());

    for (NodeList nodeList : nodeLists) {
      result.append(nodeList.build());
    }

    return result.toString();
  }
}
