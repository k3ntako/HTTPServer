package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.mocks.FileIOMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DataDirectoryIOTest {

  @Test
  void write() throws IOException {
    var fileIO = new FileIOMock();
    var dataDirectory = "./data";
    var dataDirectoryIO = new DataDirectoryIO(fileIO, dataDirectory);

    var bytes = new byte[]{1, 2, 3};
    var strPath = "images/1.png";
    dataDirectoryIO.write(strPath, bytes);

    assertEquals("./data/images/1.png", fileIO.getLastWritePath().toString());
    assertArrayEquals(bytes, (byte[]) fileIO.getLastWrite());
  }

  @Test
  void writeString() throws IOException {
    var fileIO = new FileIOMock();
    var dataDirectory = "./data";
    var dataDirectoryIO = new DataDirectoryIO(fileIO, dataDirectory);

    var str = "Test content!?";
    var strPath = "images/1.png";
    dataDirectoryIO.write(strPath, str);

    assertEquals("./data/images/1.png", fileIO.getLastWritePath().toString());
    assertEquals(str, fileIO.getLastWrite());
  }

  @Test
  void read() throws IOException {
    var fileIO = new FileIOMock();
    var dataDirectory = "./data";
    var dataDirectoryIO = new DataDirectoryIO(fileIO, dataDirectory);

    var strPath = "text/1.txt";
    dataDirectoryIO.read(strPath);

    assertEquals("./data/text/1.txt", fileIO.getLastReadPath().toString());
  }
}