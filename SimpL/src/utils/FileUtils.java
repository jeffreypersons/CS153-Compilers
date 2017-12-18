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

    /** Throw IOException if given extension (including leading dot) does not match path's extension. */
    public static void ensureFileExtension(String path, String extension) throws IOException
    {
        if (!FileUtils.getEntireFileExtension(path).equals(".simpl"))
            throw new IOException("Source file " + path + " is invalid - only " + extension + " extensions are supported");
    }
    /** Throw IOException if invalid */
    public static void ensureFileExists(String path) throws IOException
    {
        if (!FileUtils.isFile(path))
            throw new IOException("Source file " + path + " is invalid - " + path);
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

    /**
     * Return basename of given path, without extension or leading dot.
     * For example: ~/Users/.tmp => tmp, /.x.y.z/ => x, foo.tar.gz => foo, .gitignore => ""
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
    /**
     * Return extension if path is existing file with at least one file extension, empty string otherwise.
     * For example: foo.tar.gz => .tar.gz, .gitignore => ""
     */
    public static String getEntireFileExtension(String path)
    {
        // empty string if empty path, starts or ends with ., or no extension
        File file = new File(path);
        String basename = file.getName();
        boolean hasFileExtension = (
            !basename.equals("") && file.isFile() &&
            !basename.startsWith(".") && !basename.endsWith(".") && basename.contains(".")
        );
        return hasFileExtension? basename.substring(basename.indexOf('.')) : "";
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
