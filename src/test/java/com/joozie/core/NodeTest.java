package com.joozie.core;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class NodeTest {
  @Test
  public void shouldCreateActionWithGivenName(){
    assertThat(new DummyNode("DummyName").getName(), is("DummyName"));
  }

  @Test
  public void shouldCreateNodeWithAutogeneratedName(){
    assertThat(new DummyNode().getName(), containsString("NODE-"));
  }
}

class DummyNode extends Node{
  public DummyNode() {
    super();
  }

  public DummyNode(String name) {
    super(name);
  }

  @Override
  public String build() {
    return "";
  }
}