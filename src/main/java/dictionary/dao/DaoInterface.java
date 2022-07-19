package dictionary.dao;

import dictionary.model.Row;

import java.util.List;
import java.util.Optional;

/**
 * Interface provide methods to work with data which stored in file.
 */
public interface DaoInterface {
    /**
     * Save given row in given file.
     *
     * @param row      - row is a model. contains (String key, String value).
     * @param fileName the name of the file where the data will be stored.
     * @return true if row is stored, else false.
     */
    boolean save(Row row, String fileName);

    /**
     * Show all data from file
     *
     * @param fileName this is the name of the file to get data from.
     * @return <code>List<code/> which contains data from file.
     */
    List<Row> findAll(String fileName);

    /**
     * Search one row from file.
     *
     * @param key      the key word by which the entire string will be searched in the file.
     * @param fileName name of the file to get data from.
     * @return one row or null.
     */
    Optional<Row> findByKey(String key, String fileName);

    /**
     * delete row by key.
     *
     * @param inputtedKey the key word by which the entire string will be deleted from file.
     * @param fileName    name of the file where row will be deleted.
     * @return true if row was found and deleted, else false
     */
    boolean deleteByKey(String inputtedKey, String fileName);
}
