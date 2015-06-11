package com.joozie.core;

import com.joozie.core.node.EndNode;
import com.joozie.core.node.KillNode;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TransitiveNodeTest {


  @Test
  public void shouldAbleToSetAndGetNextAndErrorNode(){
    TransitiveNode node = new DummyTransitiveNode();

    EndNode endNode = new EndNode();

    node.setNextNode(endNode);
    assertThat((EndNode) node.getNextNode(), is(endNode));

    KillNode killNode = new KillNode("");
    node.setErrorNode(killNode);
    assertThat((KillNode) node.getErrorNode(), is(killNode));
  }
}

class DummyTransitiveNode extends TransitiveNode{
  public DummyTransitiveNode() {
    super();
  }

  @Override
  public String build() {
    return "";
  }
}