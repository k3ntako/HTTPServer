package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapperInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class EchoServer {
  private IIOGenerator ioGenerator;

  public EchoServer(IIOGenerator ioGenerator) {
    this.ioGenerator = ioGenerator;
  }

  public void run(ServerSocketWrapperInterface serverSocket) {
    try {
      var clientSocket = serverSocket.accept();

      var io = ioGenerator.generateIO(clientSocket);
      BufferedReader input = (BufferedReader) io.get("bufferedReader");
      PrintWriter output = (PrintWriter) io.get("printWriter");

      String clientInput;
      while ((clientInput = input.readLine()) != null) {
        output.println(clientInput);
      }

      input.close();
      output.close();
      clientSocket.close();
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
