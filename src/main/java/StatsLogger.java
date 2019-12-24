import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Writes statistics for a CSV file to a log file
 */
public class StatsLogger {

    /**
     * Writes record statistics to a log file
     *
     * @param numRecordsReceived
     * @param numRecordsSuccessful
     * @param numRecordsFailed
     * @param fileName
     * @throws IOException
     */
    public static void writeStatsToLog(int numRecordsReceived, int numRecordsSuccessful,
                                       int numRecordsFailed, String fileName) throws IOException {
        String output = "Number of records received: " + numRecordsReceived + "\n" +
                "Number of records successful: " + numRecordsSuccessful + "\n" +
                "Number of records failed: " + numRecordsFailed + "\n";

        Files.write(Paths.get(fileName + ".log"), output.getBytes());
    }
}
