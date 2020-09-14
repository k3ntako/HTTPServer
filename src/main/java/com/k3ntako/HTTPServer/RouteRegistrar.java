package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.controllers.*;

public class RouteRegistrar {
  final private RouteRegistry routeRegistry;
  final private FileIOInterface fileIO;
  final private ReminderIOInterface reminderIO;

  public RouteRegistrar(RouteRegistry routeRegistry, FileIOInterface fileIO, ReminderIOInterface reminderIO) {
    this.routeRegistry = routeRegistry;
    this.fileIO = fileIO;
    this.reminderIO = reminderIO;
  }

  public RouteRegistry registerRoutes() throws Exception {
    routeRegistry.registerRoute("GET", "/simple_get", (RequestInterface req) -> new SimpleGet().get(req));
    routeRegistry.registerRoute("GET", "/simple_get_with_body", (RequestInterface req) -> new SimpleGetWithBody().get(req));
    routeRegistry.registerRoute("GET", "/admin", (RequestInterface req) -> new Admin().get(req));
    routeRegistry.registerRoute("GET", "/account", (RequestInterface req) -> new Account().get(req));
    routeRegistry.registerRoute("POST", "/reminders", (RequestInterface req) -> new ReminderLists(reminderIO).post(req));
    routeRegistry.registerRoute("POST", "/reminders/:list_id", (RequestInterface req) -> new Reminders(reminderIO).post(req));
    routeRegistry.registerRoute("GET", "/reminders/:list_id/:reminder_id", (RequestInterface req) -> new Reminders(reminderIO).get(req));
    routeRegistry.registerRoute("PUT", "/reminders/:list_id/:reminder_id", (RequestInterface req) -> new Reminders(reminderIO).put(req));
    routeRegistry.registerRoute("DELETE", "/reminders/:list_id/:reminder_id", (RequestInterface req) -> new Reminders(reminderIO).delete(req));

    return routeRegistry;
  }
}
