package dictionary.dao;

import dictionary.exception.DictionaryNotFoundException;
import dictionary.model.Row;
import dictionary.model.Word;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

/**
 * Contains business logic.
 */
public class Dao {
    private static final String NAME_OF_DIRECTORY = "resources";
    private static final String PATH_TO_DIRECTORY = System.getProperty("user.dir") + File.separator + "out" + File.separator + NAME_OF_DIRECTORY + File.separator;
    private static final String PATH_AND_FILENAME = PATH_TO_DIRECTORY + "Sym.txt";
    private static final String TEMPORARY_FILENAME = PATH_TO_DIRECTORY + "temp.txt";

    /**
     * Empty constructor that create directory for storage files, if they not exist.
     */
    public Dao() {
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
            File file = new File(fileName);
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
    public String findAll() {
        createFile(PATH_AND_FILENAME);
        StringBuilder sf;
        File file = createFile(PATH_AND_FILENAME);
        try (Scanner sc = new Scanner(file)) {
            String lineList;
            sf = new StringBuilder();
            while (sc.hasNextLine()) {
                lineList = sc.nextLine();
                sf.append(lineList).append("\n");
            }
        } catch (IOException e) {
            throw new DictionaryNotFoundException();
        }
        return String.valueOf(sf);
    }

    /**
     * Save given pair - key value in storage.
     *
     * @param key   key of the added row.
     * @param value value of the added row.
     * @return boolean. if row added - true, else - false.
     */
    public boolean save(String key, String value) {
        Row row = new Row(new Word(key), new Word(value));
        Codec codec = new Codec(row);
        File file = createFile(PATH_AND_FILENAME);
        boolean isAdded;
        try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, true)) {
            if (file.length() == 0) {
                fileWriter.write(codec.convertKVToStorageEntry());
            } else {
                fileWriter.write(System.lineSeparator() + codec.convertKVToStorageEntry());
            }
            isAdded = true;
        } catch (IOException e) {
            throw new DictionaryNotFoundException();
        }
        return isAdded;
    }

    /**
     * compare input row with rows in file.
     *
     * @param key by what parameter to search for a string.
     * @return String message that contains null or searched row.
     */
    public Optional<Row> findByKey(String key) {
        Row row = new Row();
        Codec codec = new Codec(row);
        Word wordKey = new Word(key);
        File file = createFile(PATH_AND_FILENAME);
        try (Scanner sc = new Scanner(file)) {

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                row = codec.convertStorageEntryToKV(line);
                if (wordKey.getWord().equals(key)) {
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
    public boolean deleteByKey(String inputtedKey) {
        Row row = new Row();
        Codec codec = new Codec(row);
        boolean isExist = false;
        row.setKey(new Word(inputtedKey));
        if (findByKey(row.getKey().getWord()).isPresent()) {
            boolean isFirstRow = true;
            File mainFile = createFile(PATH_AND_FILENAME);
            File tempFile = createFile(TEMPORARY_FILENAME);
            try (FileWriter fileWriter = new FileWriter(tempFile, StandardCharsets.UTF_8, true);
                 Scanner sc = new Scanner(mainFile)) {
                String line;

                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    row = codec.convertStorageEntryToKV(line);
                    if (row.getKey().getWord().equals(inputtedKey)) {
                        isExist = true;
                    } else {
                        if (isFirstRow) {
                            fileWriter.write(codec.convertKVToStorageEntry());
                            isFirstRow = false;
                        } else {
                            fileWriter.write(System.lineSeparator() + codec.convertKVToStorageEntry());
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
        Row row;
        /**
         * Separate key and value in file row.
         */
        private static final String KEY_VALUE_SEPARATOR_FOR_STORAGE = ":";

        public Codec(Row row) {
            this.row = row;
        }

        public Codec() {
        }

        /**
         * Convert key and value into a String to storage format.
         *
         * @return String consisting of a key and value with a given separator.
         */
        public String convertKVToStorageEntry() {
            return this.row.getKey().getWord() + KEY_VALUE_SEPARATOR_FOR_STORAGE + this.row.getValue().getWord();
        }

        /**
         * Convert String from file to separate variables.
         *
         * @param s line from file.
         */
        public Row convertStorageEntryToKV(String s) {
            try {
                String[] encode = s.split(KEY_VALUE_SEPARATOR_FOR_STORAGE, 2);
                row.setKey(new Word(encode[0]));
                row.setValue(new Word(encode[1]));
                row = new Row(row.getKey(), row.getValue());

            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DictionaryNotFoundException();
            }
            return row;
        }


    }
}
