package com.k3ntako.HTTPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoServer implements EchoServerInterface {
  private BufferedReader input;
  private PrintWriter output;
  private Socket clientSocket;
  private ServerSocketWrapperInterface serverSocketWrapper;

  public void createAndListen(ServerSocketWrapperInterface serverSocketWrapper) {
    try {
      this.serverSocketWrapper = serverSocketWrapper;

      clientSocket = serverSocketWrapper.accept();

      input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      output = new PrintWriter(clientSocket.getOutputStream(), true);
    } catch (IOException e) {
      e.printStackTrace();
    }
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
    output.println(data);
  }

  public void close() {
    try {
      output.close();
      input.close();
      clientSocket.close();
      serverSocketWrapper.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
