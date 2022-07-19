package dictionary.dao;

import dictionary.exception.DictionaryNotFoundException;
import dictionary.model.Row;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.PatternSyntaxException;

/**
 * Contains business logic.
 */
public class Dao implements DaoInterface {
    private static final String PATH_TO_DIRECTORY = System.getProperty("user.dir") + File.separator + "out" + File.separator + "resources" + File.separator;
    private static final String TEMPORARY_FILENAME = "temp.txt";

    private final Codec codec;

    /**
     * Empty constructor that create directory for storage files, if they not exist.
     *
     * @throws DictionaryNotFoundException If a security manager exists and its SecurityManager.checkRead(String) method denies read access to the file(SecurityException).
     *                                     If the <code>pathname</code> argument is <code>null</code> (NullPointerException).
     */
    public Dao() {
        this.codec = new Codec();
        try {
            File directory = new File(PATH_TO_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdir();
            }
        } catch (SecurityException | NullPointerException e) {
            throw new DictionaryNotFoundException();
        }
    }

    /**
     * Create file if file not exists.
     *
     * @param fileName String that contains Path to file and its name.
     * @return created file.
     * @throws DictionaryNotFoundException if path will be invalid (IOException).
     *                                     If a security manager exists and its SecurityManager.checkRead(String) method denies read access to the file(SecurityException).
     */
    private File createFile(String fileName) {
        try {
            File file = new File(PATH_TO_DIRECTORY + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            return file;
        } catch (IOException | SecurityException e) {
            throw new DictionaryNotFoundException();
        }
    }

    /**
     * Get all rows from file.
     *
     * @return String that have all rows.
     * @throws DictionaryNotFoundException if no line was found (NoSuchElementException).
     *                                     if scanner is closed (IllegalStateException).
     */
    public List<Row> findAll(String fileName) {
        File file = createFile(fileName);
        List<Row> listRow = new ArrayList<>();
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                listRow.add(codec.convertStorageEntryToKV(sc.nextLine()));
            }
        } catch (IOException | NoSuchElementException | IllegalStateException e) {
            throw new DictionaryNotFoundException();
        }
        return listRow;
    }

    /**
     * Save given pair - key value in storage.
     *
     * @param row class that contains String key, String value
     * @return boolean. if row added - true.
     * @throws DictionaryNotFoundException if the file exists but is a directory rather than
     *                                     a regular file, does not exist but cannot be created,
     *                                     or cannot be opened for any other reason (IOException).
     *                                     If a security manager exists and its SecurityManager.checkRead(String) method denies read access to the file(SecurityException).
     */
    public boolean save(Row row, String fileName) {
        File file = createFile(fileName);
        try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, true)) {
            if (file.length() != 0) {
                fileWriter.write(System.lineSeparator());
            }
            fileWriter.write(codec.convertKVToStorageEntry(row));
        } catch (IOException | SecurityException e) {
            throw new DictionaryNotFoundException();
        }
        return true;
    }

    /**
     * compare input row with rows in file.
     *
     * @param inputtedKeyForSearch by what parameter to search for a string.
     * @return String message that contains null or searched row.
     * @throws DictionaryNotFoundException if no line was found (NoSuchElementException).
     *                                     if scanner is closed (IllegalStateException).
     *                                     if the file is not found (IOException).
     */
    public Optional<Row> findByKey(String inputtedKeyForSearch, String fileName) {
        File file = createFile(fileName);
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Row row = codec.convertStorageEntryToKV(line);
                if (row.getKey().equals(inputtedKeyForSearch)) {
                    return Optional.of(row);
                }
            }
        } catch (IOException | NoSuchElementException | IllegalStateException e) {
            throw new DictionaryNotFoundException();
        }
        return Optional.empty();
    }

    /**
     * Delete row by key.
     *
     * @param inputtedKeyForDeletion by what parameter to search for a row that should be deleted.
     * @return boolean. true - if row was found and deleted. false - if not.
     * @throws DictionaryNotFoundException if no line was found (NoSuchElementException).
     *                                     if this scanner is closed(IllegalStateException).
     *                                     If a security manager exists and its SecurityManager.checkDelete method denies delete access to the file (SecurityException).
     *                                     If parameter <code>mainFile</code> is <code>null</code> (NullPointerException).
     */
    public boolean deleteByKey(String inputtedKeyForDeletion , String fileName) {
        boolean isExist = false;
        if ((findByKey(inputtedKeyForDeletion, fileName).isPresent())) {
            boolean isFirstRow = true;
            File mainFile = createFile(fileName);
            File tempFile = createFile(TEMPORARY_FILENAME);
            try (FileWriter fileWriter = new FileWriter(tempFile, StandardCharsets.UTF_8, true);
                 Scanner sc = new Scanner(mainFile)) {

                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    Row row = codec.convertStorageEntryToKV(line);
                    if (row.getKey().equals(inputtedKeyForDeletion)) {
                        isExist = true;
                    } else {
                        if (isFirstRow) {
                            isFirstRow = false;
                        } else {
                            fileWriter.write(System.lineSeparator());
                        }
                        fileWriter.write(codec.convertKVToStorageEntry(row));
                    }
                }
            } catch (IOException | NoSuchElementException | IllegalStateException | NullPointerException |
                     SecurityException e) {
                throw new DictionaryNotFoundException();
            }
            try {
                mainFile.delete();
                tempFile.renameTo(mainFile);
            } catch (SecurityException | NullPointerException e){
                throw new DictionaryNotFoundException();
            }

        }
        return isExist;
    }

    /**
     * Encapsulates the format in which the line in the file is stored.
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
            return row.getKey() + KEY_VALUE_SEPARATOR_FOR_STORAGE + row.getValue();
        }

        /**
         * Convert String from file to separate variables.
         *
         * @param lineFromFile line from file.
         * @throws DictionaryNotFoundException <code>encode.length</code> is smaller than <code>NUMBER_FOR_SPLIT</code>(ArrayIndexOutOfBoundsException).
         *                                     if the regular expression's syntax is invalid(PatternSyntaxException).
         */
        public Row convertStorageEntryToKV(String lineFromFile) {
            try {
                String[] encode = lineFromFile.split(KEY_VALUE_SEPARATOR_FOR_STORAGE, NUMBER_FOR_SPLIT);
                return new Row(encode[KEY_SERIAL_NUMBER], encode[VALUE_SERIAL_NUMBER]);

            } catch (ArrayIndexOutOfBoundsException | PatternSyntaxException e) {
                throw new DictionaryNotFoundException();
            }
        }
    }
}
