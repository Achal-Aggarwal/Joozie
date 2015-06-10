package com.joozie.core;

import lombok.NoArgsConstructor;

import java.util.HashMap;

@NoArgsConstructor
public class Configuration {
  HashMap<String, String> properties = new HashMap<String, String>();

  private String jobTracker;
  private String nameNode;

  public Configuration(String propertyName, String propertyValue) {
    property(propertyName, propertyValue);
  }

  public Configuration withJobTracker(String jobTracker) {
    this.jobTracker = jobTracker;
    return this;
  }

  private String getJobTrackerXMLString(){
    if (jobTracker == null){
      return "";
    }

    return "<job-tracker>" + jobTracker + "</job-tracker>";
  }

  public Configuration withNameNode(String nameNode) {
    this.nameNode = nameNode;
    return this;
  }

  private String getNameNodeXMLString(){
    if (nameNode == null){
      return "";
    }

    return "<name-node>" + nameNode + "</name-node>";
  }

  public Configuration property(String propertyName, String propertyValue) {
    properties.put(propertyName, propertyValue);

    return this;
  }

  private String getPropertyXmlString(String name, String value) {
    return
      "<property>"
        +   "<name>" + name + "</name>"
        +   "<value>" + value + "</value>"
        + "</property>";
  }

  public String build() {
    StringBuilder result = new StringBuilder();

    result.append(getJobTrackerXMLString());
    result.append(getNameNodeXMLString());

    if (properties.size() > 0){
      result.append("<configuration>");

      for (String propertyName : properties.keySet()) {
        result.append(getPropertyXmlString(propertyName, properties.get(propertyName)));
      }

      result.append("</configuration>");
    }

    return result.toString();
  }
}
