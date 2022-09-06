package ru.dictionary.dao;

import ru.dictionary.model.Language;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class LanguageDAO implements LanguageDAOInterface {

    @Override
    public Optional<List<Language>> getAllLanguages() {

        return Optional.empty();
    }

    @Override
    public Optional<Language> getLanguageById(String id) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/qqq");
        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                String lineFromFile = sc.nextLine();
                Language language = codec
//                Row row = codec.convertStorageEntryToKV(line);
//                if (row.getId_word_key().equals(inputtedKeyForSearch)) {
//                    return Optional.of(row);
//                }
//            }
//        } catch (IOException | NoSuchElementException | IllegalStateException e) {
//            throw new DictionaryNotFoundException("findByKey");
//        }
//        return Optional.empty();

            }
            return Optional.empty();
        }
    }

    @Override
    public void saveLanguage() {

    }

    @Override
    public void deleteLanguage() {

    }

    private static class Codec {

        private static final String KEY_VALUE_SEPARATOR_FOR_STORAGE = ":";
        private static final int NUMBER_FOR_SPLIT = Language.class.getFields().length;
        private static final int KEY_SERIAL_NUMBER = 0;
        private static final int VALUE_SERIAL_NUMBER = 1;

        public void convertStorageFormatToClassFormat(String line){

            return new Language();
        }

    }
}
