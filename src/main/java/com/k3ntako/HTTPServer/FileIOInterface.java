package com.k3ntako.HTTPServer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public interface FileIOInterface {
    void write(Path path, String str) throws IOException;
    String read(Path path) throws IOException;
    void patchNewLine(Path path, String str) throws IOException;
    void overwrite(Path path, String str) throws IOException;
    void delete(Path path) throws IOException;
    String getResource(String fileName) throws IOException, URISyntaxException;
}
