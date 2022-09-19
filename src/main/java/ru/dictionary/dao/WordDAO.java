package ru.dictionary.dao;

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
import static ru.dictionary.dao.Util.Util.PATH_TO_STORAGE_DIRECTORY;

@Component
public class WordDAO {

    private final static String WORD_STORAGE_PATH_AND_FILENAME = PATH_TO_STORAGE_DIRECTORY + File.separator + "word.txt";
    private final Codec codec;


    public WordDAO() throws IOException {
        this.codec = new Codec();

        File directory = new File(PATH_TO_STORAGE_DIRECTORY);
        File wordFile = getWordStorageTxtFile();

        try {
            if ((!directory.mkdir() == directory.exists()) && !(!wordFile.createNewFile() == wordFile.exists())) {
                System.out.println("throw new DictionaryNotFoundException();");
            }
        } catch (IOException e) {
            throw new DictionaryNotFoundException("Error creating storage for words");
        }
    }

    private File getWordStorageTxtFile() {
        return new File(WORD_STORAGE_PATH_AND_FILENAME);
    }

    public void saveWord(Word word) {
        File fileWithWords = getWordStorageTxtFile();
        try (FileWriter fileWriter = new FileWriter(fileWithWords, StandardCharsets.UTF_8, true)) {
            if (fileWithWords.length() != 0) {
                fileWriter.write(System.lineSeparator());
            }
            fileWriter.write(codec.convertFromObjectFormatToStorageFormat(word));
        } catch (IOException | SecurityException e) {
            throw new DictionaryNotFoundException("add form word");
        }
    }

    public Word searchByUUID(UUID UUIDWord) {
        File fileWithWords = getWordStorageTxtFile();
        try (Scanner sc = new Scanner(fileWithWords, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                Word word = codec.convertFromStorageFormatToObjectFormat(sc.nextLine());
                if (word.getWordUUID().equals(UUIDWord)) {
                    return word;
                }
            }
        } catch (IOException | NoSuchElementException | IllegalStateException e) {
            throw new DictionaryNotFoundException("findByKey");
        }
        return null; //TODO вернуть Optional
    }

    public List<Word> searchAllByUUID(UUID languageUUID) {
        List<Word> listOfWordsWithProperLanguage = new ArrayList<>();
        File fileWithWords = getWordStorageTxtFile();
        try (Scanner sc = new Scanner(fileWithWords, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                Word word = codec.convertFromStorageFormatToObjectFormat(sc.nextLine());
                if (word.getWordLanguageUUID().equals(languageUUID)) {
                    listOfWordsWithProperLanguage.add(word);
                }
            }
            return listOfWordsWithProperLanguage;
        } catch (IOException | NoSuchElementException | IllegalStateException e) {
            throw new DictionaryNotFoundException("findByKey");
        }
    }

    public List<Word> searchAllByListUUID(List<UUID> keyUUIDList) { //TODO как правильно реализовать? 1) Вытащить все word в List и сравнить внутри цикла здесь 2) Брать по одному ключу и каждый раз заново идти в файл и искать совпадение?
        File fileWithWords = getWordStorageTxtFile();
        List<Word> wordList = new ArrayList<>();
        try (Scanner sc = new Scanner(fileWithWords, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                for (UUID uuid : keyUUIDList) {
                    var wordFromFileEncodedFromString = codec.convertFromStorageFormatToObjectFormat(sc.nextLine());
                    if (uuid.equals(wordFromFileEncodedFromString.getWordUUID())) {
                        wordList.add(wordFromFileEncodedFromString);
                    }
                }
            }
        } catch (IOException e) {
            throw new DictionaryNotFoundException("searchAllByListUUID");
        }
        return wordList;
    }

    public List<Word> getAllWords() {
        File file = getWordStorageTxtFile();

        List<Word> listWordsFromStorage = new ArrayList<>();

        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                listWordsFromStorage.add(codec.convertFromStorageFormatToObjectFormat(sc.nextLine()));
            }
        } catch (NullPointerException | NoSuchElementException | IllegalStateException | IOException e) {
            throw new DictionaryNotFoundException("findAll languages");
        }
        return listWordsFromStorage;
    }

    private static class Codec {

        private static final int NUMBER_FOR_SPLIT = Word.class.getFields().length;
        private static final int WORD_ID_SERIAL_NUMBER = 0;
        private static final int WORD_VALUE_SERIAL_NUMBER = 1;
        private static final int WORD_LANGUAGE_ID_SERIAL_NUMBER = 2;

        public String convertFromObjectFormatToStorageFormat(Word word) { //mapper
            return word.getWordUUID() + ELEMENTS_SEPARATOR + word.getWordValue() + ELEMENTS_SEPARATOR + word.getWordLanguageUUID();
        }

        public Word convertFromStorageFormatToObjectFormat(String lineFromFile) { //builder
            String[] arrayOfValue = lineFromFile.split(ELEMENTS_SEPARATOR, NUMBER_FOR_SPLIT); //перенести разделенире строки в сервис
            Word word = new Word();
            word.setWordUUID(UUID.fromString(arrayOfValue[WORD_ID_SERIAL_NUMBER]));
            word.setWordValue(arrayOfValue[WORD_VALUE_SERIAL_NUMBER]);
            word.setWordLanguageUUID(UUID.fromString(arrayOfValue[WORD_LANGUAGE_ID_SERIAL_NUMBER]));
            return word;
        }
    }
}
