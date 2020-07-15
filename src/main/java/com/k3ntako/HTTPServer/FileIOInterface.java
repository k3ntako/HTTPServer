package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.nio.file.Path;

public interface FileIOInterface {
    void write(Path path, String str) throws IOException;

    String read(Path path) throws IOException;
}