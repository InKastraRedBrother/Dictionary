package dictionary.dao;

import dictionary.exception.DictionaryNotFoundException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Contains business logic
 */
public class Dao {
    private static final String NAME_OF_DIRECTORY = "resources";
    private static final String PATH_AND_FILENAME = System.getProperty("user.dir") + File.separator + NAME_OF_DIRECTORY + File.separator + "Sym.txt";
    private static final String TEMPORARY_FILENAME = System.getProperty("user.dir") + File.separator + NAME_OF_DIRECTORY + File.separator + "temp.txt";
    private static final String PATH_TO_DIRECTORY = System.getProperty("user.dir") + File.separator + NAME_OF_DIRECTORY;

    public Dao() {
        File directory = new File(PATH_TO_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    /**
     * Create file if file not exists
     *
     * @param fileName String that contains Path to file and its name
     * @return created file
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
     * Get all rows from file
     *
     * @return String that have all rows
     */
    public String showAll() {
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
     * @param key   key of the added row
     * @param value value of the added row
     * @return boolean. if row added - true, else - false
     */
    public boolean add(String key, String value) {
        Codec codec = new Codec(key, value);
        File file = createFile(PATH_AND_FILENAME);
        boolean isAdded;
        try (FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8, true)) {
            if (file.length() == 0) {
                fileWriter.write(codec.encodeKVToString());
            } else {
                fileWriter.write(System.lineSeparator() + codec.encodeKVToString());
            }
            isAdded = true;
        } catch (IOException e) {
            throw new DictionaryNotFoundException();
        }
        return isAdded;
    }

    /**
     * compare input row with rows in file
     *
     * @param key by what parameter to search for a string
     * @return String message that contains null or searched row.
     */
    public String search(String key) {
        Codec codec = new Codec();
        String message = null;
        File file = createFile(PATH_AND_FILENAME);
        try (Scanner sc = new Scanner(file)) {
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                codec.decodeKVFromString(line);
                if (codec.getKey().equals(key)) {
                    message = line;
                    break;
                }
            }
        } catch (IOException e) {
            throw new DictionaryNotFoundException();
        }
        return message;
    }

    /**
     * Delete row by key
     *
     * @param inputtedKey by what parameter to search for a row that should be deleted
     * @return boolean. true - if row was found and deleted. false - if not
     */
    public boolean delete(String inputtedKey) {
        Codec codec = new Codec();
        boolean isExist = false;
        if (search(inputtedKey) != null) {
            boolean isFirstRow = true;
            File mainFile = createFile(PATH_AND_FILENAME);
            File tempFile = createFile(TEMPORARY_FILENAME);
            try (FileWriter fileWriter = new FileWriter(tempFile, StandardCharsets.UTF_8, true);
                 Scanner sc = new Scanner(mainFile)) {
                String line;

                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    codec.decodeKVFromString(line);
                    if (codec.getKey().equals(inputtedKey)) {
                        isExist = true;
                    } else {
                        if (isFirstRow) {
                            fileWriter.write(codec.encodeKVToString());
                            isFirstRow = false;
                        } else {
                            fileWriter.write(System.lineSeparator() + codec.encodeKVToString());
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
     * Encapsulates the view in which the line in the file is stored
     */
    private static class Codec {
        /**
         * Separate key and value in file row
         */
        private static final String KEY_VALUE_SEPARATOR_FOR_STORAGE = ":";
        private String key;
        private String value;

        public Codec(){}
        public Codec(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        /**
         * Encodes into a String
         *
         * @return String consisting of a key and value with a given separator
         */
        public String encodeKVToString() {
            return this.key + KEY_VALUE_SEPARATOR_FOR_STORAGE + this.value;
        }

        /**
         * Decode String from file to separate variables
         *
         * @param s line from file.
         */
        public void decodeKVFromString(String s) {
            try {
                String[] encode = s.split(KEY_VALUE_SEPARATOR_FOR_STORAGE, 2);
                this.key = encode[0];
                this.value = encode[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DictionaryNotFoundException();
            }
        }
    }
}
