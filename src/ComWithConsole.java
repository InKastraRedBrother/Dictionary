import java.io.Console;
import java.util.Scanner;

public class ComWithConsole {

    private final Scanner in = new Scanner(System.in);
    private final Console console = System.console();

    public String inputInConsole() {
        if (console != null) {
            return console.readLine();
        } else {
            return in.nextLine();
        }
    }
}
