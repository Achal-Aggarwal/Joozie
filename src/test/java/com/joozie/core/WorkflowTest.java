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
        + "<start to='" + workflow.getEndNodeName() + "'/>"
        + "<end name='" + workflow.getEndNodeName() + "'/>"
        + "<kill name='" + workflow.getKillNodeName() + "'><message>" + workflow.getKillNodeMessage() + "</message></kill>"
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
  public void shouldAbleToAddAndSetFirstActionAndCorrespondingEnd(){
    Action mockAction = mock(Action.class);

    when(mockAction.build()).thenReturn("dummyAction");
    when(mockAction.getName()).thenReturn("dummyActionName");
    when(mockAction.isSuccessNotSet()).thenReturn(true);
    when(mockAction.isErrorNotSet()).thenReturn(true);

    assertThat(workflow.firstDo(mockAction), is(workflow));

    verify(mockAction).onSuccess(workflow.getEndNodeName());
    verify(mockAction).onError(workflow.getKillNodeName());

    assertThat(workflow.build(), containsString("<start to='dummyActionName'/>dummyAction"));
  }

  @Test
  public void shouldAbleToAddSubsequentActionsAfterFirstOne(){
    Action mockFirstAction = mock(Action.class);
    when(mockFirstAction.getName()).thenReturn("firstActionName");
    when(mockFirstAction.build()).thenReturn("firstAction");
    when(mockFirstAction.isSuccessNotSet()).thenReturn(true);
    when(mockFirstAction.isErrorNotSet()).thenReturn(true);

    Action mockSecondAction = mock(Action.class);
    when(mockSecondAction.getName()).thenReturn("secondActionName");
    when(mockSecondAction.build()).thenReturn("secondAction");
    when(mockSecondAction.isSuccessNotSet()).thenReturn(true);
    when(mockSecondAction.isErrorNotSet()).thenReturn(true);

    assertThat(workflow.firstDo(mockFirstAction), is(workflow));
    assertThat(workflow.thenDo(mockSecondAction), is(workflow));

    verify(mockFirstAction).onSuccess(workflow.getEndNodeName());
    verify(mockFirstAction).onSuccess(mockSecondAction.getName());
    verify(mockSecondAction).onSuccess(workflow.getEndNodeName());
    verify(mockSecondAction).onError(workflow.getKillNodeName());
  }
}