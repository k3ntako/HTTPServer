package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.fileSystemsIO.DataDirectoryIO;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import org.junit.jupiter.api.Test;

import java.io.File;
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
  void readAllBytesById() throws IOException {
    var file = new File("./mock/data/123abc.png");

    var fileIO = new FileIOMock(new byte[]{1, 2, 3}, new File[]{file});
    var dataDirectory = "./mock/data";
    var dataDirectoryIO = new DataDirectoryIO(fileIO, dataDirectory);

    var id = "123abc";
    dataDirectoryIO.readAllBytesById("", id);

    assertEquals(dataDirectory + "/" + id + ".png", fileIO.getLastReadPath().toString());
  }

  @Test
  void readAllBytesByIdNested() throws IOException {
    var file = new File("./mock/data/123abc.png");

    var fileIO = new FileIOMock(new byte[]{1, 2, 3}, new File[]{file});
    var dataDirectory = "./mock/data";
    var dataDirectoryIO = new DataDirectoryIO(fileIO, dataDirectory);

    var id = "123abc";
    dataDirectoryIO.readAllBytesById("images", id);

    assertEquals(dataDirectory + "/" + id + ".png", fileIO.getLastReadPath().toString());
  }

  @Test
  void readAllBytesByIdReturnsNull() throws IOException {
    var fileIO = new FileIOMock(null, null);
    var dataDirectoryIO = new DataDirectoryIO(fileIO, "./mock/data");

    var id = "123abc";
    var file = dataDirectoryIO.readAllBytesById("", id);
    assertNull(file);
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

  @Test
  void deleteById() throws IOException {
    var file = new File("./mock/data/text/1.txt");

    var fileIO = new FileIOMock(new byte[]{1, 2, 3}, new File[]{file});
    var dataDirectory = "./mock/data";
    var dataDirectoryIO = new DataDirectoryIO(fileIO, dataDirectory);

    dataDirectoryIO.deleteById("text", "1");

    assertEquals(dataDirectory + "/text/1.txt", fileIO.getLastDeletePath().toString());
  }
}