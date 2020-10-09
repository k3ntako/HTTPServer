package com.k3ntako.HTTPServer.utilities;

import java.util.HashMap;

public class FileExtensions {
  private HashMap<String, String[]> mimeToExts = new HashMap<>();

  public FileExtensions() {
    mimeToExts.put("image/apng", new String[]{".apng"});
    mimeToExts.put("image/bmp", new String[]{".bmp"});
    mimeToExts.put("image/gif", new String[]{".gif"});
    mimeToExts.put("image/x-icon", new String[]{".ico", ".cur"});
    mimeToExts.put("image/jpeg", new String[]{".jpg", ".jpeg", ".jfif", ".pjpeg", ".pjp"});
    mimeToExts.put("image/png", new String[]{".png"});
    mimeToExts.put("image/svg+xml", new String[]{".svg"});
    mimeToExts.put("image/webp", new String[]{".webp"});
  }

  public String getFromMimeType(String mimeType) {
    var types = mimeToExts.get(mimeType);

    if (types == null) {
      return null;
    }

    return types[0];
  }
}
