package com.k3ntako.HTTPServer.mocks;

import com.k3ntako.HTTPServer.RouteRegistrarInterface;
import com.k3ntako.HTTPServer.RouteRegistry;

public class RouteRegistrarMock implements RouteRegistrarInterface {
  @Override
  public RouteRegistry registerRoutes() {
    return new RouteRegistry();
  }
}
