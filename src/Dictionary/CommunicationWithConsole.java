package Dictionary;

import java.io.Console;
import java.util.Scanner;

/**
 * Class provide implementations of methods which comparing input and mask, and selection input option
 */
public class CommunicationWithConsole {
    String s;  //if s will be declared in InputInConsole method , then it will be "" in some circumstances (1st input "" (just Enter) 2nd input matches(1 or 2))

    /**
     * Empty constructor
     */
    public CommunicationWithConsole() {
    }

    /** Output message for user. Chooses valid console to input String.
     * Validate input string.
     * Method uses recursion for checking input string
     *
     * @param selectionType get String which will be printed
     * @param pattern check if input String matches geted pattern
     * @return Valid String from valid console input
     */
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
}


