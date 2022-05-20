package Dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * This class implements methods for opening and closing I/O streams
 */
public class IOStream {

    private final String pathToDictionary = System.getProperty("user.dir") + File.separator + "resources" + File.separator;
    BufferedReader br;
    FileWriter fw;

    public IOStream() {
        createFolder();
    }

    public void createFolder(){
        File folder = new File(pathToDictionary);
        if(!folder.exists()) {
            try {
                if (folder.mkdir()) {
                    System.out.println("Directory created");
                } else {
                    System.out.println("Directory is not created");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public File createFile(String fileName) {
        File file = new File(pathToDictionary + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }

    public BufferedReader getBufferedReader(String fileName){
        try{
            FileReader fr = new FileReader(pathToDictionary + File.separator + fileName, StandardCharsets.UTF_8);
            br = new BufferedReader(fr);
            return br;
        } catch (IOException e) {
            System.out.println("Cannot read File in path - " + pathToDictionary + "; with name - " + fileName);
        }
        return br;
    }

    public FileWriter getFileWriter(String fileName){
        try {
            fw = new FileWriter(pathToDictionary + File.separator + fileName, StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fw;
    }

    public void closeBufferedReader(){
        try {
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeFileWriter(){
        try {
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
