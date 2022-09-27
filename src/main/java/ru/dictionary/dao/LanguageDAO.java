package ru.dictionary.dao;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.dictionary.exception.DictionaryNotFoundException;
import ru.dictionary.model.Language;
import ru.dictionary.model.Word;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static ru.dictionary.dao.Util.Util.ELEMENTS_SEPARATOR;

@Component
public class LanguageDAO implements InterfaceLanguageDAO {
    private final static String LANGUAGE_STORAGE_DIRECTORY = System.getProperty("user.dir");
    private final static String LANGUAGE_STORAGE_PATH_AND_FILENAME = LANGUAGE_STORAGE_DIRECTORY + File.separator + "language.txt";
    private final Codec codec;
    @Getter
    private final String wordPath;

    public LanguageDAO(@Value("${language.path}") String path) {
        wordPath = path;
        this.codec = new Codec();

        File directory = new File(LANGUAGE_STORAGE_DIRECTORY);
        File languageFile = getLanguageTxtFile();

        try {
            if ((!directory.mkdir() == directory.exists()) && !(!languageFile.createNewFile() == languageFile.exists())) {
                throw new DictionaryNotFoundException("Error creating storage");
            }
        } catch (IOException e) {
            throw new DictionaryNotFoundException("Error creating storage");
        }
    }

    private File getLanguageTxtFile() {
        return new File(wordPath);
    }

    @Override
    public boolean saveLanguage(Language language) {
        File file = getLanguageTxtFile();
        try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, true)) {
            if (file.length() != 0) {
                fileWriter.write(System.lineSeparator());
            }
            fileWriter.write(codec.convertFromObjectFormatToStorageFormat(language));
        } catch (IOException | SecurityException e) {
            throw new DictionaryNotFoundException("add form language");
        }
        return true;
    }

    @Override
    public boolean deleteLanguage(Language language) {
        return false;
    }

    @Override
    public List<Language> findAllLanguagesByWordList(List<Word> listWord) {
        List<Language> listLanguage = new ArrayList<>();
        File rowFile = getLanguageTxtFile();
        try (Scanner sc = new Scanner(rowFile, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                Language language = codec.convertFromStorageFormatToObjectFormat(sc.nextLine());
                for (Word word : listWord) {
                    if (word.getWordLanguageUUID().equals(language.getLanguageUUID())) {
                        listLanguage.add(language);
                    }
                }
            }
        } catch (NullPointerException | NoSuchElementException | IllegalStateException | IOException e) {
            throw new DictionaryNotFoundException("findAll");
        }
        return listLanguage;
    }

    @Override
    public List<Language> getAllLanguages() {

        File file = getLanguageTxtFile();

        List<Language> listLanguagesFromStorage = new ArrayList<>();

        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                listLanguagesFromStorage.add(codec.convertFromStorageFormatToObjectFormat(sc.nextLine()));
            }
        } catch (NullPointerException | NoSuchElementException | IllegalStateException | IOException e) {
            throw new DictionaryNotFoundException("findAll languages");
        }
        return listLanguagesFromStorage;
    }

    @Override
    public Language searchByUUID(UUID languageUUID) {
        File file = getLanguageTxtFile();
        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                Language language = codec.convertFromStorageFormatToObjectFormat(sc.nextLine());
                if (language.getLanguageUUID().equals(languageUUID)) {
                    return language;
                }
            }
        } catch (IOException | NoSuchElementException | IllegalStateException e) {
            throw new DictionaryNotFoundException("findByKey");
        }
        return null;
    }


    private static class Codec {

        private static final int NUMBER_FOR_SPLIT = Language.class.getFields().length;
        private static final int LANGUAGE_ID_SERIAL_NUMBER = 0;
        private static final int LANGUAGE_NAME_SERIAL_NUMBER = 1;
        private static final int LANGUAGE_RULE_SERIAL_NUMBER = 2;

        public String convertFromObjectFormatToStorageFormat(Language language) { //mapper
            return language.getLanguageUUID() + ELEMENTS_SEPARATOR + language.getLanguageName() + ELEMENTS_SEPARATOR + language.getLanguageRule();
        }

        public Language convertFromStorageFormatToObjectFormat(String lineFromFile) { //builder
            String[] arrayOfValue = lineFromFile.split(ELEMENTS_SEPARATOR, NUMBER_FOR_SPLIT); //перенести разделенире строки в сервис
            Language language = new Language();
            language.setLanguageUUID(UUID.fromString(arrayOfValue[LANGUAGE_ID_SERIAL_NUMBER]));
            language.setLanguageName(arrayOfValue[LANGUAGE_NAME_SERIAL_NUMBER]);
            language.setLanguageRule(arrayOfValue[LANGUAGE_RULE_SERIAL_NUMBER]);
            return language;

        }
    }

}