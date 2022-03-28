package edu.wpi.cs3733.D22.teamC.fileio.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An abstract class for CSVWriters
 * @param <T> The type of object the implementing CSVWriter will write to.
 */
public abstract class CSVWriter<T> {
    /**
     * Compile and write Objects of type T to a CSV file
     * @param fileName The designated CSV output file.
     * @param objects The list of Objects of T to write to CSV
     * @return true if successful, else false
     */
    public boolean writeFile(String fileName, List<T> objects) {
        // Convert Objects to List<String[]>
        List<String[]> objectAttributesList = objects.stream().map(object -> compileObject(object)).collect(Collectors.toList());

        // Convert List<String[]> to Data Stream
        Stream dataStream = objectAttributesList.stream().map(this::convertToCSV);

        // Write Headers to CSV
        String headerString = convertToCSV(compileHeaders());

        // Write Data Stream to CSV Output File
        File outputFile = new File(fileName);
        try (PrintWriter pw = new PrintWriter(outputFile)) {
            pw.println(headerString);
            dataStream.forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return outputFile.exists();
    }

    /**
     * Compile the attributes of an Object, creating a String[] to be inserted into the CSV
     * @param object The Object whose attributes are to be compiled
     * @return Ordered String[] of compiled attributes
     */
    protected abstract String[] compileObject(T object);

    /**
     * Compile the headers of this Object type T, creating a String[] for the header of the CSV
     * @return Ordered String[] of headers
     */
    protected abstract String[] compileHeaders();

    /**
     * Convert data in String[] into a CSV line.
     * @param data String[] of items to be linked as a CSV line.
     * @return A single String to be inserted as a line into a CSV file.
     */
    private String convertToCSV(String[] data) {
        return Stream.of(data).collect(Collectors.joining(","));
    }
}
