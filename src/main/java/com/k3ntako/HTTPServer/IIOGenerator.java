package com.k3ntako.HTTPServer;

import java.net.Socket;
import java.util.HashMap;

public interface IIOGenerator {
  HashMap<String, Object> generateIO(Socket clientSocket);
}
