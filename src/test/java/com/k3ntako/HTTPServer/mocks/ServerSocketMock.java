package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapperInterface;

import java.net.Socket;

public class ServerSocketMock implements ServerSocketWrapperInterface {
  public boolean acceptCalled = false;
  public boolean closeCalled = false;
  private SocketMock mockClientSocket;


  public ServerSocketMock() {
    this.mockClientSocket = new SocketMock();
  }

  public ServerSocketMock(SocketMock mockClientSocket) {
    this.mockClientSocket = mockClientSocket;
  }


  public Socket accept() {
    acceptCalled = true;
    return this.mockClientSocket;
  }

  public void close() {
    closeCalled = true;
  }

  @Override
  public int port() {
    return 5000;
  }
}

