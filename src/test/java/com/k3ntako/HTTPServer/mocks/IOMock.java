package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.EchoServerInterface;
import com.k3ntako.HTTPServer.IOInterface;
import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapperInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class IOMock implements IOInterface {
  private BufferedReader input;
  private PrintWriter output;

  public boolean acceptCalled;
  public boolean startConnectionCalled;
  public boolean closeCalled;
  public ArrayList<String> sentData;

  public IOMock(BufferedReader input, PrintWriter output) {
    acceptCalled = false;
    startConnectionCalled = false;
    closeCalled = false;
    sentData = new ArrayList<>();
    this.input = input;
    this.output = output;
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

  public void startConnection(Socket clientSocket) {
    startConnectionCalled = true;
  }

  public Socket accept() {
    acceptCalled = true;
    return null;
  }

  public String readLine() {
    try {
      return input.readLine();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void sendData(String data) {
    sentData.add(data);
  }
}
