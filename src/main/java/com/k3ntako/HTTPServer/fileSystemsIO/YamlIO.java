package com.k3ntako.HTTPServer.fileSystemsIO;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.LinkedHashMap;

public class YamlIO implements YamlIOInterface {
  private FileIOInterface fileIO;
  private Yaml yaml;

  public YamlIO(FileIOInterface fileIO, Yaml yaml) {
    this.fileIO = fileIO;
    this.yaml = yaml;
  }

  @Override
  public LinkedHashMap<String, Object> read(String fileName) throws IOException {
    var yamlStr = fileIO.getResource(fileName);

    if(yamlStr == null) {
      return null;
    }

    return yaml.load(yamlStr);
  }
}
