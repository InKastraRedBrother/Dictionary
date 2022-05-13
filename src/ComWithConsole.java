import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ComWithConsole {

    public String inputInConsole(){
        Scanner in = new Scanner(System.in, StandardCharsets.UTF_8);
        return in.nextLine();
//        Console console = System.console();
//        return console.readLine();
    }
}
