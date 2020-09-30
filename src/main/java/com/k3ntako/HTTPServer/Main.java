package com.k3ntako.HTTPServer;

import org.yaml.snakeyaml.Yaml;

public class Main {
  public static void main(String[] args) throws Exception {
    var fileIO = new FileIO();
    var yamlIO = new YamlIO(fileIO, new Yaml());

    var serverGenerator = new ServerGenerator(fileIO, yamlIO);

    var server = serverGenerator.generate();

    System.out.println("Server listening at port " + server.port() + "...");
    while (true) {
      server.run();
    }
  }
}
