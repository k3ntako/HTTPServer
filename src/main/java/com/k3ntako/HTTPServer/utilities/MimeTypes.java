package com.k3ntako.HTTPServer.utilities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

public class MimeTypes implements MimeTypesInterface {

  @Override
  public String guessContentTypeFromBytes(byte[] fileContent) {
    var byteStream = new ByteArrayInputStream(fileContent);
    try {
      return URLConnection.guessContentTypeFromStream(byteStream);
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public String guessContentTypeFromStream(InputStream fileContent) {
    try {
      return URLConnection.guessContentTypeFromStream(fileContent);
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public String guessContentTypeFromName(String fileName) {
    return URLConnection.guessContentTypeFromName(fileName);
  }

  @Override
  public String guessContentType(InputStream fileContent, String fileName) {
    var mimeTypes = guessContentTypeFromStream(fileContent);

    if(mimeTypes != null){
      return mimeTypes;
    }

    return guessContentTypeFromName(fileName);
  }
}
