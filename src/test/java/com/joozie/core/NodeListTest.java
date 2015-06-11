package com.joozie.core;

import com.joozie.core.node.EndNode;
import com.joozie.core.node.KillNode;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NodeListTest {
  EndNode endNode = new EndNode();
  KillNode killNode = new KillNode("");
  NodeList nodeList;

  @Before
  public void setup(){
    nodeList = new NodeList(endNode, killNode);
  }
  @Test
  public void shouldAbleToCreateNodeListUsingNoArgConstructor(){
    assertThat(new NodeList(), instanceOf(NodeList.class));
  }

  @Test
  public void shouldAbleToCreateNodeListWithEndAndErrorNodeSpecifiedInConstructor(){
    assertThat(nodeList, instanceOf(NodeList.class));
    assertThat((EndNode) nodeList.getNextNode(), is(endNode));
    assertThat((KillNode) nodeList.getErrorNode(), is(killNode));
  }

  @Test
  public void shouldAddANodeAsFirstNode(){
    TransitiveNode firstNode = mock(TransitiveNode.class);
    when(firstNode.isNextNodeNotSet()).thenReturn(true);
    when(firstNode.isErrorNodeNotSet()).thenReturn(true);

    assertThat(nodeList.getFirstNodeName(), is(""));
    assertThat(nodeList.firstDo(firstNode), is(nodeList));
    assertThat((TransitiveNode) nodeList.getNode(0), is(firstNode));
    assertThat(nodeList.getFirstNodeName(), is(firstNode.getName()));
    verify(firstNode).setNextNode(endNode);
    verify(firstNode).setErrorNode(killNode);
  }

  @Test
  public void shouldAddSecondNodeAndUpdatePreviousNodeTransitionNodes(){
    TransitiveNode firstTransitiveNode = mock(TransitiveNode.class);

    TransitiveNode secondTransitiveNode = mock(TransitiveNode.class);
    when(secondTransitiveNode.isNextNodeNotSet()).thenReturn(true);
    when(secondTransitiveNode.isErrorNodeNotSet()).thenReturn(true);

    nodeList.firstDo(firstTransitiveNode);
    assertThat(nodeList.thenDo(secondTransitiveNode), is(nodeList));

    verify(firstTransitiveNode).setNextNode(secondTransitiveNode);
    verify(secondTransitiveNode).setNextNode(endNode);
    verify(secondTransitiveNode).setErrorNode(killNode);
  }

  @Test
  public void shouldUpdateErrorNodeOfAllNodes(){
    TransitiveNode firstTransitiveNode = mock(TransitiveNode.class);
    when(firstTransitiveNode.isNextNodeNotSet()).thenReturn(true);
    when(firstTransitiveNode.isErrorNodeNotSet()).thenReturn(true);

    TransitiveNode secondTransitiveNode = mock(TransitiveNode.class);
    when(secondTransitiveNode.isNextNodeNotSet()).thenReturn(true);
    when(secondTransitiveNode.isErrorNodeNotSet()).thenReturn(true);

    nodeList.firstDo(firstTransitiveNode).thenDo(secondTransitiveNode);

    nodeList.updateErrorNodeOfEveryNode(endNode);

    verify(firstTransitiveNode).setErrorNode(endNode);
    verify(secondTransitiveNode).setErrorNode(endNode);
  }
}