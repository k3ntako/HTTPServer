package com.k3ntako.HTTPServer.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileTypesTest {

  @Test
  void getFromMimeTypePng() {
    var fileTypes = new FileTypes();

    var fileType = fileTypes.getFromMimeType("image/png");
    assertEquals(".png", fileType);
  }

  @Test
  void getFromMimeTypeJpg() {
    var fileTypes = new FileTypes();

    var fileType = fileTypes.getFromMimeType("image/jpeg");
    assertEquals(".jpg", fileType);
  }

  @Test
  void getFromMimeTypeSvg() {
    var fileTypes = new FileTypes();

    var fileType = fileTypes.getFromMimeType("image/svg+xml");
    assertEquals(".svg", fileType);
  }
}