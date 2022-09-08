package ru.dictionary.dao;

import ru.dictionary.model.Language;

import java.io.IOException;
import java.util.List;

public interface LanguageDAOInterface {

    List<String> getAllLanguages();

    Language searchById(String id) throws IOException;

    boolean saveLanguage(Language language);

    boolean deleteLanguage(Language language);
}
