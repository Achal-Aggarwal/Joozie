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
import static org.mockito.Mockito.when;

public class DecisionTest {

  public static final String FIRST_NODE_NAME = "FIRST_NODE_NAME";
  public static final String NODE_LIST_BUILD = "NODE_LIST_BUILD";
  public static final String PREDICATE_IF = "PREDICATE_IF";
  public static final String DECISION_NAME = "DECISION_NAME";

  NodeList mockNodeList;
  @Before
  public void setup(){
    mockNodeList = mock(NodeList.class);
    when(mockNodeList.getFirstNodeName()).thenReturn(FIRST_NODE_NAME);
    when(mockNodeList.build()).thenReturn(NODE_LIST_BUILD);
  }

  @Test
  public void shouldCreateDecisionNodeWithAContextToReturnTo(){
    assertThat(new Decision(null), instanceOf(Decision.class));
  }

  @Test
  public void shouldAddFirstCaseAsIfTrueAndReturnSelfToContinue(){
    Decision decisionNode = new Decision(DECISION_NAME);

    assertThat(decisionNode.ifTrue(PREDICATE_IF, mockNodeList), is(decisionNode));
    assertThat(decisionNode.build(), is(
      "<decision name='" + DECISION_NAME + "'>"
        +"<switch>"
          + "<case to='"+ FIRST_NODE_NAME + "'>" + PREDICATE_IF + "</case>"
        + "</switch>"
      +"</decision>" + NODE_LIST_BUILD
    ));
  }

  @Test
  public void shouldAddElseIfAfterFirstIfAndReturnSelfToContinue(){
    Decision decisionNode = new Decision();

    assertThat(decisionNode.ifTrue(PREDICATE_IF, mockNodeList), is(decisionNode));
    assertThat(decisionNode.elseIf("PREDICATE_ELSEIF", new NodeList()), is(decisionNode));
  }

  @Test
  public void shouldAddOtherWiseForDefaultCase(){
    Decision decisionNode = new Decision();

    assertThat(decisionNode.ifTrue(PREDICATE_IF, new NodeList()), is(decisionNode));
    decisionNode.otherwise(mockNodeList);
  }
}