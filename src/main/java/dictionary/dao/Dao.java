package dictionary.dao;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Dao {
    private static final String PATH_AND_FILENAME = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "Sym.txt";
    private static final String FNF_MESSAGE = "Cannot read File in path - ";
    private static final String KEY_VALUE_SEPARATOR = ":";
    private static final String KEY_VALUE_ADDED = "Строка добавлена - ";
    private static final String NOT_FOUND_LINE = "Строка не найдена ";
    private static final String FOUND_LINE = "Строка найдена - ";
    private static final String FILE_IS_EMPTY = "File is empty";
    private static final String TEMPORARY_FILENAME = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "temp.txt";




    public String showAll() {
        StringBuilder sf = null;
        if (!isFileEmpty()){
            try (FileReader fr = new FileReader(PATH_AND_FILENAME, StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(fr)) {
                String lineList;
                sf = new StringBuilder();
                while((lineList = br.readLine()) != null) {
                    sf.append(lineList).append("\n");
                }
            } catch (IOException e) {
                System.out.println(FNF_MESSAGE + PATH_AND_FILENAME);
            }
            return String.valueOf(sf);
        } else {
            return FILE_IS_EMPTY;
        }
    }

    public String add(String key, String value) {
        String message ="";
        String line;
        try (FileWriter fw = new FileWriter(PATH_AND_FILENAME, StandardCharsets.UTF_8, true);
             FileReader fr = new FileReader(PATH_AND_FILENAME, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(fr)) {
            while ((line = br.readLine()) != null) {
                if (line.contains(key + KEY_VALUE_SEPARATOR)) {
                    message = FOUND_LINE + line;
                    break;
                } else {
                    message = NOT_FOUND_LINE ;
                }
            }
            if(message.equals(NOT_FOUND_LINE)) {
                fw.write(key + KEY_VALUE_SEPARATOR + value + "\n");
                message = KEY_VALUE_ADDED + key + KEY_VALUE_SEPARATOR + value;
            } else{
                message = "Row already exists";
            }

        } catch (IOException e) {
            message = FNF_MESSAGE;
        }
        return message;
    }

    public String search(String key) {
        if(!isFileEmpty()){
            String message = null;
            try (FileReader fr = new FileReader(PATH_AND_FILENAME, StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(fr)) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains(key + KEY_VALUE_SEPARATOR)) {
                        message = FOUND_LINE + line;
                        break;
                    } else {
                        message = NOT_FOUND_LINE ;
                    }
                }
            } catch (IOException e) {
                return FNF_MESSAGE;
            }
            return message;
        } else{
            return FILE_IS_EMPTY;
        }
    }

    public String delete(String key) {
        String message;

        try(FileWriter fw = new FileWriter(TEMPORARY_FILENAME, StandardCharsets.UTF_8, true);
            FileReader fr = new FileReader(PATH_AND_FILENAME, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(fr)) {
            String line;
            boolean isExist = false;
            while ((line = br.readLine()) != null) {

                if (line.contains(key)) {
                    isExist = true;
                }
                if (!line.contains(key) && !line.isBlank()) {
                    fw.write(line);
                    fw.write(System.lineSeparator());
                }
            }
            if (isExist) {
                message = "LINE_REMOVED" + line;
            } else {
                message = "PRINT_ERR_KEY_NOT_FOUND" + key;
            }

            } catch (IOException e) {
            message = "smth goes wrong";
        }

        File file = new File(PATH_AND_FILENAME);
        file.delete();
        File tempFile = new File(TEMPORARY_FILENAME);
        tempFile.renameTo(new File(PATH_AND_FILENAME));

        return message;
}

    public boolean isFileEmpty() {
        File file = new File(PATH_AND_FILENAME);
        return file.length() == 0;
    }
}
