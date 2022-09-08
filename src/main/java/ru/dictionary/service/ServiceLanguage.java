package ru.dictionary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dictionary.dao.LanguageDAO;
import ru.dictionary.model.Language;
import ru.dictionary.service.builder.BuilderLanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ServiceLanguage {

    private static final String KEY_VALUE_SEPARATOR_FOR_STORAGE = "#:!:#";
    private static final int NUMBER_FOR_SPLIT = Language.class.getDeclaredFields().length;

    private final LanguageDAO languageDAO;
    private final BuilderLanguage builderLanguage;

    public List<Language> findAllLanguages() {

        List<String> allLanguagesFromDao = languageDAO.getAllLanguages();
        List<Language> listLanguages = new ArrayList<>();
        for (String s : allLanguagesFromDao) {
            String[] splittedString = s.split(KEY_VALUE_SEPARATOR_FOR_STORAGE, NUMBER_FOR_SPLIT);
            Language languageAddedToList = builderLanguage.FromStringToEntity(splittedString);
            listLanguages.add(languageAddedToList);
        }

        return listLanguages;
    }

    public boolean addLanguage(Language language) {
        language.setLanguageId(UUID.randomUUID().toString());
        return languageDAO.saveLanguage(language);
    }

    public Language getLanguageById(String uuidLanguage) {
        return languageDAO.searchById(uuidLanguage);
    }
}
