package com.joozie.core;

import com.joozie.core.node.EndNode;
import com.joozie.core.node.KillNode;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ActionTest{
  EndNode endNode = new EndNode("SuccessNode");
  KillNode killNode = new KillNode("ErrorNode", "");

  @Test
  public void shouldBuildWithGivenNameAndOkAndErrorNodes(){
    DummyAction dummyAction = new DummyAction("DummyName");
    assertThat((DummyAction) dummyAction.onSuccess(endNode), is(dummyAction));
    assertThat((DummyAction) dummyAction.onError(killNode), is(dummyAction));
    assertThat(dummyAction.build(), is(
      "<action name='DummyName'>"
        + "DummyActionContent"
        + "<ok to='SuccessNode'/>"
        + "<error to='ErrorNode'/>"
      + "</action>"
    ));
  }

  @Test
  public void shouldReturnTrueIfSuccessNodeNameIsNotSet(){
    assertThat(new DummyAction().isNextNodeNotSet(), is(true));
    assertThat(new DummyAction().onSuccess(endNode).isNextNodeNotSet(), is(false));
  }

  @Test
  public void shouldReturnTrueIfErrorNodeNameIsNotSet(){
    assertThat(new DummyAction().isErrorNodeNotSet(), is(true));
    assertThat(new DummyAction().onError(killNode).isErrorNodeNotSet(), is(false));
  }
}

class DummyAction extends Action{
  public DummyAction() {
    super();
  }

  public DummyAction(String dummyName) {
    super(dummyName);
  }

  @Override
  protected String getActionXmlString() {
    return "DummyActionContent";
  }
}