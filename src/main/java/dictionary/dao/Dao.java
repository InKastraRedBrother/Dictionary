package dictionary.dao;

import dictionary.exception.DictionaryNotFoundException;
import dictionary.model.Row;
import dictionary.model.Word;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Contains business logic.
 */
public class Dao {
    private static final String PATH_TO_DIRECTORY = System.getProperty("user.dir") + File.separator + "out" + File.separator + "resources" + File.separator;
    private static final String TEMPORARY_FILENAME = "temp.txt";

    private final Codec codec;

    /**
     * Empty constructor that create directory for storage files, if they not exist.
     */
    public Dao() {
        this.codec = new Codec();
        File directory = new File(PATH_TO_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    /**
     * Create file if file not exists.
     *
     * @param fileName String that contains Path to file and its name.
     * @return created file.
     */
    private File createFile(String fileName) {
        try {
            File file = new File(PATH_TO_DIRECTORY + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            return file;
        } catch (IOException e) {
            throw new DictionaryNotFoundException();
        }
    }

    /**
     * Get all rows from file.
     *
     * @return String that have all rows.
     */
    public List<Row> findAll(String fileName) {
        File file = createFile(fileName);
        List<Row> listRow = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                listRow.add(codec.convertStorageEntryToKV(sc.nextLine()));
            }
        } catch (IOException e) {
            throw new DictionaryNotFoundException();
        }
        return listRow;
    }

    /**
     * Save given pair - key value in storage.
     *
     * @param key   key of the added row.
     * @param value value of the added row.
     * @return boolean. if row added - true, else - false.
     */
    public boolean save(String key, String value, String fileName) {
        File file = createFile(fileName);
        try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, true)) {
            if (file.length() == 0) {
                fileWriter.write(codec.convertKVToStorageEntry(new Row(new Word(key), new Word(value))));
            } else {
                fileWriter.write(System.lineSeparator() + codec.convertKVToStorageEntry(new Row(new Word(key), new Word(value))));
            }
        } catch (IOException e) {
            throw new DictionaryNotFoundException();
        }
        return true;
    }

    /**
     * compare input row with rows in file.
     *
     * @param key by what parameter to search for a string.
     * @return String message that contains null or searched row.
     */
    public Optional<Row> findByKey(String key, String fileName) {
        File file = createFile(fileName);
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Row row = codec.convertStorageEntryToKV(line);
                if (row.getKey().getWord().equals(key)) {
                    return Optional.of(row);
                }
            }
        } catch (IOException e) {
            throw new DictionaryNotFoundException();
        }
        return Optional.empty();
    }

    /**
     * Delete row by key.
     *
     * @param inputtedKey by what parameter to search for a row that should be deleted.
     * @return boolean. true - if row was found and deleted. false - if not.
     */
    public boolean deleteByKey(String inputtedKey, String fileName) {
        Row row = new Row();
        boolean isExist = false;
        row.setKey(new Word(inputtedKey));
        if ((findByKey((row.getKey().getWord()), fileName).isPresent())) {
            boolean isFirstRow = true;
            File mainFile = createFile(fileName);
            File tempFile = createFile(TEMPORARY_FILENAME);
            try (FileWriter fileWriter = new FileWriter(tempFile, StandardCharsets.UTF_8, true);
                 Scanner sc = new Scanner(mainFile)) {

                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    row = codec.convertStorageEntryToKV(line);
                    if (row.getKey().getWord().equals(inputtedKey)) {
                        isExist = true;
                    } else {
                        if (isFirstRow) {
                            fileWriter.write(codec.convertKVToStorageEntry(row));
                            isFirstRow = false;
                        } else {
                            fileWriter.write(System.lineSeparator() + codec.convertKVToStorageEntry(row));
                        }
                    }
                }
            } catch (IOException e) {
                throw new DictionaryNotFoundException();
            }

            mainFile.delete();
            tempFile.renameTo(mainFile);
        }

        return isExist;
    }

    /**
     * Encapsulates the view in which the line in the file is stored.
     */
    private static class Codec {
        private static final String KEY_VALUE_SEPARATOR_FOR_STORAGE = ":";
        private static final int NUMBER_FOR_SPLIT = 2;
        private static final int KEY_SERIAL_NUMBER = 0;
        private static final int VALUE_SERIAL_NUMBER = 1;

        /**
         * Convert key and value into a String to storage format.
         *
         * @return String consisting of a key and value with a given separator.
         */
        public String convertKVToStorageEntry(Row row) {
            return row.getKey().getWord() + KEY_VALUE_SEPARATOR_FOR_STORAGE + row.getValue().getWord();
        }

        /**
         * Convert String from file to separate variables.
         *
         * @param s line from file.
         */
        public Row convertStorageEntryToKV(String s) {
            try {
                String[] encode = s.split(KEY_VALUE_SEPARATOR_FOR_STORAGE, NUMBER_FOR_SPLIT);
                return new Row(new Word(encode[KEY_SERIAL_NUMBER]), new Word(encode[VALUE_SERIAL_NUMBER]));

            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DictionaryNotFoundException();
            }
        }
    }
}
