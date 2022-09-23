package ru.dictionary.dao.Util;

import ru.dictionary.model.Row;
import ru.dictionary.model.Word;

import java.util.List;
import java.util.UUID;

/**
 * Interface provide methods to work with data which stored in file.
 */
public interface InterfaceDAOWord2 {

    void saveWord(Word word);
    Word searchByUUID(UUID UUIDWord);
    List<Word> searchAllByUUID(UUID languageUUID);
    List<Word> searchAllByListUUID(List<UUID> keyUUIDList);

    List<Word> getWordsByWordValue(String wordValue);
    /**
     * Save given row in given file.
     *
     * @param Word entity.
     * @return true if row is stored, else false.
     */
    void save(Word Word);

    /**
     * Show all data from storage.
     *
     * @return <code>List<code/> which contains data from file.
     */
    List<Word> findAll();

    /**
     * Search one row from file.
     *
     * @param uuid id by which the entire string will be searched in the file.
     * @return one row or null.
     */
    Row findById(UUID uuid);

    /**
     * delete row.
     *
     * @param uuid id by which the entire string will be deleted from storage.
     * @return true if row was found and deleted, else false.
     */
    boolean deleteById(UUID uuid);

}
