package com.k3ntako.HTTPServer.utilities;

import java.io.IOException;
import java.io.InputStream;

public interface MimeTypesInterface {
  String guessContentTypeFromBytes(byte[] fileContent);

  String guessContentTypeFromStream(InputStream fileContent);

  String guessContentTypeFromName(String fileName);

  String guessContentType(InputStream fileContent, String fileName);
}
