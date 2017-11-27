package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Utility functions for file input/output. */
public class FileUtils
{
    // todo: add ability to append lines to file
    public static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;
    /** Return absolute path of given file (wraps Paths's toAbsolutePath()). */
    public static String getAbsolutePath(String filePath)
    {
        return Paths.get(filePath).toAbsolutePath().toString();
    }
    /**
     * Return basename of given path, without extension or leading dot.
     * For example:
     *   ~/Users/.tmp => tmp, /.x.y.z/ => x, foo.tar.gz => foo
     */
    public static String getBaseName(String path)
    {
        String rawName = new File(path).getName();
        if (rawName.equals("") || rawName.equals("."))
            return "";

        // ignoring starting dots, and end at the first dot found
        int dotIndex = rawName.substring(1).indexOf('.');
        int start = rawName.startsWith(".")? 1 : 0;
        int end = dotIndex == -1? rawName.length() : dotIndex + 1;
        return rawName.substring(start, end);
    }
    /** Return name of directory without extension, "" if not . */
    public static String getDirectoryName(String filePath)
    {
        return Paths.get(filePath).getParent().toAbsolutePath().toString();
    }
    /** Return true if path exists as file, false otherwise. */
    public static boolean isFile(String filePath)
    {
        return (new File(filePath)).isFile();
    }
    /** Return true if path exists as directory, false otherwise. */
    public static boolean isDirectory(String filePath)
    {
        return (new File(filePath)).isDirectory();
    }
    /** Return empty string if no extension, null if not a file, else extension without dot of given file. */
    public static String getExtension(String filePath)
    {
        File file = new File(filePath);
        String fileName = file.getName();
        int dotLocation = fileName.lastIndexOf('.');
        if (file.isFile())
            return (dotLocation == -1)? "" : fileName.substring(dotLocation + 1);
        return null;
    }
    /** Returns true if path has single matching extension. */
    public static boolean hasExtension(String filePath, String extension)
    {
        // todo: change to ensureIsTextFile(), and throw exception if not instead of bool return
        File file = new File(filePath);
        String fileName = file.getName();
        int dotLocation = fileName.lastIndexOf('.');
        return file.isFile() && dotLocation >= 1 && fileName.substring(dotLocation + 1).equals(extension);
    }
    /** Overwrites contents (newlines are recognized) of given text file, creating if not present. */
    public static void writeText(String filePath, String text)
    {
        try (PrintStream out = new PrintStream(filePath, DEFAULT_ENCODING.name()))
        {
            out.print(text + "\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    /** Writes given lines of text to file. */
    public static void writeLines(String filePath, List<String> lines)
    {
        try (BufferedWriter writer = java.nio.file.Files.newBufferedWriter(Paths.get(filePath), DEFAULT_ENCODING))
        {
            for (String line : lines)
            {
                writer.write(line);
                writer.newLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    /** Return list of strings representing each non-blank line in the file. */
    public static List<String> readLines(String filePath)
    {
        // try with resources, pipe each line of file through a stream and into an arraylist
        List<String> linesInFile = new ArrayList<>();
        try (Stream<String> stream = java.nio.file.Files.lines(Paths.get(filePath), DEFAULT_ENCODING))
        {
            linesInFile = stream
                .filter(line -> !line.trim().equals(""))
                .collect(Collectors.toCollection(ArrayList<String>::new));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return linesInFile;
    }
}
