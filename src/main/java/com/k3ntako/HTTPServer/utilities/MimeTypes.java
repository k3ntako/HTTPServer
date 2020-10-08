package com.k3ntako.HTTPServer.utilities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

public class MimeTypes implements MimeTypesInterface {
  @Override
  public String guessContentTypeFromStream(InputStream fileInputStream) {
    try {
      return URLConnection.guessContentTypeFromStream(fileInputStream);
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public String guessContentTypeFromName(String fileName) {
    return URLConnection.guessContentTypeFromName(fileName);
  }

  @Override
  public String guessContentTypeFromBytes(byte[] fileBytes) {
    if(fileBytes == null) {
      return null;
    }

    var byteStream = new ByteArrayInputStream(fileBytes);

    return guessContentTypeFromStream(byteStream);
  }

  @Override
  public String guessContentType(byte[] fileBytes, String fileName) {
    var mimeTypes = guessContentTypeFromBytes(fileBytes);

    if(mimeTypes != null){
      return mimeTypes;
    }

    return guessContentTypeFromName(fileName);
  }
}
