package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.UUIDMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TextFileTest {

  @Test
  void saveFile() throws IOException {
    var fileIO = new FileIOMock();
    var textFile = new TextFile(fileIO, new UUIDMock("test_file_name"));

    var fileName = textFile.saveFile("hello!");

    assertEquals("test_file_name", fileName);

    var pathStr = fileIO.getLastWritePath().toString();
    assertEquals("./data/test_file_name.txt", pathStr);
    assertEquals("hello!", fileIO.getLastWrite());
  }
}