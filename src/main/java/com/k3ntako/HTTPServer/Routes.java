package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.routes.SimpleGet;
import com.k3ntako.HTTPServer.routes.SimpleGetWithBody;
import com.k3ntako.HTTPServer.routes.SimpleRedirect;

import java.util.HashMap;

public class Routes {
  private HashMap<String, RouteInterface> routes = new HashMap<>();

  public Routes() {
    routes.put("/simple_get", new SimpleGet());
    routes.put("/simple_get_with_body", new SimpleGetWithBody());
    routes.put("/redirect", new SimpleRedirect());
  }

  public RouteInterface get (String url) {
    return routes.get(url);
  }
}
