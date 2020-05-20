package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.EchoServerInterface;
import com.k3ntako.HTTPServer.ServerSocketWrapperInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class EchoServerMock implements EchoServerInterface {
  public boolean createAndListenCalled;
  public boolean closeCalled;
  public String sentData;
  private BufferedReader input;
  private PrintWriter output;
  public EchoServerMock(BufferedReader input, PrintWriter output) {
    createAndListenCalled = false;
    closeCalled = false;
    sentData = null;
    this.input = input;
    this.output = output;
  }

  public void createAndListen(ServerSocketWrapperInterface serverSocketWrapper) {
    createAndListenCalled = true;
  }

  public void close(){
    closeCalled = true;
  }

  public String readLine() throws IOException {
    return input.readLine();
  }

  public void sendData(String data) {
    sentData = data;
  }
}
