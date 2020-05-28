package com.k3ntako.HTTPServer;

import java.io.*;
import java.util.HashMap;

public class IOGenerator {


  public HashMap<String, Object> generateIO(Reader reader, OutputStream writer) {
    return generateIOReturnValue(reader, new PrintWriter(writer));
  }

  public HashMap<String, Object> generateIO(Reader reader, Writer writer) {
    return generateIOReturnValue(reader, new PrintWriter(writer, true));
  }

  private HashMap<String, Object> generateIOReturnValue(Reader reader, PrintWriter printWriter) {
    var io = new HashMap<String, Object>();
    io.put("printWriter", new PrintWriter(printWriter, true));
    io.put("bufferedReader", new BufferedReader(reader));

    return io;
  }

}
