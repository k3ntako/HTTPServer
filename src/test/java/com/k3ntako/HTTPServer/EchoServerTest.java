package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.IOGeneratorMock;
import com.k3ntako.HTTPServer.mocks.IOMock;
import com.k3ntako.HTTPServer.mocks.ServerSocketMock;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EchoServerTest {
  @Test
  void echoServerReturnsInput() {
    var ioGeneratorMock = new IOGeneratorMock();
    var app = new EchoServer(ioGeneratorMock);
    var socket = new ServerSocketMock();
    app.run(socket);

    assertTrue(socket.acceptCalled);
    assertTrue(socket.closeCalled);
  }

}