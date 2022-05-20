package Dictionary;

import java.io.Console;
import java.util.Scanner;

public class CommunicationWithConsole {
    //String s;  //if s will be initialized in InputInConsole method , then it will be "" in some circumstances (1st input "" (just Enter) 2nd input matches(1 or 2))

    public CommunicationWithConsole() {
    }

    String s;

    public String inputInConsole(String selectionType, String pattern) {
        System.out.println(selectionType);
        s = consoleChooser();

        if (!s.matches(pattern)){
            inputInConsole(selectionType, pattern);
        }
        return s;
    }

    /** Select input option
     * @return String
     */
    public String consoleChooser() {
        final Scanner in = new Scanner(System.in);
        final Console console = System.console();
        if (console != null) {
            return console.readLine();
        } else {
            return in.nextLine();
        }
    }


    private static final String INPUT_KEY = "Input key";
    private static final String INPUT_VALUE = "Input value";
    private static final String PRINT_ERR_KEY_NOT_FOUND = "Key not found";
    private static final String MASK_ERROR = "The entered key or value do NOT match the constraints";

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


