package com.k3ntako.HTTPServer.utilities;

import java.util.ArrayList;
import java.util.HashMap;

public class FileTypes {
  private HashMap<String, String[]> fileTypes = new HashMap<>();

  public FileTypes() {
    fileTypes.put("image/apng", new String[]{".apng"});
    fileTypes.put("image/bmp", new String[]{".bmp"});
    fileTypes.put("image/gif", new String[]{".gif"});
    fileTypes.put("image/x-icon", new String[]{".ico", ".cur"});
    fileTypes.put("image/jpeg", new String[]{".jpg", ".jpeg", ".jfif", ".pjpeg", ".pjp"});
    fileTypes.put("image/png", new String[]{".png"});
    fileTypes.put("image/svg+xml", new String[]{".svg"});
    fileTypes.put("image/webp", new String[]{".webp"});
  }

  public String getFromMimeType(String mimeType) {
    var types = fileTypes.get(mimeType);
    return types[0];
  }
}
