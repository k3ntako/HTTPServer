package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.BufferedReaderWrapper;
import com.k3ntako.HTTPServer.wrappers.PrintWriterWrapper;
import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class IO implements IOInterface {
  private BufferedReaderWrapper input;
  private PrintWriterWrapper output;
  private Socket clientSocket;
  private ServerSocketWrapper serverSocket;

  public IO(ServerSocketWrapper serverSocket) {
    this.serverSocket = serverSocket;
  }

  public Socket accept() {
    return serverSocket.accept();
  }

  public void startConnection(Socket clientSocket) {
    try {
      this.clientSocket = clientSocket;

      var inputStreamReader = new InputStreamReader(this.clientSocket.getInputStream());
      input = new BufferedReaderWrapper(inputStreamReader);

      var outputStream = this.clientSocket.getOutputStream();
      output = new PrintWriterWrapper(outputStream, true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String readLine() {
    return input.readLine();
  }

  public void sendData(String data) {
    output.sendData(data);
  }

  public void close() {
    try {
      input.close();
      output.close();
      clientSocket.close();
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
