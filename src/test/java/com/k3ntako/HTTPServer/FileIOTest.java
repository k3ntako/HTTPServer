package com.k3ntako.HTTPServer;

import com.k3ntako.HTTPServer.fileSystemsIO.FileIO;
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
  final private Path path = Paths.get("./mock_file.txt");
  final private Path pathOfFolder = Paths.get("./mock_data");
  final private Path pathInFolder = Paths.get(pathOfFolder.toString() + "/file.txt");

  @Test
  void write() throws IOException {
    final var str = "This is text\nthis is a new line!";

    final var fileIO = new FileIO();
    fileIO.write(path, str);

    final var fileContent = Files.readString(path);

    assertEquals(str, fileContent);
  }

  @Test
  void writeBinary() throws IOException {
    final var bytes = "This is text\nthis is a new line!".getBytes();

    final var fileIO = new FileIO();
    fileIO.write(path, bytes);

    final var fileContent = Files.readAllBytes(path);

    assertArrayEquals(bytes, fileContent);
  }

  @Test
  void readString() throws IOException {
    final var str = "This is a different text\nthis is a new line!\r\n And more!";
    Files.write(path, str.getBytes());

    final var fileIO = new FileIO();
    final var fileContent = fileIO.readString(path);

    assertEquals(str, fileContent);
  }

  @Test
  void readAllBytes() throws IOException {
    final var str = "This text will be turned into bytes.\n";
    Files.write(path, str.getBytes());

    final var fileIO = new FileIO();
    final var fileContent = fileIO.readAllBytes(path);

    assertArrayEquals(str.getBytes(), fileContent);
  }

  @Test
  void readingEmptyFileReturnsEmptyString() throws IOException {
    var file = new File(path.toString());
    file.createNewFile();

    final var fileIO = new FileIO();
    final var fileContent = fileIO.readString(path);

    assertNotNull(fileContent);
    assertEquals("", fileContent);
  }

  @Test
  void readReturnsNullIfFileNotFound() throws IOException {
    final var fileIO = new FileIO();
    final var fileContent = fileIO.readString(path);

    assertNull(fileContent);
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
  void patch() throws IOException {
    final var str = "This is the first line";
    Files.write(path, str.getBytes());

    final var patchStr = "This is the second line";
    final var fileIO = new FileIO();
    fileIO.patchNewLine(path, patchStr);

    final var fileContent = Files.readString(path);

    final var expected = str + "\n" + patchStr;
    assertEquals(expected, fileContent);
  }

  @Test
  void patchThrowsErrorIfDoesNotExist() {
    final var patchStr = "This is the second line";
    final var fileIO = new FileIO();

    IOException exception = assertThrows(IOException.class, () -> fileIO.patchNewLine(path, patchStr));

    assertEquals("File does not exist", exception.getMessage());
  }

  @Test
  void overwrite() throws IOException {
    final var str = "This is the first line";
    Files.write(path, str.getBytes());

    final var overwriteStr = "This is the second line";
    final var fileIO = new FileIO();
    fileIO.overwrite(path, overwriteStr);

    final var fileContent = Files.readString(path);

    assertEquals(overwriteStr, fileContent);
  }

  @Test
  void overwriteThrowsErrorIfDoesNotExist() {
    final var overwriteStr = "This is the second line";
    final var fileIO = new FileIO();

    IOException exception = assertThrows(IOException.class, () -> fileIO.overwrite(path, overwriteStr));
    assertEquals("File does not exist", exception.getMessage());
  }

  @Test
  void delete() throws IOException {
    var file = new File(path.toString());
    file.createNewFile();

    assertTrue(Files.exists(path));

    final var fileIO = new FileIO();
    fileIO.delete(path);

    assertFalse(Files.exists(path));
  }

  @Test
  void deleteThrowsErrorIfFileDoesNotExist() {
    final var fileIO = new FileIO();

    IOException exception = assertThrows(
        IOException.class,
        () -> fileIO.delete(path)
    );

    assertEquals("File does not exist", exception.getMessage());
  }

  @Test
  void listFiles() throws IOException {
    var file = new File(pathInFolder.toString());
    file.mkdirs();
    file.createNewFile();

    assertTrue(Files.exists(pathInFolder));

    final var fileIO = new FileIO();
    var files = fileIO.listFiles(pathOfFolder);

    assertEquals(1, files.length);
    assertEquals("file.txt", files[0].getName());
  }

  @Test
  void listFilesReturnsNullIfNotFound() {
    final var fileIO = new FileIO();
    var files = fileIO.listFiles(pathOfFolder);

    assertNull(files);
  }

  @AfterEach
  void tearDown() throws IOException {
    Files.deleteIfExists(path);

    if (Files.exists(pathInFolder.getParent())) {
      Files.walk(pathOfFolder)
          .sorted(Comparator.reverseOrder())
          .map(Path::toFile)
          .forEach(File::delete);
    }
  }
}