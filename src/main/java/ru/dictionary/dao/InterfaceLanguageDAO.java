package ru.dictionary.dao;

import ru.dictionary.model.Language;
import ru.dictionary.model.Word;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface InterfaceLanguageDAO {

    List<Language> getAllLanguages();

    Language searchByUUID(UUID languageUUID);

    boolean saveLanguage(Language language);

    boolean deleteLanguage(Language language);

    List<Language> findAllLanguagesByWordList(List<Word> listWord);
}
