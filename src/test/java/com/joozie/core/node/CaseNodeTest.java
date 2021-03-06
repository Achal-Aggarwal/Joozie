package com.joozie.core.node;

import com.joozie.core.Node;
import com.joozie.core.NodeList;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CaseNodeTest {

  @Test
  public void shouldCreateCaseWithAPredicate(){
    NodeList nodeList = mock(NodeList.class);
    when(nodeList.getFirstNodeName()).thenReturn("FIRST_NODE_NAME");

    CaseNode caseNode = new CaseNode("PREDICATE", nodeList);
    assertThat(caseNode, instanceOf(CaseNode.class));
    assertThat(caseNode.build(), is("<case to='FIRST_NODE_NAME'>PREDICATE</case>"));
  }

  @Test
  public void shouldCreateDefaultCaseWithNoPredicate(){
    NodeList nodeList = mock(NodeList.class);
    when(nodeList.getFirstNodeName()).thenReturn("FIRST_NODE_NAME");

    CaseNode caseNode = CaseNode.getDefaultNode(nodeList);
    assertThat(caseNode, instanceOf(CaseNode.class));
    assertThat(caseNode.build(), is("<default to='FIRST_NODE_NAME'/>"));
  }

  @Test
  public void shouldGetNodeListBuild(){
    NodeList nodeList = mock(NodeList.class);
    when(nodeList.build()).thenReturn("NODE_LIST_BUILD");

    CaseNode caseNode = new CaseNode("PREDICATE", nodeList);
    assertThat(caseNode.getNodeListBuild(), is("NODE_LIST_BUILD"));
  }

  @Test
  public void shouldUpdateEndAndErrorNode(){
    NodeList nodeList = mock(NodeList.class);
    Node endNode = new EndNode();
    Node killNode = new EndNode();

    new CaseNode("PREDICATE", nodeList).updateLastNodeTransitionNodes(endNode, killNode);
    verify(nodeList).updateLastNodeTransitionNodes(endNode, killNode);
  }
}