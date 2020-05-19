package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.ServerSocketWrapperInterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketWrapperMock implements ServerSocketWrapperInterface {
  private int port;
  public int acceptCalledNum;
  public ServerSocketWrapperMock(int specifiedPort) {
    port = specifiedPort;
    acceptCalledNum = 0;
  }

  public Socket accept(){
    acceptCalledNum = acceptCalledNum + 1;
    return new Socket();
  }
}
