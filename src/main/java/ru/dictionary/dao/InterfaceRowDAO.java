package ru.dictionary.dao;

import ru.dictionary.model.Row;
import ru.dictionary.model.Word;

import java.util.List;
import java.util.UUID;

/**
 * Interface provide methods to work with data which stored in file.
 */
public interface InterfaceRowDAO {

    /**
     * Save given row in given file.
     *
     * @param row entity.
     * @return true if row is stored, else false.
     */
    void save(Row row);

    /**
     * Show all data from storage.
     *
     * @return <code>List<code/> which contains data from file.
     */
    List<Row> findAll();

    /**
     * delete row.
     *
     * @param uuid id by which the entire string will be deleted from storage.
     * @return true if row was found and deleted, else false.
     */

    boolean delete(UUID uuid);

    /**
     * Search one row from file.
     *
     * @param uuid id by which the entire string will be searched in the file.
     * @return one row or null.
     */
    Row findById(UUID uuid);

    List<Row> find(List<Word> listWord);

    List<Row> findListById(UUID uuid);

    Row find(UUID keyWordUUID, UUID valueWordUUID);
}
