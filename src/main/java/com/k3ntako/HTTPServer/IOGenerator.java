package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.wrappers.BufferedReaderWrapper;
import com.k3ntako.HTTPServer.wrappers.PrintWriterWrapper;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class IOGenerator {

  public HashMap<String, Object> generateIO(Socket clientSocket) {
    try {
      var io = new HashMap<String, Object>();

      var inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
      var bufferedReader = new BufferedReaderWrapper(inputStreamReader);

      var outputStream = clientSocket.getOutputStream();
      var printWriter = new PrintWriterWrapper(outputStream, true);

      io.put("bufferedReader", bufferedReader);
      io.put("printWriter", printWriter);

      return io;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

}
