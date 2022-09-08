package ru.dictionary.dao;

import org.springframework.stereotype.Component;
import ru.dictionary.exception.DictionaryNotFoundException;
import ru.dictionary.model.Language;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static ru.dictionary.dao.Util.Util.ELEMENTS_SEPARATOR;

@Component
public class LanguageDAO implements LanguageDAOInterface {

    private final static String LANGUAGE_STORAGE_DIRECTORY = System.getProperty("user.dir");
    private final static String LANGUAGE_STORAGE_PATH_AND_FILENAME = LANGUAGE_STORAGE_DIRECTORY + File.separator + "language.txt";
    private final Codec codec;


    public LanguageDAO() throws IOException {
        this.codec = new Codec();


        File directory = new File(LANGUAGE_STORAGE_DIRECTORY);
        File languageFile = getLanguageTxtFile();

        try {
            if ((!directory.mkdir() == directory.exists()) && !(!languageFile.createNewFile() == languageFile.exists())) {
                System.out.println("throw new DictionaryNotFoundException();");
            }
        } catch (IOException e) {
            throw new DictionaryNotFoundException("Error creating storage");
        }
    }

    private File getLanguageTxtFile() {
        return new File(LANGUAGE_STORAGE_PATH_AND_FILENAME);
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
    public List<String> getAllLanguages() {

        File file = getLanguageTxtFile();

        List<String> listStringsFromStorage = new ArrayList<>();

        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                listStringsFromStorage.add(sc.nextLine());
            }
        } catch (NullPointerException | NoSuchElementException | IllegalStateException | IOException e) {
            throw new DictionaryNotFoundException("findAll languages");
        }
        return listStringsFromStorage;
    }

    @Override
    public Language searchById(String uuidLanguage) {
        File file = getLanguageTxtFile();
        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                Language language = codec.convertFromStorageFormatToObjectFormat(sc.nextLine());
                if (language.getLanguageId().equals(uuidLanguage)) {
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
//            language = new Language(language.getLanguageName(), language.getLanguageRule()); //TODO норм или нет? либо здесь явный new и через кастомный конструктор
//             TODO либо в сервисе явно через сеттер вызывать uuid
            return language.getLanguageId() + ELEMENTS_SEPARATOR + language.getLanguageName() + ELEMENTS_SEPARATOR + language.getLanguageRule();
        }

        public Language convertFromStorageFormatToObjectFormat(String lineFromFile) { //builder
            String[] arrayOfValue = lineFromFile.split(ELEMENTS_SEPARATOR, NUMBER_FOR_SPLIT); //перенести разделенире строки в сервис
            Language language = new Language();
            language.setLanguageId(arrayOfValue[LANGUAGE_ID_SERIAL_NUMBER]);
            language.setLanguageName(arrayOfValue[LANGUAGE_NAME_SERIAL_NUMBER]);
            language.setLanguageRule(arrayOfValue[LANGUAGE_RULE_SERIAL_NUMBER]);
            return language;

        }
    }

}