import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Parses a CSV file
 * Writes bad records to a log file
 * Writes valid records to a database
 * Generates statistics for a CSV file
 */
public class Driver {
    public static final int VALID_RECORD_NUMBER_OF_COLUMNS = 10;

    public static void main(String args[]) {
        int numRecordsReceived = 0;
        int numRecordsSuccessful = 0;
        int numRecordsFailed = 0;
        String csvFile = "ms3Interview - Jr Challenge 2.csv";
        String fileName = csvFile.replace(".csv", "");

        try (CSVReader reader = new CSVReader(new FileReader("src/main/resources/" + csvFile));
             CSVWriter writer = new CSVWriter(new PrintWriter(
                     new FileOutputStream(fileName + "-bad.csv")))) {

            // Reads the first line of the CSV File to skip the headers
            reader.readNext();

            RecordWriter.createTable(fileName);

            for (String[] record : reader) {
                numRecordsReceived++;

                if (RecordValidator.isValidRecord(record, VALID_RECORD_NUMBER_OF_COLUMNS)) {
                    RecordWriter.writeValidRecordToDB(record, fileName);
                    numRecordsSuccessful++;
                } else {
                    writer.writeNext(record);
                    numRecordsFailed++;
                }
            }

            StatsLogger.writeStatsToLog(numRecordsReceived, numRecordsSuccessful, numRecordsFailed, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}