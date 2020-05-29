package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.IOGeneratorInterface;
import com.k3ntako.HTTPServer.wrappers.PrintWriterWrapper;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class IOGeneratorMock implements IOGeneratorInterface {
  private String clientInput;
  private HashMap<String, Object> io;

  public IOGeneratorMock(String clientInput) {
    this.clientInput = clientInput;
  }

  public HashMap<String, Object> generateIO(Socket clientSocket) {
    var io = new HashMap<String, Object>();

    var bufferedReader = new BufferedReader(new StringReader(this.clientInput));
    var printWriter = new PrintWriterWrapperMock();

    io.put("bufferedReader", bufferedReader);
    io.put("printWriter", printWriter);

    this.io = io;

    return io;
  }

  public BufferedReader getBufferedReader(){
    return (BufferedReader) this.io.get("bufferedReader");
  }

  public PrintWriterWrapperMock getPrintWriter(){
    return (PrintWriterWrapperMock) this.io.get("printWriter");
  }
}
