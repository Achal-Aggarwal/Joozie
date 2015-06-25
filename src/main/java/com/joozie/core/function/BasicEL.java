package com.joozie.core.function;

import static com.joozie.core.function.Function.generate;

public class BasicEL {
  public static String firstNotNull(String value1, String value2){
    return generate("firstNotNull", value1, value2);
  }

  public static String concat(String string1, String string2){
    return generate("concat", string1, string2);
  }

  public static String replaceAll(String src, String regex, String replacement){
    return generate("replaceAll", src, regex, replacement);
  }
}
