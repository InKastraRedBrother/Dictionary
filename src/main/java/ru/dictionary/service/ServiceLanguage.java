package ru.dictionary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dictionary.dao.InterfaceLanguageDAO;
import ru.dictionary.model.Language;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ServiceLanguage {

    private final InterfaceLanguageDAO languageDAO;

    public List<Language> findAllLanguages() {
        return languageDAO.getAllLanguages();
    }

    public boolean addLanguage(Language language) {
        language.setLanguageUUID(UUID.randomUUID());
        return languageDAO.saveLanguage(language);
    }

    public Language getLanguageByUUID(UUID languageUUID) {
        return languageDAO.searchByUUID(languageUUID);
    }
}
