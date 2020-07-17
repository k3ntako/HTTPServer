package com.k3ntako.HTTPServer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {
  final private Path path = Paths.get("./file.txt");
  final private Path pathInFolder = Paths.get("./data/test/file.txt");

  @AfterEach
  void tearDown() throws IOException {
    Files.deleteIfExists(path);

    if (Files.exists(pathInFolder.getParent())) {
      Files.walk(pathInFolder.getParent())
          .sorted(Comparator.reverseOrder())
          .map(Path::toFile)
          .forEach(File::delete);
    }
  }

  @Test
  void write() throws IOException {
    final var str = "This is text\nthis is a new line!";

    final var fileIO = new FileIO();
    fileIO.write(path, str);

    final var fileContent = Files.readString(path);

    assertEquals(str, fileContent);
  }

  @Test
  void read() throws IOException {
    final var str = "This is a different text\nthis is a new line!\r\n And more!";
    Files.write(path, str.getBytes());

    final var fileIO = new FileIO();
    final var fileContent = fileIO.read(path);

    assertEquals(str, fileContent);
  }

  @Test
  void writeToFileInFolder() throws IOException {
    final var str = "This is text\nthis is a new line!";

    final var fileIO = new FileIO();
    fileIO.write(pathInFolder, str);

    final var fileContent = Files.readString(pathInFolder);

    assertEquals(str, fileContent);
  }

  @Test
  void append() throws IOException, HTTPError {
    final var str = "This is a different text\nthis is a new line!\r\n And more!";
    Files.write(path, str.getBytes());

    final var fileIO = new FileIO();
    fileIO.append(path, "Appended string!");

    final var fileContent = Files.readString(path);

    assertEquals(str + "Appended string!", fileContent);
  }

  @Test
  void appendShouldThrowIfFileDoesNotExist() {
    final var fileIO = new FileIO();

    HTTPError exception = assertThrows(
        HTTPError.class,
        () -> fileIO.append(path, "Appended string!")
    );

    assertEquals("File to be appended was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }

  @Test
  void delete() throws IOException, HTTPError {
    Files.write(path, "File text to delete".getBytes());

    final var fileIO = new FileIO();
    fileIO.delete(path);

    var file = new File(path.toString());
    assertFalse(file.exists());
  }

  @Test
  void deleteShouldThrowIfFileDoesNotExist() {
    final var fileIO = new FileIO();


    HTTPError exception = assertThrows(
        HTTPError.class,
        () -> fileIO.delete(path)
    );

    assertEquals("File was not found", exception.getMessage());
    assertEquals(404, exception.getStatus());
  }
}