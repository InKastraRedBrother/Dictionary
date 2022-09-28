package ru.dictionary.dao;

import ru.dictionary.model.Language;
import ru.dictionary.model.Word;

import java.util.List;
import java.util.UUID;

public interface InterfaceLanguageDAO {

    List<Language> getAll();

    Language find(UUID languageUUID);

    boolean save(Language language);

    boolean delete(Language language);

    List<Language> find(List<Word> listWord);
}
