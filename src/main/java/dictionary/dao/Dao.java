package dictionary.dao;

import dictionary.exception.DictionaryNotFoundException;
import dictionary.model.Row;
import dictionary.model.Word;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.PatternSyntaxException;

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
     * @throws IOException       If an I/O error occurred
     * @throws SecurityException If a security manager exists and its {@link
     *                           java.lang.SecurityManager#checkWrite(java.lang.String)}
     *                           method denies write access to the file
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
     * @throws NoSuchElementException if no line was found
     * @throws IllegalStateException  if this scanner is closed
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
     * @param key   key of the added row.
     * @param value value of the added row.
     * @return boolean. if row added - true.
     * @throws IOException if the file exists but is a directory rather than
     *                     a regular file, does not exist but cannot be created,
     *                     or cannot be opened for any other reason
     */
    public boolean save(String key, String value, String fileName) {
        File file = createFile(fileName);
        try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, true)) {
            if (file.length() != 0) {
                fileWriter.write(System.lineSeparator());
            }
            fileWriter.write(codec.convertKVToStorageEntry(new Row(new Word(key), new Word(value))));
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
     * @throws NoSuchElementException if no line was found
     * @throws IllegalStateException  if this scanner is closed
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
     * @throws NoSuchElementException if no line was found
     * @throws IllegalStateException  if this scanner is closed
     * @throws SecurityException      If a security manager exists and its SecurityManager.checkDelete method denies delete access to the file
     * @throws NullPointerException   If parameter <code>mainFile</code> is <code>null</code>
     */
    public boolean deleteByKey(String inputtedKey, String fileName) {
        boolean isExist = false;
        if ((findByKey(inputtedKey, fileName).isPresent())) {
            boolean isFirstRow = true;
            File mainFile = createFile(fileName);
            File tempFile = createFile(TEMPORARY_FILENAME);
            try (FileWriter fileWriter = new FileWriter(tempFile, StandardCharsets.UTF_8, true);
                 Scanner sc = new Scanner(mainFile)) {

                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    Row row = codec.convertStorageEntryToKV(line);
                    if (row.getKey().getWord().equals(inputtedKey)) {
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
            } catch (IOException | NoSuchElementException | IllegalStateException e) {
                throw new DictionaryNotFoundException();
            }
            try {
                mainFile.delete();
                tempFile.renameTo(mainFile);
            } catch (NullPointerException | SecurityException e) {
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
            return row.getKey().getWord() + KEY_VALUE_SEPARATOR_FOR_STORAGE + row.getValue().getWord();
        }

        /**
         * Convert String from file to separate variables.
         *
         * @param lineFromFile line from file.
         * @throws ArrayIndexOutOfBoundsException <code>encode.length</code> is smaller than <code>NUMBER_FOR_SPLIT</code>
         * @throws PatternSyntaxException         if the regular expression's syntax is invalid
         */
        public Row convertStorageEntryToKV(String lineFromFile) {
            try {
                String[] encode = lineFromFile.split(KEY_VALUE_SEPARATOR_FOR_STORAGE, NUMBER_FOR_SPLIT);
                return new Row(new Word(encode[KEY_SERIAL_NUMBER]), new Word(encode[VALUE_SERIAL_NUMBER]));

            } catch (ArrayIndexOutOfBoundsException | PatternSyntaxException e) {
                throw new DictionaryNotFoundException();
            }
        }
    }
}
