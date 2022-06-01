package dictionary.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Dao {

    public String showAll(){
        BufferedReader br = null;
            try{
                FileReader fr = new FileReader(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "Sym.txt", StandardCharsets.UTF_8);
                br = new BufferedReader(fr);
            } catch (IOException e) {
                System.out.println("Cannot read File in path - " + System.getProperty("user.dir") + File.separator + "resources" + "; with name - Sym.txt" );
            }
            String lineList;
            StringBuilder sf = new StringBuilder();
            while (true) {
                try {
                    if ((lineList = br.readLine()) == null) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sf.append(lineList).append("\n");
            }
            System.out.println(sf);

            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return String.valueOf(sf);
    }
}
