package edu.wpi.cs3733.D22.teamC.fileio.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An abstract class for CSVWriters.
 * @param <T> The type of object the implementing CSVWriter will write to.
 */
public abstract class CSVWriter<T> {
    /**
     * Compile and write Objects of type T to a CSV file.
     * @param fileName The designated CSV output file.
     * @param objects The list of Objects of T to write to CSV.
     * @return true if successful, else false.
     */
    public boolean writeFile(String fileName, List<T> objects) {
        // Compile headers for output order
        final String[] headers = compileHeaders();
        final String headerString = convertToCSV(headers);

        // Compile objects to Strings according to header order
        final String[] objectStrings = objects.stream().map(object -> compileObject(object, headers)).toArray(String[]::new);

        // Write Strings to CSV Output File
        File outputFile = new File(fileName);
        try (PrintWriter pw = new PrintWriter(outputFile)) {
            pw.println(headerString);
            for (String objectString : objectStrings) {
                pw.println(objectString);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return outputFile.exists();
    }

    /**
     * Set an array of headers, representing the order in which they will be output to CSV.
     * For implementing classes, this must be defined manually.
     * @return The array of headers to be output to CSV.
     */
    protected abstract String[] compileHeaders();

    /**
     * Compile the attributes of an Object, creating a String[] to be inserted into the CSV.
     * @param object The Object whose attributes are to be compiled.
     * @param headers The Headers which define the order to be output to.
     * @return Ordered String of compiled attributes.
     */
    private String compileObject(T object, String[] headers) {
        String[] attributes = new String[headers.length];
        for (int i = 0; i < headers.length; i++) {
            attributes[i] = compileAttribute(object, headers[i]);
        }
        return convertToCSV(attributes);
    }

    /**
     * Compile attributes for the current header to return the corresponding String for the given object.
     * For implementing classes, this function should be a switch case handling each expected header.
     * @param object The object to be read from.
     * @param header The header to be mapped to an attribute.
     * @return String corresponding to the attribute value of the given object and header.
     */
    protected abstract String compileAttribute(T object, String header);

    /**
     * Convert data in String[] into a CSV line.
     * @param data String[] of items to be linked as a CSV line.
     * @return A single String to be inserted as a line into a CSV file.
     */
    private String convertToCSV(String[] data) {
        return Stream.of(data).collect(Collectors.joining(","));
    }
}
