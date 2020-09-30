package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.*;

public class RouteRegistrar {
  final private RouteRegistry routeRegistry;
  final private FileIOInterface fileIO;
  final private DataDirectoryIO dataDirectoryIO;
  final private ReminderIOInterface reminderIO;

  public RouteRegistrar(
      RouteRegistry routeRegistry,
      FileIOInterface fileIO,
      DataDirectoryIO dataDirectoryIO,
      ReminderIOInterface reminderIO
  ) {
    this.routeRegistry = routeRegistry;
    this.fileIO = fileIO;
    this.dataDirectoryIO = dataDirectoryIO;
    this.reminderIO = reminderIO;
  }

  public RouteRegistry registerRoutes() throws Exception {
    route("GET", "/api/simple_get", (RequestInterface req, ResponseInterface res) -> new SimpleGet().get(req, res));
    route("GET", "/api/simple_get_with_body", (RequestInterface req, ResponseInterface res) -> new SimpleGetWithBody().get(req, res));
    route("GET", "/api/admin", (RequestInterface req, ResponseInterface res) -> new Admin().get(req, res));
    route("POST", "/api/reminders", (RequestInterface req, ResponseInterface res) -> new ReminderLists(reminderIO).post(req, res));
    route("POST", "/api/reminders/:list_id", (RequestInterface req, ResponseInterface res) -> new Reminders(reminderIO).post(req, res));
    route("GET", "/api/reminders/:list_id/:reminder_id", (RequestInterface req, ResponseInterface res) -> new Reminders(reminderIO).get(req, res));
    route("PUT", "/api/reminders/:list_id/:reminder_id", (RequestInterface req, ResponseInterface res) -> new Reminders(reminderIO).put(req, res));
    route("DELETE", "/api/reminders/:list_id/:reminder_id", (RequestInterface req, ResponseInterface res) -> new Reminders(reminderIO).delete(req, res));
    route("GET", "/api/images/:image_name", (RequestInterface req, ResponseInterface res) -> new Images(dataDirectoryIO, new UUID()).get(req, res));
    route("POST", "/api/images", (RequestInterface req, ResponseInterface res) -> new Images(dataDirectoryIO, new UUID()).post(req, res));
    route("DELETE", "/api/images/:image_name", (RequestInterface req, ResponseInterface res) -> new Images(dataDirectoryIO, new UUID()).delete(req, res));

    route("GET", "/account", (RequestInterface req, ResponseInterface res) -> new Account().get(req, res));
    route("GET", "/", (RequestInterface req, ResponseInterface res) -> new PublicFiles(fileIO).get(req, res));
    route("GET", "/:file_name", (RequestInterface req, ResponseInterface res) -> new PublicFiles(fileIO).get(req, res));

    return routeRegistry;
  }

  private void route(String method, String url, ControllerMethodInterface controller) throws Exception {
    routeRegistry.registerRoute(method, url, controller);
  }
}
