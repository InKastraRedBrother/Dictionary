package ru.dictionary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dictionary.dao.InterfaceLanguageDAO;
import ru.dictionary.model.Language;
import ru.dictionary.model.Word;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ServiceLanguage {

    private final InterfaceLanguageDAO languageDAO;

    public List<Language> getAllLanguages() {
        return languageDAO.getAllLanguages();
    }

    public boolean addLanguage(Language language) {
        language.setLanguageUUID(UUID.randomUUID());
        return languageDAO.saveLanguage(language);
    }

    public Language getLanguageByUUID(UUID languageUUID) {
        return languageDAO.searchByUUID(languageUUID);
    }

    public List<Language> findAllLanguagesByWordList(List<Word> listWord) {
        return languageDAO.findAllLanguagesByWordList(listWord);
    }
}
