import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class CSVParser {
    private static List<String[]> goodRecordList;
    private static String csvFile;
    private static String fileName;

    public static void main(String args[]){
        int numRecordsRcvd = 0;
        int numRecordsSuccessful = 0;
        int numRecordsFailed = 0;
        csvFile = "ms3Interview - Jr Challenge 2.csv";
        fileName = csvFile.replace(".csv", "");
        CSVReader reader = null;
        CSVWriter writer = null;

        try{
            reader = new CSVReader(new FileReader(csvFile));
            writer = new CSVWriter(new PrintWriter(new FileOutputStream(fileName+ "-bad.csv")));
            goodRecordList = new ArrayList<String[]>();

            // Reads the first line of the CSV File to skip the headers
            reader.readNext();

            String[] nextRecord;
            outerloop:
            while ((nextRecord = reader.readNext()) != null) {

                numRecordsRcvd++;
                // Classifies a record as bad if it has more than 10 columns
                if (nextRecord.length != 10) {
                    writer.writeNext(nextRecord);
                    numRecordsFailed++;
                    continue outerloop;
                }

                else{
                    // Classifies a record as bad if it has an empty cell
                    for (String cell : nextRecord) {
                        if (cell.isEmpty()) {
                            writer.writeNext(nextRecord);
                            numRecordsFailed++;
                            continue outerloop;
                        }
                    }

                    // Classifies a record as good if it does not meet above criteria
                    goodRecordList.add(nextRecord);
                }
            }

            // Add records with 10 columns to database
            for(String[] recordArr : goodRecordList){
                writeGoodListToDB(recordArr);
                numRecordsSuccessful++;
            }

            writeStatsToLog(numRecordsRcvd, numRecordsSuccessful, numRecordsFailed);
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            try {
                reader.close();
                writer.close();
            }
            catch(NullPointerException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

    }

    private static void writeGoodListToDB(String[] record){
        Connection connection = null;

        try{
            // SQLite
            connection = DriverManager.getConnection("jdbc:sqlite:" +fileName+ ".db");
            Statement statement = connection.createStatement();

            statement.executeUpdate("create table if not exists person (a string, b string, c string, d string, e string, " +
                    "f string, g string, h string, i string, j string)");

            PreparedStatement pStatement = connection.prepareStatement("insert into " +
                    "person (a, b, c, d, e, f, g, h, i, j) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for(String r : record){
                pStatement.setString(3, r);
                pStatement.setString(2, r);
                pStatement.setString(1, r);
                pStatement.setString(4, r);
                pStatement.setString(5, r);
                pStatement.setString(6, r);
                pStatement.setString(7, r);
                pStatement.setString(8, r);
                pStatement.setString(9, r);
                pStatement.setString(10, r);
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        finally{
            try {
                if (connection != null) {
                    connection.close();
                }
            }
            catch(SQLException e) {
                // Connection failed to close
                e.printStackTrace();
            }
        }
    }

    private static void writeStatsToLog(int numRecordsRcvd, int numRecordsSuccessful, int numRecordsFailed){
        PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new FileOutputStream(fileName + ".log"));
            pWriter.println("Number of records received: "+ numRecordsRcvd);
            pWriter.println("Number of records successful: "+ numRecordsSuccessful);
            pWriter.println("Number of records failed: "+ numRecordsFailed);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            pWriter.close();
        }
    }

}
