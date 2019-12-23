import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.opencsv.CSVReader;

public class CSVParser {
    private static List<String[]> goodRecordList;

    public static void main(String args[]){
        List<String[]> badRecordList;
        int numRecordsRcvd = 0;
        int numRecordsSuccessful = 0;
        int numRecordsFailed = 0;
        String csvFile = "ms3Interview - Jr Challenge 2.csv";
        CSVReader reader = null;

        try{
            reader = new CSVReader(new FileReader(csvFile));
            reader.readNext();

            badRecordList = new ArrayList<String[]>();
            goodRecordList = new ArrayList<String[]>();

            // Reads the first line of the CSV File to skip the headers
            reader.readNext();

            String[] nextRecord;
            outerloop:
            while ((nextRecord = reader.readNext()) != null) {
                // Classifies a record as bad if it has more than 10 columns
                if (nextRecord.length != 10) {
                    badRecordList.add(nextRecord);
                    numRecordsFailed++;
                    continue;
                }

                else{
                    for (String cell : nextRecord) {
                        if (cell.isEmpty()) {
                            badRecordList.add(nextRecord);
                            numRecordsFailed++;
                            continue outerloop;
                        }
                    }

                    // Add record with 10 columns to database
                    // writeRecordsToDB
                    goodRecordList.add(nextRecord);
                    numRecordsSuccessful++;
                }
                numRecordsRcvd++;
            }

            for(String[] recordArr : goodRecordList){
                writeGoodListToDB(recordArr);
            }

            // writeStatsToLog(numRecordsRcvd, numRecordsFailed, numRecordsSuccessful);
            // writeBadListToFile(badRecordList);
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            try {
                reader.close();
            }
            catch(NullPointerException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

    }

    public static void writeGoodListToDB(String[] record){
        Connection connection = null;

        try{
            // SQLite
            connection = DriverManager.getConnection("jdbc:sqlite:ms3.db");
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
            catch(SQLException e){
                // Connection failed to close
                e.printStackTrace();
            }
        }
    }

//    public static void writeStatsToLog(int numRecordsRcvd, int numRecordsFailed, int numRecordsSuccessful){
//    }
//
//    public static void writeBadListToFile(List<String> badRecordList){
//    }

}
