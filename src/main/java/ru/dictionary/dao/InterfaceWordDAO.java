package ru.dictionary.dao;

import ru.dictionary.model.Word;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface provide methods to work with data which stored in file.
 */
public interface InterfaceWordDAO {

    List<Word> searchAllByUUID(UUID languageUUID);
    List<Word> searchAllByListUUID(List<UUID> keyUUIDList);

    public List<Word> getWordsByWordValue(String wordValue);
    /**
     * Save given row in given file.
     *
     * @param word entity.
     * @return true if row is stored, else false.
     */
    void saveWord(Word word);

    /**
     * Show all data from storage.
     *
     * @return <code>List<code/> which contains data from file.
     */
    List<Word> getAllWords();

    /**
     * Search one row from file.
     *
     * @param uuid id by which the entire string will be searched in the file.
     * @return one row or null.
     */
    Word searchByUUID(UUID UUIDWord);

    /**
     * delete row.
     *
     * @param uuid id by which the entire string will be deleted from storage.
     * @return true if row was found and deleted, else false.
     */
    boolean deleteById(UUID uuid);

    List<Word> searchListWordsByValue(String s);

    Word searchWordByValue(String wordValueFromView);
}
