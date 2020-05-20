package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.EchoServerInterface;
import com.k3ntako.HTTPServer.ServerSocketWrapperInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class EchoServerMock implements EchoServerInterface {
  public boolean createAndListenCalled;
  public boolean closeCalled;
  public ArrayList<String> sentData;
  private BufferedReader input;
  private PrintWriter output;
  public EchoServerMock(BufferedReader input, PrintWriter output) {
    createAndListenCalled = false;
    closeCalled = false;
    sentData = new ArrayList<>();
    this.input = input;
    this.output = output;
  }

  public void createAndListen(ServerSocketWrapperInterface serverSocketWrapper) {
    createAndListenCalled = true;
  }

  public void close(){
    closeCalled = true;

    try {
      input.close();
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
      throw new Error("Mock class threw error while closing");
    }
  }

  public String readLine() throws IOException {
    return input.readLine();
  }

  public void sendData(String data) {
    sentData.add(data);
  }
}
