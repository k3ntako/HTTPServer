package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.wrappers.ServerSocketWrapperInterface;

import java.net.Socket;

public class ServerSocketMock implements ServerSocketWrapperInterface {
  public boolean acceptCalled;
  public boolean closeCalled;
  public ServerSocketMock() {
    acceptCalled = false;
    closeCalled = false;
  }

  public Socket accept() {
    acceptCalled = true;
    return null;
  }

  public void close() {
    closeCalled = true;
  }
}
