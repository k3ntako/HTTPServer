package com.k3ntako.HTTPServer.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileExtensionsTest {

  @Test
  void getFromMimeTypePng() {
    var fileExtensions = new FileExtensions();

    var fileExt = fileExtensions.getFromMimeType("image/png");
    assertEquals(".png", fileExt);
  }

  @Test
  void getFromMimeTypeJpg() {
    var fileExtensions = new FileExtensions();

    var fileExt = fileExtensions.getFromMimeType("image/jpeg");
    assertEquals(".jpg", fileExt);
  }

  @Test
  void getFromMimeTypeSvg() {
    var fileExtensions = new FileExtensions();

    var fileExt = fileExtensions.getFromMimeType("image/svg+xml");
    assertEquals(".svg", fileExt);
  }

  @Test
  void getFromMimeTypeReturnsNullIfNotFound() {
    var fileExtensions = new FileExtensions();

    var fileExt = fileExtensions.getFromMimeType("not a real type");
    assertNull(fileExt);
  }
}