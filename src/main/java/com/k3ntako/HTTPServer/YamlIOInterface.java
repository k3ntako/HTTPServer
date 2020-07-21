package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;

public interface YamlIOInterface {
  LinkedHashMap<String, Object> read(String fileName) throws IOException, URISyntaxException;
}
