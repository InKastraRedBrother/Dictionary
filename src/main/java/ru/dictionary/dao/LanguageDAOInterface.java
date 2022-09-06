package ru.dictionary.dao;

import ru.dictionary.model.Language;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface LanguageDAOInterface {

    Optional<List<Language>> getAllLanguages();
    Optional<Language> getLanguageById(String id) throws IOException;
    void saveLanguage();
    void deleteLanguage();
}
