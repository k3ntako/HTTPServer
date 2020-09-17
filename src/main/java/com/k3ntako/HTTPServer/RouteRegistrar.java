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
    routeRegistry.registerRoute("GET", "/api/simple_get", (RequestInterface req) -> new SimpleGet().get(req));
    routeRegistry.registerRoute("GET", "/api/simple_get_with_body", (RequestInterface req) -> new SimpleGetWithBody().get(req));
    routeRegistry.registerRoute("GET", "/api/admin", (RequestInterface req) -> new Admin().get(req));
    routeRegistry.registerRoute("POST", "/api/reminders", (RequestInterface req) -> new ReminderLists(reminderIO).post(req));
    routeRegistry.registerRoute("POST", "/api/reminders/:list_id", (RequestInterface req) -> new Reminders(reminderIO).post(req));
    routeRegistry.registerRoute("GET", "/api/reminders/:list_id/:reminder_id", (RequestInterface req) -> new Reminders(reminderIO).get(req));
    routeRegistry.registerRoute("PUT", "/api/reminders/:list_id/:reminder_id", (RequestInterface req) -> new Reminders(reminderIO).put(req));
    routeRegistry.registerRoute("DELETE", "/api/reminders/:list_id/:reminder_id", (RequestInterface req) -> new Reminders(reminderIO).delete(req));
    routeRegistry.registerRoute("POST", "/api/images", (RequestInterface req) -> new Images(dataDirectoryIO, new UUID()).post(req));

    routeRegistry.registerRoute("GET", "/account", (RequestInterface req) -> new Account().get(req));
    routeRegistry.registerRoute("GET", "/", (RequestInterface req) -> new PublicFiles(fileIO).get(req));
    routeRegistry.registerRoute("GET", "/:file_name", (RequestInterface req) -> new PublicFiles(fileIO).get(req));

    return routeRegistry;
  }
}
