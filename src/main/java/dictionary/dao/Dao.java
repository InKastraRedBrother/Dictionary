package dictionary.dao;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Contains business logic
 */
public class Dao {
    private static final String PATH_AND_FILENAME = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "Sym.txt";
    private static final String TEMPORARY_FILENAME = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "temp.txt";
    private static final String KEY_VALUE_SEPARATOR = ":";


    /**
     * Create file if file not exists
     *
     * @param fileName String that contains Path to file and its name
     * @return created file
     */
    private File createFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }

    /**
     * Get all rows from file
     *
     * @return String that have all rows
     */
    public String showAll() {
        createFile(PATH_AND_FILENAME);
        StringBuilder sf = null;
        try (FileReader fileReader = new FileReader(PATH_AND_FILENAME, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String lineList;
            sf = new StringBuilder();
            while ((lineList = bufferedReader.readLine()) != null) {
                sf.append(lineList).append("\n");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return String.valueOf(sf);
    }

    /**
     * @param key   key of the added row
     * @param value value of the added row
     * @return boolean. if row added - true, else - false
     */
    public boolean add(String key, String value) {
        createFile(PATH_AND_FILENAME);
        boolean isAdded = false;
        try (FileWriter fileWriter = new FileWriter(PATH_AND_FILENAME, StandardCharsets.UTF_8, true)) {
            fileWriter.write("\n" + key + KEY_VALUE_SEPARATOR + value);
            isAdded = true;
        } catch (IOException e) {
            System.out.println(e);
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
        createFile(PATH_AND_FILENAME);
        String message = null;
        try (FileReader fileReader = new FileReader(PATH_AND_FILENAME, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(key + KEY_VALUE_SEPARATOR)) {
                    message = line;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return message;
    }

    /**
     * Delete row by key
     *
     * @param key by what parameter to search for a row that should be deleted
     * @return boolean. true - if row was found. false - if not
     */
    public boolean delete(String key) throws Exception {
        createFile(PATH_AND_FILENAME);
        createFile(TEMPORARY_FILENAME);
        boolean isExist = false;
        try (FileWriter fileWriter = new FileWriter(TEMPORARY_FILENAME, StandardCharsets.UTF_8, true);
             FileReader fileReader = new FileReader(PATH_AND_FILENAME, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {

                if (line.contains(key)) {
                    isExist = true;
                }
                if (!line.contains(key) && !line.isBlank()) {
                    fileWriter.write(line);
                    fileWriter.write(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            Exception IOException = null;
            throw IOException;
        }

        File file = new File(PATH_AND_FILENAME);
        file.delete();
        File tempFile = new File(TEMPORARY_FILENAME);
        tempFile.renameTo(new File(PATH_AND_FILENAME));

        return isExist;
    }
}
