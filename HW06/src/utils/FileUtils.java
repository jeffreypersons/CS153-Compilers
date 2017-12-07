package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
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

import static java.nio.file.StandardOpenOption.APPEND;


/** Utility functions for file input/output: does normalization as much as possible. */
public class FileUtils
{
    public static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

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
    /** Return true if path exists as file, false otherwise. */
    public static boolean isFile(String path)
    {
        return new File(path).isFile();
    }
    /** Return true if path exists as directory, false otherwise. */
    public static boolean isDir(String path)
    {
        return new File(path).isDirectory();
    }
    /** Return true if path exists and dir/file successfully deleted, false otherwise. */
    public static boolean delete(String path)
    {
        return new File(path).delete();
    }

    /** Return given paths joined to base. */
    public static String joinPaths(String basepath, String... paths)
    {
        return Paths.get(basepath, paths).normalize().toString();
    }
    /** Return absolute path of given file (wraps Paths's toAbsolutePath()). */
    public static String getAbsolutePath(String filePath)
    {
        return Paths.get(filePath).toAbsolutePath()
            .normalize().toString();
    }
    /** Return full path of given path's parent directory. */
    public static String getParentDir(String path)
    {
        return Paths.get(path).getParent().toAbsolutePath()
            .normalize().toString();
    }

    // todo: change to return entire extension (eg foo.x.y.z => x.y.z)
    /**
     * Return last extension of given filePath, empty string if not a file
     *  empty string if no extension, null if not a file, else extension without dot of given file.
     */
    public static String getLastExtension(String filePath)
    {
        File file = new File(filePath);
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return file.isFile()? fileName.substring(dotIndex + 1) : "";
    }

    /** Overwrites contents (newlines are recognized) of given text file, creating if not present. */
    public static void appendText(String filePath, String text)
    {
        try (PrintStream out = new PrintStream(new FileOutputStream(filePath, true)))
        {
            out.write(
                (text + System.lineSeparator()).getBytes(DEFAULT_ENCODING)
            );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    /** Append given lines of text to file. */
    public static void appendLines(String filePath, List<String> lines)
    {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath), DEFAULT_ENCODING, APPEND))
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
        try (Stream<String> stream = Files.lines(Paths.get(filePath), DEFAULT_ENCODING))
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
