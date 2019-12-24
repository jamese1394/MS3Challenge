import java.sql.*;

/**
 * Checks if a table exists
 * Writes valid records to the database
 */
public class RecordWriter {

    /**
     * Creates or replaces the table
     *
     * @throws SQLException
     */
    public static void createTable(String fileName) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + fileName + ".db")) {
            Statement statement = connection.createStatement();

            statement.execute("DROP TABLE if exists person");

            statement.execute("CREATE TABLE if not exists person (a string, b string, c string, " +
                    "d string, e string, " + "f string, g string, h string, i string, j string)");
        }
    }

    /**
     * Writes a valid record to the SQLite database
     *
     * @param record
     * @param fileName
     * @throws SQLException
     */
    public static void writeValidRecordToDB(String[] record, String fileName) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + fileName + ".db")) {

            PreparedStatement pStatement = connection.prepareStatement("INSERT into" +
                    " person (a, b, c, d, e, f, g, h, i, j) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for (int i = 0; i < 10; i++) {
                pStatement.setString(i + 1, record[i]);
            }

            pStatement.execute();
        }
    }
}
