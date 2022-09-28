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

    public UUID addWordIfRequired(String wordValue, UUID wordLanguageUUID) {
        Word wordKeyFromStorage = this.getByValue(wordValue);
        if (wordKeyFromStorage != null) {
            return wordKeyFromStorage.getWordUUID();
        }

        Word word = new Word();
        word.setWordUUID(UUID.randomUUID());
        word.setWordValue(wordValue);
        word.setWordLanguageUUID(wordLanguageUUID);

        wordDAO.saveWord(word);
        return word.getWordUUID();
    }

    public void delete(UUID wordUUID) {
        wordDAO.deleteById(wordUUID);
    }

    public List<Word> getByLanguageId(UUID languageUUID) {
        return wordDAO.searchAllByUUID(languageUUID);
    }

    public Word getById(UUID wordUUID) {
        return wordDAO.searchByUUID(wordUUID);
    }

    public Language getLanguageByWordUUID(UUID wordUUID) {
        return serviceLanguage.getLanguageByUUID(wordUUID);

    }

    public List<Word> getAll() {
        return wordDAO.getAllWords();
    }

    public List<Word> getListByValue(String wordValueFromView) {
        return wordDAO.searchListWordsByValue(wordValueFromView);
    }

    public Word getByValue(String wordValueFromView) {
        return wordDAO.searchWordByValue(wordValueFromView);
    }
}
