package com.k3ntako.HTTPServer.utilities;

import java.io.IOException;
import java.io.InputStream;

public interface MimeTypesInterface {
  String guessContentTypeFromBytes(byte[] fileBytes);

  String guessContentTypeFromStream(InputStream fileInputStream);

  String guessContentTypeFromName(String fileName);

  String guessContentType(byte[] fileBytes, String fileName);
}
