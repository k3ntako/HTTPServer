package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.fileSystemsIO.DataDirectoryIO;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DataDirectoryIOTest {

  @Test
  void write() throws IOException {
    var fileIO = new FileIOMock();
    var dataDirectory = "./mock/data";
    var dataDirectoryIO = new DataDirectoryIO(fileIO, dataDirectory);

    var bytes = new byte[]{1, 2, 3};
    var strPath = "images/1.png";
    dataDirectoryIO.write(strPath, bytes);

    assertEquals(dataDirectory + "/" + strPath, fileIO.getLastWritePath().toString());
    assertArrayEquals(bytes, (byte[]) fileIO.getLastWrite());
  }

  @Test
  void writeString() throws IOException {
    var fileIO = new FileIOMock();
    var dataDirectory = "./mock/data";
    var dataDirectoryIO = new DataDirectoryIO(fileIO, dataDirectory);

    var str = "Test content!?";
    var strPath = "images/1.png";
    dataDirectoryIO.write(strPath, str);

    assertEquals(dataDirectory + "/" + strPath, fileIO.getLastWritePath().toString());
    assertEquals(str, fileIO.getLastWrite());
  }

  @Test
  void readString() throws IOException {
    var fileIO = new FileIOMock();
    var dataDirectory = "./mock/data";
    var dataDirectoryIO = new DataDirectoryIO(fileIO, dataDirectory);

    var strPath = "text/1.txt";
    dataDirectoryIO.readString(strPath);

    assertEquals(dataDirectory + "/" + strPath, fileIO.getLastReadPath().toString());
  }

  @Test
  void readAllBytes() throws IOException {
    var fileIO = new FileIOMock();
    var dataDirectory = "./mock/data";
    var dataDirectoryIO = new DataDirectoryIO(fileIO, dataDirectory);

    var strPath = "text/1.txt";
    dataDirectoryIO.readAllBytes(strPath);

    assertEquals(dataDirectory + "/" + strPath, fileIO.getLastReadPath().toString());
  }

  @Test
  void delete() throws IOException {
    var fileIO = new FileIOMock();
    var dataDirectory = "./mock/data";
    var dataDirectoryIO = new DataDirectoryIO(fileIO, dataDirectory);

    var strPath = "text/1.txt";
    dataDirectoryIO.delete(strPath);

    assertEquals(dataDirectory + "/" + strPath, fileIO.getLastDeletePath().toString());
  }
}