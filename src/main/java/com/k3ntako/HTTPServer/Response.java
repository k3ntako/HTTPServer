package com.k3ntako.HTTPServer;


import com.google.gson.JsonElement;
import com.k3ntako.HTTPServer.utilities.FileTypeResolverInterface;
import com.k3ntako.HTTPServer.utilities.JsonConverterInterface;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response.Status;

public class Response implements ResponseInterface {
  private byte[] body = null;
  private int status = 200;
  final private HashMap<String, String> additionalHeaders = new HashMap<>();
  final private JsonConverterInterface jsonConverter;
  private FileTypeResolverInterface fileTypeResolver;
  
  public Response(JsonConverterInterface jsonConverter, FileTypeResolverInterface fileTypeResolver) {
    this.jsonConverter = jsonConverter;
    this.fileTypeResolver = fileTypeResolver;
  }

  @Override
  public byte[] createResponse() {
    var header = this.createHeader();

    if (body != null) {
      return concatByteArrays(header, body);
    }

    return header;
  }

  private byte[] concatByteArrays(byte[] first, byte[] second) {
    byte[] combined = new byte[first.length + second.length];
    System.arraycopy(first, 0, combined, 0, first.length);
    System.arraycopy(second, 0, combined, first.length, second.length);
    return combined;
  }

  private byte[] createHeader() {
    var status = this.status;

    if (body == null && status >= 200 && status <= 299) {
      status = 204;
    }

    var header = "HTTP/1.1 " + status + " " + Status.fromStatusCode(status) + "\r\n";
    header += stringifyAdditionHeaders();

    if (body != null) {
      header += "Content-Length: " + body.length + "\r\n";
    }

    header += "\r\n";

    return header.getBytes();
  }

  @Override
  public void setBody(String body) throws HTTPError {
    validateBody(body);
    setBody(body.getBytes(), "text/plain");
  }

  @Override
  public void setBody(JsonElement body) throws HTTPError {
    validateBody(body);
    setBody(this.jsonConverter.toJson(body).getBytes(), "application/json");
  }

  @Override
  public void setBody(byte[] body) throws HTTPError {
    validateBody(body);
    var fileType = fileTypeResolver.guessContentTypeFromBytes(body);
    setBody(body, fileType);
  }

  @Override
  public void setBody(byte[] body, String contentType) throws HTTPError {
    validateBody(body);
    if(contentType != null){
      addHeader("Content-Type", contentType);
    }

    this.body = body;
  }

  private void validateBody(Object body) throws HTTPError {
    if (body == null) {
      throw new HTTPError(500, "Response body cannot be null");
    }
  }

  @Override
  public void setStatus(int status) {
    this.status = status;
  }

  @Override
  public void addHeader(String key, String value) {
    additionalHeaders.put(key, value);
  }

  @Override
  public void setRedirect(String url, int status) {
    setStatus(status);
    addHeader("Location", url);
  }

  private String stringifyAdditionHeaders() {
    var stringBuilder = new StringBuilder();
    for (Map.Entry<String, String> entry : additionalHeaders.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();

      stringBuilder.append(key);
      stringBuilder.append(": ");
      stringBuilder.append(value);
      stringBuilder.append("\r\n");
    }

    return stringBuilder.toString();
  }
}
