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
    var textFile = new TextFile(fileIO, new UUIDMock());

    var fileName = textFile.saveFile("hello!");

    assertEquals("8d142d80-565f-417d-8334-a8a19caadadb", fileName);

    var pathStr = fileIO.getLastWritePath().toString();
    assertEquals("./data/8d142d80-565f-417d-8334-a8a19caadadb.txt", pathStr);
    assertEquals("hello!", fileIO.getLastWrite());
  }

  @Test
  void readFile() throws IOException {
    var fileIO = new FileIOMock("test file str");
    var textFile = new TextFile(fileIO, new UUIDMock());

    var fileStr = textFile.readFile("8d142d80-565f-417d-8334-a8a19caadadb");
    assertEquals("test file str", fileStr);
    assertEquals("./data/8d142d80-565f-417d-8334-a8a19caadadb.txt", fileIO.getLastReadPath().toString());
  }

  @Test
  void patchFile() throws IOException {
    var fileIO = new FileIOMock();
    var uuid = new UUIDMock();
    var textFile = new TextFile(fileIO, uuid);

    textFile.patchFile(uuid.getDefaultUUID(), "Here is the patch text");

    var pathStr = fileIO.getLastPatchPath().toString();
    assertEquals("./data/8d142d80-565f-417d-8334-a8a19caadadb.txt", pathStr);
    assertEquals("Here is the patch text", fileIO.getLastPatch());
  }
}