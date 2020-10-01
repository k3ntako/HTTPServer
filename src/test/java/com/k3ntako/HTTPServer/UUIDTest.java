package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.utilities.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UUIDTest {

  @Test
  void generate() {
    var uuid = new UUID();
    var uuidV4 = uuid.generate();
    assertNotNull(uuidV4);
    assertEquals(36, uuidV4.length());
  }
}