package com.joozie.core;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ActionTest{
  @Test
  public void shouldBuildWithGivenNameAndOkAndErrorNodes(){
    DummyAction dummyAction = new DummyAction("DummyName");
    assertThat((DummyAction) dummyAction.onSuccess("SuccessNode"), is(dummyAction));
    assertThat((DummyAction) dummyAction.onError("ErrorNode"), is(dummyAction));
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
    assertThat(new DummyAction().isSuccessNotSet(), is(true));
    assertThat(new DummyAction().onSuccess("ON_SUCCESS").isSuccessNotSet(), is(false));
  }

  @Test
  public void shouldReturnTrueIfErrorNodeNameIsNotSet(){
    assertThat(new DummyAction().isErrorNotSet(), is(true));
    assertThat(new DummyAction().onError("ON_ERROR").isErrorNotSet(), is(false));
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