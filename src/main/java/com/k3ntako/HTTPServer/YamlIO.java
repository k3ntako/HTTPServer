package com.k3ntako.HTTPServer;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;

public class YamlIO {
  private FileIOInterface fileIO;
  private Yaml yaml;

  public YamlIO(FileIOInterface fileIO, Yaml yaml) {
    this.fileIO = fileIO;
    this.yaml = yaml;
  }

  public LinkedHashMap<String, Object> read(String fileName) throws IOException, URISyntaxException {
    var yamlStr = fileIO.getResource(fileName);

    if(yamlStr == null) {
      return null;
    }

    return yaml.load(yamlStr);
  }
}
