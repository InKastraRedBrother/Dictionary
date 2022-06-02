package dictionary.dao;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Dao {
    private static final String PATH_AND_FILENAME = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "Sym.txt";
    private static final String TEMPORARY_FILENAME = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "temp.txt";
    private static final String KEY_VALUE_SEPARATOR = ":";
//    private static final String FILE_IS_EMPTY = "File is empty";


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

    public String showAll() {
        createFile(PATH_AND_FILENAME);
        StringBuilder sf = null;
//        if (!isFileEmpty()){
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
//        } else {
//            return FILE_IS_EMPTY;
//        }
    }

    public boolean add(String key, String value) {
        createFile(PATH_AND_FILENAME);
        boolean isAdded = false;
        try (FileWriter fileWriter = new FileWriter(PATH_AND_FILENAME, StandardCharsets.UTF_8, true);
             FileReader fileReader = new FileReader(PATH_AND_FILENAME, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            fileWriter.write("\n" + key + KEY_VALUE_SEPARATOR + value);
            isAdded = true;
//            System.out.println(KEY_VALUE_ADDED + key + KEY_VALUE_SEPARATOR + value);
        } catch (IOException e) {
            System.out.println(e);
        }
        return isAdded;
    }

    public String search(String key) {
        createFile(PATH_AND_FILENAME);
//        if(!isFileEmpty()){
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
//                return FNF_MESSAGE;
        }
        return message;
//        } else{
//            return FILE_IS_EMPTY;
//        }
    }

    public boolean delete(String key) {
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
            System.out.println(e);
        }

        File file = new File(PATH_AND_FILENAME);
        file.delete();
        File tempFile = new File(TEMPORARY_FILENAME);
        tempFile.renameTo(new File(PATH_AND_FILENAME));

        return isExist;
    }

//    public boolean isFileEmpty() {
//        createFile(PATH_AND_FILENAME);
//        File file = new File(PATH_AND_FILENAME);
//        return file.length() == 0;
//    }
}
