package com.k3ntako.HTTPServer;

import java.util.LinkedHashMap;

public interface YamlIOInterface {
  LinkedHashMap<String, Object> read(String fileName) throws Exception;
}
