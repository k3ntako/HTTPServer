package com.k3ntako.HTTPServer.fileSystemsIO;

import java.util.LinkedHashMap;

public interface YamlIOInterface {
  LinkedHashMap<String, Object> read(String fileName) throws Exception;
}
