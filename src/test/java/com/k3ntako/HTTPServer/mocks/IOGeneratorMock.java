package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.IOGeneratorInterface;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class IOGeneratorMock implements IOGeneratorInterface {
  private String clientInput;

  public IOGeneratorMock(String clientInput) {
    this.clientInput = clientInput;
  }

  public HashMap<String, Object> generateIO(Socket clientSocket) {
    var io = new HashMap<String, Object>();

    var bufferedReader = new BufferedReader(new StringReader(this.clientInput));
    var printWriter = new PrintWriter(new StringWriter(), true);

    io.put("bufferedReader", bufferedReader);
    io.put("printWriter", printWriter);

    return io;
  }
}
