package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.YamlIOMock;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class ServerGeneratorTest {

  @Test
  void generate() throws Exception {
    var fileIO = new FileIOMock("---" + "port");

    var mockConfig = new LinkedHashMap<String, Object>();
    mockConfig.put("port", 3000);
    var yamlIO = new YamlIOMock(mockConfig);

    var serverGenerator = new ServerGenerator(fileIO, yamlIO);

    var server = serverGenerator.generate();

    assertNotNull(server);
  }
}