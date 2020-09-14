package com.k3ntako.HTTPServer.wrappers;

import java.io.OutputStream;
import java.io.PrintWriter;


public class PrintWriterWrapper implements PrintWriterWrapperInterface {
  final private PrintWriter printerWriter;

  public PrintWriterWrapper(OutputStream outputStream, boolean autoFlush) {
    printerWriter = new PrintWriter(outputStream, autoFlush);
  }

  public void sendData(String data) {
    printerWriter.println(data);
  }

  public void close() {
    printerWriter.close();
  }
}