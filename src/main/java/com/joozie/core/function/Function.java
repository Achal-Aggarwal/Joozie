package com.joozie.core.function;

public class Function {
  public static String generate(String funcName, String... args){
    StringBuilder result = new StringBuilder();

    result.append(funcName + "(");

    for (String arg : args) {
      result.append(arg + ", ");
    }

    result.delete(result.lastIndexOf(", "), result.length());

    result.append(")");

    return result.toString();
  }
}
