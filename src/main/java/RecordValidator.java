/**
 * Validates records
 */
public class RecordValidator {

    /**
     * Checks whether a record is valid
     * Classifies a record as bad if it has more than 10 columns or has an empty cell
     * Classifies a record as valid otherwise
     *
     * @param record
     * @return
     */
    public static boolean isValidRecord(String[] record, int validRecordLength) {
        if (record.length != validRecordLength) {
            return false;
        }

        for (String cell : record) {
            if (cell.isEmpty()) {
                return false;
            }
        }

        return true;
    }

}
