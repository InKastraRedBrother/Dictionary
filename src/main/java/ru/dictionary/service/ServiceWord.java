package ru.dictionary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dictionary.dao.WordDAO;
import ru.dictionary.model.Language;
import ru.dictionary.model.Word;

@Component
@AllArgsConstructor
public class ServiceWord {
    WordDAO wordDAO;
    ServiceLanguage serviceLanguage;

    public void addWord(String uuidWordId, String wordValue, String uuidLanguageId) {
        Word word = new Word();
        word.setWordId(uuidWordId);
        word.setWordValue(wordValue);
        word.setLanguageId(uuidLanguageId);

        wordDAO.saveWord(word);
    }

    public Word getWordById(String uuidWord) {
        return wordDAO.searchById(uuidWord);
    }

    public Language getLanguageByWordId(Word word) {
        return serviceLanguage.getLanguageById(word.getLanguageId());

    }
}
