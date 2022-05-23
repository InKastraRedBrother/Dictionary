package Dictionary;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * This class implements methods which open/close stream to read and write to file, creating Directory and Files if they're missing
 */
public class IOStream {

    public final String FILE_FORMAT = ".txt";
    public final String pathToDictionary = System.getProperty("user.dir") + File.separator + "resources" + File.separator;
    BufferedReader br;
    FileWriter fw;

    /**
     * Empty constructor which create folder for dictionary's files
     */
    public IOStream() {
        createFolder();
    }

    /**
     * Creating folder if it doesn't exist
     */
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

    /**
     * Create file for chosen Dictionary if it doesn't exist
     * @param fileName get name of the file which need to be created
     * @return created file
     */
    public File createFile(String fileName) {
        File file = new File(pathToDictionary + fileName + FILE_FORMAT);
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
     * Open FileReader and BufferedReader streams
     * @param fileName get name of the file which need to be read
     * @return BufferedReader output stream
     */
    public BufferedReader getBufferedReader(String fileName){
        try{
            FileReader fr = new FileReader(pathToDictionary + File.separator + fileName + FILE_FORMAT, StandardCharsets.UTF_8);
            br = new BufferedReader(fr);
            return br;
        } catch (IOException e) {
            System.out.println("Cannot read File in path - " + pathToDictionary + "; with name - " + fileName + FILE_FORMAT);
        }
        return br;
    }

    /**
     * Open FileWriter stream for writing to file
     * @param fileName gets the name of the file to be written to
     * @return FileWriter input stream
     */
    public FileWriter getFileWriter(String fileName){
        try {
            fw = new FileWriter(pathToDictionary + File.separator + fileName + FILE_FORMAT, StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fw;
    }

    /**
     * Close BufferedReader stream
     */
    public void closeBufferedReader(){
        try {
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Close FileWriter stream
     */
    public void closeFileWriter(){
        try {
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
