import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

class Main {

    private static final String ERR_UNSUPPORTED_OPERATION = "Недопустимая команда";
    private static final String FILE_SYM = "sym";
    private static final String PATTERN_SYM = "^[a-z]{4}+$";
    private static final String FILE_NUM = "num";
    private static final String PATTERN_NUM = "^[0-9]{5}+$";

    private static final String COMMAND_SEARCH = "search";
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_SHOW_ALL = "show";

    public static void main(String[] args) throws IOException {

        Dictionary dictionary;

        while (true){
            System.out.print("Choose dictionary type: " + FILE_SYM + " - Symbolic; " + FILE_NUM + " - Numeric : ");

            Scanner in = new Scanner(System.in);
            String s = in.nextLine().strip();

            switch (s){
                case (FILE_SYM): {
                    dictionary = new Dictionary(FILE_SYM, PATTERN_SYM);
                    break;
                }
                case (FILE_NUM): {
                    dictionary = new Dictionary(FILE_NUM, PATTERN_NUM);
                    break;
                }
                default:
                    System.out.println(ERR_UNSUPPORTED_OPERATION);
                    return;
            }

            System.out.print("Choose dictionary's destiny: 1 - Search; 2 - Show all; 3 - Add to the end; 4 - Delete : ");

            s = in.nextLine().strip();

            switch (s) {
                case ("1"):
                case (COMMAND_SEARCH):{
                    dictionary.search();
                    break;
                }
                case ("2"):
                case (COMMAND_SHOW_ALL):{
                    dictionary.showAll();
                    break;
                }
                case ("3"):
                case (COMMAND_ADD):{
                    dictionary.add();
                    break;
                }
                case ("4"):
                case (COMMAND_DELETE):{
                    dictionary.deleteEntry();
                    break;
                }
                default: System.out.println(ERR_UNSUPPORTED_OPERATION);
           }
        }
    }
}




