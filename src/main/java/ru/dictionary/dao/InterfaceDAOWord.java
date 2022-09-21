package ru.dictionary.dao;

import ru.dictionary.model.Row;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface provide methods to work with data which stored in file.
 */
public interface InterfaceDAOWord {
    /**
     * Save given row in given file.
     *
     * @param row      - row is a model. contains (String key, String value).
     * @param fileName the name of the file where the data will be stored.
     * @return true if row is stored, else false.
     */
    boolean save(Row row);

    /**
     * Show all data from file
     *
     * @param fileName this is the name of the file to get data from.
     * @return <code>List<code/> which contains data from file.
     */
    List<Row> findAll();

    /**
     * Search one row from file.
     *
     * @param key      the key word by which the entire string will be searched in the file.
     * @param fileName name of the file to get data from.
     * @return one row or null.
     */
    Row findById(UUID uuid);

    /**
     * delete row by key.
     *
     * @param inputtedKey the key word by which the entire string will be deleted from file.
     * @param fileName    name of the file where row will be deleted.
     * @return true if row was found and deleted, else false
     */
    boolean deleteByKey(UUID uuid);
}
