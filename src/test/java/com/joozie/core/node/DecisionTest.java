package com.joozie.core.node;

import com.joozie.core.Node;
import com.joozie.core.NodeList;
import com.joozie.core.Workflow;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DecisionTest {

  public static final String FIRST_NODE_NAME = "FIRST_NODE_NAME";
  public static final String NODE_LIST_BUILD = "NODE_LIST_BUILD";
  public static final String PREDICATE_IF = "PREDICATE_IF";
  public static final String DECISION_NAME = "DECISION_NAME";
  public static final String PREDICATE_ELSEIF = "PREDICATE_ELSEIF";

  NodeList mockNodeList;
  Node mockNode;

  @Before
  public void setup(){
    mockNodeList = mock(NodeList.class);
    mockNode = mock(Node.class);
    when(mockNodeList.getFirstNodeName()).thenReturn(FIRST_NODE_NAME);
    when(mockNodeList.build()).thenReturn(NODE_LIST_BUILD);
  }

  @Test
  public void shouldCreateDecisionNode(){
    assertThat(new Decision(), instanceOf(Decision.class));
  }

  @Test
  public void shouldAddFirstCaseAsIfTrueAndReturnSelfToContinue(){
    Decision decisionNode = new Decision(DECISION_NAME);

    assertThat(decisionNode.ifTrue(PREDICATE_IF, mockNodeList), is(decisionNode));
    assertThat(decisionNode.build(), is(
      "<decision name='" + DECISION_NAME + "'>"
        + "<switch>"
        + "<case to='" + FIRST_NODE_NAME + "'>" + PREDICATE_IF + "</case>"
        + "</switch>"
        + "</decision>" + NODE_LIST_BUILD
    ));
  }

  @Test
  public void shouldAddElseIfAfterFirstIfAndReturnSelfToContinue(){
    Decision decisionNode = new Decision();

    assertThat(decisionNode.elseIf(PREDICATE_ELSEIF, mockNodeList), is(decisionNode));
  }

  @Test
  public void shouldAddOtherwiseForDefaultCase(){
    Decision decisionNode = new Decision();

    assertThat(decisionNode.otherwise(mockNodeList), is(decisionNode));
  }

  @Test
  public void shouldUseGivenEndNodeAndKillNodeAsDefaultTransitionForGivenNodeListInIfTrue(){
    EndNode endNode = new EndNode();
    KillNode killNode = new KillNode("Kill node message");

    Decision decisionNode = new Decision(endNode, killNode);
    decisionNode.ifTrue(PREDICATE_IF, mockNodeList);

    verify(mockNodeList).updateLastNodeTransitionNodes(endNode, null);
    verify(mockNodeList).updateErrorNodeOfEveryNode(killNode);
  }

  @Test
  public void shouldUseGivenEndNodeAndKillNodeAsDefaultTransitionForGivenNodeListInElseIf(){
    EndNode endNode = new EndNode();
    KillNode killNode = new KillNode("Kill node message");

    Decision decisionNode = new Decision(endNode, killNode);
    decisionNode.elseIf(PREDICATE_ELSEIF, mockNodeList);

    verify(mockNodeList).updateLastNodeTransitionNodes(endNode, null);
    verify(mockNodeList).updateErrorNodeOfEveryNode(killNode);
  }

  @Test
  public void shouldUseGivenEndNodeAndKillNodeAsDefaultTransitionForGivenNodeListInOtherwise(){
    EndNode endNode = new EndNode();
    KillNode killNode = new KillNode("Kill node message");

    Decision decisionNode = new Decision(endNode, killNode);
    decisionNode.otherwise(mockNodeList);

    verify(mockNodeList).updateLastNodeTransitionNodes(endNode, null);
    verify(mockNodeList).updateErrorNodeOfEveryNode(killNode);
  }

  @Test
  public void shouldUpdateAllCaseNodeNodeListLastNodeTransitionNodes(){
    EndNode endNode = new EndNode();
    KillNode killNode = new KillNode("Kill node message");

    Decision decisionNode = new Decision();
    decisionNode.ifTrue(PREDICATE_IF, mockNodeList);

    decisionNode.updateLastNodeTransitionNodes(endNode, killNode);

    verify(mockNodeList).updateLastNodeTransitionNodes(endNode, killNode);
  }
}