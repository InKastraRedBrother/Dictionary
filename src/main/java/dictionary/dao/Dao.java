package dictionary.dao;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

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
    private File createFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * Get all rows from file
     *
     * @return String that have all rows
     */
    public String showAll() throws IOException {
        createFile(PATH_AND_FILENAME);
        StringBuilder sf = null;
        File file = createFile(PATH_AND_FILENAME);
        try (Scanner sc = new Scanner(file)){
            String lineList;
            sf = new StringBuilder();
            while(sc.hasNextLine()) {
                lineList = sc.nextLine();
                sf.append(lineList).append("\n");
            }
        }
        return String.valueOf(sf);
    }

    /**
     * @param key   key of the added row
     * @param value value of the added row
     * @return boolean. if row added - true, else - false
     */
    public boolean add(String key, String value) throws IOException {
        createFile(PATH_AND_FILENAME);
        boolean isAdded = false;
        try (FileWriter fileWriter = new FileWriter(PATH_AND_FILENAME, StandardCharsets.UTF_8, true)) {
            fileWriter.write("\n" + key + KEY_VALUE_SEPARATOR + value);
            isAdded = true;
        }
        return isAdded;
    }

    /**
     * compare input row with rows in file
     *
     * @param key by what parameter to search for a string
     * @return String message that contains null or searched row.
     */
    public String search (String key) throws IOException {
        String message = null;
        File file = createFile(PATH_AND_FILENAME);
        try (Scanner sc = new Scanner(file)){
            String line;
            while(sc.hasNextLine()) {
                line = sc.nextLine();
                if (line.contains(key + KEY_VALUE_SEPARATOR)) {
                    message = line;
                    break;
                }
            }
        }
        return message;
    }

    /**
     * Delete row by key
     *
     * @param key by what parameter to search for a row that should be deleted
     * @return boolean. true - if row was found. false - if not
     */
    public boolean delete(String key) throws IOException {
        File mainFile = createFile(PATH_AND_FILENAME);
        File tempFile = createFile(TEMPORARY_FILENAME);
        boolean isExist = false;
        try (FileWriter fileWriter = new FileWriter(TEMPORARY_FILENAME, StandardCharsets.UTF_8, true);
             Scanner sc = new Scanner(mainFile)){
            String line;

            while(sc.hasNextLine()) {
                line = sc.nextLine();
                if (line.contains(key)) {
                    isExist = true;
                }
                if (!line.contains(key) && !line.isBlank()) {
                    fileWriter.write(line + System.lineSeparator());
                }
            }
        }

        File file = new File(PATH_AND_FILENAME);
        file.delete();
        tempFile.renameTo(new File(PATH_AND_FILENAME));

        return isExist;
    }
}
