package ru.dictionary.dao;

import ru.dictionary.model.Language;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface LanguageDAOInterface {

    List<Language> getAllLanguages();

    Language searchByUUID(UUID languageUUID) throws IOException;

    boolean saveLanguage(Language language);

    boolean deleteLanguage(Language language);
}
