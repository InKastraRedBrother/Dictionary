package ru.dictionary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dictionary.dao.InterfaceWordDAO;
import ru.dictionary.model.Language;
import ru.dictionary.model.Word;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ServiceWord {
    InterfaceWordDAO wordDAO;
    ServiceLanguage serviceLanguage;

    public void addWord(UUID wordUUID, String wordValue, UUID wordLanguageUUID) {
        Word word = new Word();
        word.setWordUUID(wordUUID);
        word.setWordValue(wordValue);
        word.setWordLanguageUUID(wordLanguageUUID);

        wordDAO.saveWord(word);
    }

    public void deleteWordByUUID(UUID wordUUID) {
        wordDAO.deleteById(wordUUID);
    }

    public List<Word> getListByLanguageUUID(UUID languageUUID) {
        return wordDAO.searchAllByUUID(languageUUID);
    }

    public Word getWordByUUID(UUID wordUUID) {
        return wordDAO.searchByUUID(wordUUID);
    }

    public List<Word> getListWordsByWordValue(String wordValue) {
        return wordDAO.getWordsByWordValue(wordValue);
    }

    public Language getLanguageByWordUUID(UUID wordUUID) {
        return serviceLanguage.getLanguageByUUID(wordUUID);

    }

    public List<Word> getListWordByListUUID(List<UUID> keyUUIDList) {
        return wordDAO.searchAllByListUUID(keyUUIDList);
    }

    public List<Word> findAllWords() {
        return wordDAO.getAllWords();
    }
}
