package edu.wpi.cs3733.D22.teamC.fileio.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * An abstract class for CSVReaders.
 * @param <T> The type of object the implementing CSVReader will read from.
 */
public abstract class CSVReader<T> {
    /**
     * Read and parse a CSV file for Objects of type T.
     * @param fileName name of file.
     * @return List of objects of type T.
     */
    public final List<T> readFile(String fileName) {
        try {
            // Open buffered reader from filepath
            Path filePath = Paths.get(fileName);
            BufferedReader br = Files.newBufferedReader(filePath, StandardCharsets.US_ASCII);
            return readFileInternal(br);
        } catch (IOException ioe) {
            System.out.println("Failed to read file " + fileName + ".");
            ioe.printStackTrace();
            return null;
        }
    }

    public final List<T> readFile(File file) {
        try {
            // Open buffered reader from filepath
            BufferedReader br = new BufferedReader(new FileReader(file));
            return readFileInternal(br);
        } catch (IOException ioe) {
            System.out.println("Failed to read file " + file.getName() + ".");
            ioe.printStackTrace();
            return null;
        }
    }

    private final List<T> readFileInternal(BufferedReader bufferedReader) throws IOException {
        List<T> objects = new ArrayList<>();
        String line;

        // Parse header line
        Map<String, Integer> headerMap;
        line = bufferedReader.readLine();
        String[] headers = trimStringArray(line.split(",", -1));
        headerMap = parseHeaders(headers);

        // Parse data lines
        line = bufferedReader.readLine();
        while (line != null) {
            String[] attributes = trimStringArray(line.split(",", -1));
            T object = parseObject(headerMap, attributes);
            objects.add(object);
            line = bufferedReader.readLine();
        }

        return objects;
    }

    /**
     * Parse the headers of the CSV file, storing a map of headers to their column indices.
     * @param headers A list of headers.
     * @return A map of each header to their column index.
     */
    private final Map<String, Integer> parseHeaders(String[] headers) {
        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < headers.length; i++) {
            map.put(headers[i], i);
        }
        return map;
    }

    /**
     * Parse the attributes of a line of the CSV file, creating an object of type T based on the map.
     * @param headerMap A map of each header to their column index.
     * @param attributes A list of attributes parsed from the csv line.
     * @return A newly created object of type T.
     */
    private final T parseObject(Map<String, Integer> headerMap, String[] attributes) {
        // Array Initialization necessary to avoid errors with lambda expression
        T[] object = (T[]) new Object[]{ createObject() };
        headerMap.forEach((header, index) -> {
            object[0] = parseAttribute(object[0], header, attributes[index]);
        });
        return object[0];
    }

    /**
     * Parse attributes from the current header value pair to set the corresponding attribute
     * for the given object.
     * For implementing classes, this function should be a switch case handling each expected header.
     * @param object The object to be modified.
     * @param header The header to be mapped to an attribute.
     * @param value The value for the current attribute.
     * @return The object modified with the value at the corresponding attribute.
     */
    protected abstract T parseAttribute(T object, String header, String value);

    /**
     * Create an empty Object of type T.
     * For implementing classes, this function should call the empty constructor of T.
     * @return A newly created object of type T.
     */
    protected abstract T createObject();

    /**
     * Trim all entries of a String[].
     * @param array The array of Strings to be trimmed.
     * @return An array of the trimmed Strings.
     */
    private String[] trimStringArray(String[] array) {
        return Arrays.stream(array).map(String::trim).toArray(String[]::new);
    }
}
