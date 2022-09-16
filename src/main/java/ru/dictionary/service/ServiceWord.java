package ru.dictionary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dictionary.dao.WordDAO;
import ru.dictionary.model.Language;
import ru.dictionary.model.Word;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ServiceWord {
    WordDAO wordDAO;
    ServiceLanguage serviceLanguage;

    public void addWord(UUID uuidWordId, String wordValue, UUID uuidLanguageId) {
        Word word = new Word();
        word.setWordId(uuidWordId);
        word.setWordValue(wordValue);
        word.setLanguageId(uuidLanguageId);

        wordDAO.saveWord(word);
    }

    public List<Word> getListByLanguageUUID(UUID languageUUID){
        return wordDAO.searchAllById(languageUUID);
    }

    public Word getWordById(UUID uuidWord) {
        return wordDAO.searchById(uuidWord);
    }

    public Language getLanguageByWordId(UUID uuidWord) {
        return serviceLanguage.getLanguageById(uuidWord);

    }
}
