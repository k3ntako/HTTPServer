package com.k3ntako.HTTPServer.wrappers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferedReaderWrapper implements BufferedReaderWrapperInterface {
  private BufferedReader bufferedReader;

  public BufferedReaderWrapper(InputStreamReader inputStreamReader) {
    bufferedReader = new BufferedReader(inputStreamReader);
  }

  public String readLine() {
    try {
      return bufferedReader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void close() {
    try {
      bufferedReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
