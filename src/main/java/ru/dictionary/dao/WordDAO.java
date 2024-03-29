package ru.dictionary.dao;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.dictionary.exception.DictionaryNotFoundException;
import ru.dictionary.model.Word;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static ru.dictionary.dao.Util.Util.ELEMENTS_SEPARATOR;
import static ru.dictionary.dao.Util.Util.PATH_TO_STORAGE_DIRECTORY;

@Component
public class WordDAO implements InterfaceWordDAO {
    private static final String TEMPORARY_FILENAME = "tempForWord.txt";
    private static final String TEMPORARY_FILE_PATH_AND_FILENAME = PATH_TO_STORAGE_DIRECTORY + File.separator + TEMPORARY_FILENAME;
    private final Codec codec;

    @Getter
    private final String wordPath;

    public WordDAO(@Value("${word.path}") String path) {
        wordPath = path;

        this.codec = new Codec();

        File directory = new File(PATH_TO_STORAGE_DIRECTORY);
        File wordFile = getWordStorageTxtFile();

        try {
            if ((!directory.mkdir() == directory.exists()) && !(!wordFile.createNewFile() == wordFile.exists())) {
                throw new DictionaryNotFoundException("Error creating storage");
            }
        } catch (IOException e) {
            throw new DictionaryNotFoundException("Error creating storage for words");
        }
    }

    private File getWordStorageTxtFile() {
        return new File(wordPath);
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
        return null;
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

    public List<Word> searchAllByListUUID(List<UUID> keyUUIDList) {
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

    public List<Word> getWordsByWordValue(String wordValue) {
        File file = getWordStorageTxtFile();

        List<Word> listWordsFromStorage = new ArrayList<>();

        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                Word word = codec.convertFromStorageFormatToObjectFormat(sc.nextLine());
                if (word.getWordValue().equals(wordValue)) {
                    listWordsFromStorage.add(word);
                }
            }
        } catch (NullPointerException | NoSuchElementException | IllegalStateException | IOException e) {
            throw new DictionaryNotFoundException("findAll languages");
        }
        return listWordsFromStorage;
    }

    public boolean deleteById(UUID wordUUID) {
        boolean isExistWordInStorage = false;
        boolean isFirstRow = true;
        File rowsStorage = getWordStorageTxtFile();
        File tempFile = new File(TEMPORARY_FILE_PATH_AND_FILENAME);
        try (FileWriter fileWriter = new FileWriter(tempFile, StandardCharsets.UTF_8, true);
             Scanner sc = new Scanner(rowsStorage)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Word word = codec.convertFromStorageFormatToObjectFormat(line);
                if (word.getWordUUID().equals(wordUUID)) {
                    isExistWordInStorage = true;
                } else {
                    if (isFirstRow) {
                        isFirstRow = false;
                    } else {
                        fileWriter.write(System.lineSeparator());
                    }
                    fileWriter.write(codec.convertFromObjectFormatToStorageFormat(word));
                }
            }
        } catch (IOException | NoSuchElementException | IllegalStateException | NullPointerException |
                 SecurityException e) {
            throw new DictionaryNotFoundException("deleteByKey");
        }
        try {
            if (!rowsStorage.delete() || !tempFile.renameTo(rowsStorage)) {
                throw new DictionaryNotFoundException("deleteByKey");
            }

        } catch (SecurityException | NullPointerException e) {
            throw new DictionaryNotFoundException("deleteByKey");
        }
        return isExistWordInStorage;
    }

    @Override
    public List<Word> searchListWordsByValue(String wordFromSearchForm) {

        File fileWithWords = getWordStorageTxtFile();
        List<Word> listWord = new ArrayList<>();
        try (Scanner sc = new Scanner(fileWithWords, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                var wordFromFileEncodedFromString = codec.convertFromStorageFormatToObjectFormat(sc.nextLine());
                if (wordFromFileEncodedFromString.getWordValue().equals(wordFromSearchForm)) {
                    listWord.add(wordFromFileEncodedFromString);
                }
            }
        } catch (IOException e) {
            throw new DictionaryNotFoundException("WORD searchByValue");
        }
        return listWord;
    }

    @Override
    public Word searchWordByValue(String wordValueFromView) {
        File fileWithWords = getWordStorageTxtFile();
        Word word = null; //TODO chto delat'. problema s optional
        try (Scanner sc = new Scanner(fileWithWords, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                Word wordFromFileEncodedFromString = codec.convertFromStorageFormatToObjectFormat(sc.nextLine());
                if (wordFromFileEncodedFromString.getWordValue().equals(wordValueFromView)) {
                    word = wordFromFileEncodedFromString;
                    break;
                }
            }
        } catch (IOException e) {
            throw new DictionaryNotFoundException("WORD searchByValue");
        }
        return word;
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
