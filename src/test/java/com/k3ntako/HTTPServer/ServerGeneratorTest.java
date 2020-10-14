package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.YamlIOMock;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class ServerGeneratorTest {

  @Test
  void generate() throws Exception {
    var mockConfigStr = "---\n" +
        "port: 5000\n" +
        "data_directory: ./mock/data";
    var fileIO = new FileIOMock(mockConfigStr);

    var mockConfig = new LinkedHashMap<String, Object>();
    mockConfig.put("port", 5000);
    mockConfig.put("data_directory", "./mock/data");
    var yamlIO = new YamlIOMock(mockConfig);

    var serverGenerator = new ServerGenerator(fileIO, yamlIO);

    var server = serverGenerator.generate();

    assertNotNull(server);
  }
}