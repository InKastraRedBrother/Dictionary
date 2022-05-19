package Dictionary;

import java.io.Console;
import java.util.Scanner;

public class CommunicationWithConsole {

    private final Scanner in = new Scanner(System.in);
    private final Console console = System.console();

    public String inputInConsole() {
        if (console != null) {
            return console.readLine();
        } else {
            return in.nextLine();
        }
    }

    /**
     * Printing messages
     */
    public static final String FILE_SYM = "sym";
    public static final String FILE_NUM = "num";
    private static final String DICTIONARY_SELECTION = "Choose dictionary type: " + FILE_SYM + " - Symbolic; " + FILE_NUM + " - Numeric : ";
    private static final String OPERATION_SELECTION = "Choose dictionary's destiny: 1 - Search; 2 - Show all; 3 - Add to the end; 4 - Delete : ";
    private static final String ERR_INVALID_OPERATION = "Invalid operation";
    private static final String INPUT_KEY = "Input key";
    private static final String INPUT_VALUE = "Input value";
    private static final String PRINT_ERR_KEY_NOT_FOUND = "Key not found";
    private static final String MASK_ERROR = "The entered key or value do NOT match the constraints";
    public void choseDictionary(){
        System.out.println(DICTIONARY_SELECTION);
    }
    public void choseOperation() {
        System.out.println(OPERATION_SELECTION);
    }
    public void errMessageUnsupportedOperation() {
        System.out.println(ERR_INVALID_OPERATION);
    }
    public void inputKey(){
        System.out.println(INPUT_KEY);
    }
    public void inputValue(){
        System.out.println(INPUT_VALUE);
    }
    public void printErrKeyNotFound(){
        System.out.println(PRINT_ERR_KEY_NOT_FOUND);
    }
    public void printErrMask(){
        System.out.println(MASK_ERROR);
    }
    public void printDeleteEntry(String s){
        System.out.println("String with key " + s + " has been deleted");
    }

}


