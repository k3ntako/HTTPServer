package com.k3ntako.HTTPServer.controllers;

import com.google.gson.JsonObject;
import com.k3ntako.HTTPServer.DataDirectoryIO;
import com.k3ntako.HTTPServer.mocks.FileIOMock;
import com.k3ntako.HTTPServer.mocks.RequestMock;
import com.k3ntako.HTTPServer.mocks.ResponseMock;
import com.k3ntako.HTTPServer.mocks.UUIDMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ImagesTest {

  @Test
  void post() throws IOException {
    final var bytes = "This is the body".getBytes();
    var request = new RequestMock("POST", "/images", bytes);

    final var fileIO = new FileIOMock();
    final var dataDirectoryIO = new DataDirectoryIO(fileIO, "./data");
    final var uuidMock = new UUIDMock();


    var images = new Images(dataDirectoryIO, uuidMock);
    var response = (ResponseMock) images.post(request, new ResponseMock());

    var expected = "./data/images/" + uuidMock.getDefaultUUID() + ".png";
    assertEquals(expected, fileIO.getLastWritePath().toString());
    assertArrayEquals(bytes, (byte[]) fileIO.getLastWrite());

    var responseJson = (JsonObject) response.getSetJsonBodyArg;
    var id = responseJson.get("id").getAsString();

    assertEquals(uuidMock.getDefaultUUID(), id);
  }
}