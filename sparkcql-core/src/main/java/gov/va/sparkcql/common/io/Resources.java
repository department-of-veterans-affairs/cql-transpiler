package gov.va.sparkcql.common.io;

import java.nio.charset.StandardCharsets;

public class Resources {
  public static String read(String path) {
    var classLoader = Resources.class.getClassLoader();
    var resourceStream = classLoader.getResource(path);
    try {
        var data = resourceStream.openStream();
        return new String(data.readAllBytes(), StandardCharsets.UTF_8);
    } catch (Exception ex) {
      return null;
    }
  }    
}