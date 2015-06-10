package com.joozie.core;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigurationTest {
  public static final String PROPERTY_NAME = "PROPERTY_NAME";
  public static final String PROPERTY_VALUE = "PROPERTY_VALUE";
  private static final String ANOTHER_PROPERTY_NAME = "ANOTHER_PROPERTY_NAME";
  private static final String ANOTHER_PROPERTY_VALUE = "ANOTHER_PROPERTY_VALUE";

  public static final String JOB_TRACKER = "JOB-TRACKER";
  public static final String NAME_NODE = "NAME-NODE";

  Configuration config;

  @Before
  public void setup(){
    config = new Configuration();
  }

  @Test
  public void shouldBuildEmptyConfiguration() throws Exception {
    assertThat(new Configuration().build(), is(""));
  }

  @Test
  public void shouldAbleToSetAndBuildJobTracker(){
    assertThat(config.withJobTracker(JOB_TRACKER), is(config));
    assertThat(config.build(), is(
        "<job-tracker>" + JOB_TRACKER + "</job-tracker>"
    ));
  }

  @Test
  public void shouldAbleToSetAndBuildNameNode(){
    assertThat(config.withNameNode(NAME_NODE), is(config));
    assertThat(config.build(), is(
        "<name-node>" + NAME_NODE + "</name-node>"
    ));
  }

  @Test
  public void shouldBuildConfigurationWithSingleProperty() {
    Configuration config = new Configuration(PROPERTY_NAME, PROPERTY_VALUE);
    assertThat(config.build(), is(
      "<configuration>"
        + "<property>"
        + "<name>" + PROPERTY_NAME + "</name>"
        + "<value>" + PROPERTY_VALUE + "</value>"
        + "</property>"
        +"</configuration>"
    ));
  }

  @Test
  public void shouldAbleToSetPropertyKeyValue() {
    Configuration config = new Configuration(PROPERTY_NAME, PROPERTY_VALUE);
    assertThat(config.property(ANOTHER_PROPERTY_NAME, ANOTHER_PROPERTY_VALUE), is(config));
    assertThat(config.build(), anything(
        "<property>"
          + "<name>" + ANOTHER_PROPERTY_NAME + "</name>"
          + "<value>" + ANOTHER_PROPERTY_VALUE + "</value>"
        + "</property>"
    ));
  }
}
