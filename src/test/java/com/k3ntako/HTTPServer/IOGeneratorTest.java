package com.k3ntako.HTTPServer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

class IOGeneratorTest {

  @Test
  void generateIOWithStringReaderAndWriter() {
    var ioGenerator = new IOGenerator();

    var reader = new StringReader("Something\n");
    var writer = new StringWriter();
    var io = ioGenerator.generateIO(reader, writer);

    var bufferedReader = io.get("bufferedReader");
    assertTrue(bufferedReader instanceof BufferedReader, "Expected an instance of BufferedReader");

    var printWriter = io.get("printWriter");
    assertTrue(printWriter instanceof PrintWriter, "Expected an instance of PrintWriter");
  }

  @Test
  void generateIOWithInputAndOutputStreams() {
    var ioGenerator = new IOGenerator();

    var mockInputStream = new InputStream() {
      @Override
      public int read() {
        return 0;
      }
    };

    var mockOutputStream = new OutputStream() {

      @Override
      public void write(int b) {

      }
    };

    var reader = new InputStreamReader(mockInputStream);
    var io = ioGenerator.generateIO(reader, mockOutputStream);

    var bufferedReader = io.get("bufferedReader");
    assertTrue(bufferedReader instanceof BufferedReader, "Expected an instance of BufferedReader");

    var printWriter = io.get("printWriter");
    assertTrue(printWriter instanceof PrintWriter, "Expected an instance of PrintWriter");
  }

}