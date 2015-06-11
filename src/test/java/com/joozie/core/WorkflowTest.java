package com.joozie.core;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.Mockito.*;

public class WorkflowTest {

  public static final String WORKFLOW_NAME = "WORKFLOW-NAME";
  public static final String GLOBAL_CONFIGURATIONS = "GLOBAL_CONFIGURATIONS";

  Workflow workflow;

  @Before
  public void setup(){
    workflow = new Workflow(WORKFLOW_NAME);
  }

  @Test
  public void shouldBuildBasicWorkflowAppXmlCode(){
    assertThat(workflow.build(), is(
      "<workflow-app name='" + WORKFLOW_NAME + "' xmlns='uri:oozie:workflow:0.1'>"
        + "<start to='" + workflow.getEndNode().getName() + "'/>"
        + "<end name='" + workflow.getEndNode().getName() + "'/>"
        + "<kill name='" + workflow.getKillNode().getName() + "'><message>" + workflow.getKillNodeMessage() + "</message></kill>"
      + "</workflow-app>"
    ));
  }

  @Test
  public void shouldAbleToSetGlobalConfiguration(){
    Configuration mockConfig = mock(Configuration.class);
    when(mockConfig.build()).thenReturn(GLOBAL_CONFIGURATIONS);

    assertThat(workflow.usingConfig(mockConfig), is(workflow));
    assertThat(workflow.build().contains(
      "<global>" + GLOBAL_CONFIGURATIONS + "</global>"
    ), is(true));
  }

  @Test
  public void shouldAbleToAddFirstActionAndSetCorrespondingEnd(){
    Action mockAction = mock(Action.class);

    when(mockAction.build()).thenReturn("dummyAction");
    when(mockAction.getName()).thenReturn("dummyActionName");
    when(mockAction.isNextNodeNotSet()).thenReturn(true);
    when(mockAction.isErrorNodeNotSet()).thenReturn(true);

    assertThat(workflow.firstDo(mockAction), is(workflow));

    verify(mockAction).setNextNode(workflow.getEndNode());
    verify(mockAction).setErrorNode(workflow.getKillNode());

    assertThat(workflow.build(), containsString("<start to='dummyActionName'/>dummyAction"));
  }

  @Test
  public void shouldAbleToAddSubsequentActionsAfterFirstOne(){
    Action mockFirstAction = mock(Action.class);
    when(mockFirstAction.getName()).thenReturn("firstActionName");
    when(mockFirstAction.build()).thenReturn("firstAction");
    when(mockFirstAction.isNextNodeNotSet()).thenReturn(true);
    when(mockFirstAction.isErrorNodeNotSet()).thenReturn(true);

    Action mockSecondAction = mock(Action.class);
    when(mockSecondAction.getName()).thenReturn("secondActionName");
    when(mockSecondAction.build()).thenReturn("secondAction");
    when(mockSecondAction.isNextNodeNotSet()).thenReturn(true);
    when(mockSecondAction.isErrorNodeNotSet()).thenReturn(true);

    assertThat(workflow.firstDo(mockFirstAction), is(workflow));
    assertThat(workflow.thenDo(mockSecondAction), is(workflow));

    verify(mockFirstAction).setNextNode(workflow.getEndNode());
    verify(mockFirstAction).setNextNode(mockSecondAction);
    verify(mockSecondAction).setNextNode(workflow.getEndNode());
    verify(mockSecondAction).setErrorNode(workflow.getKillNode());
  }
}