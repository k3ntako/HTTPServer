package com.k3ntako.HTTPServer;

import java.io.BufferedReader;

public interface RequestHandlerInterface {
  RequestInterface handleRequest(BufferedReader bufferedReader);
}
