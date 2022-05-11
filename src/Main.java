import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

class Main {

    public static void main(String[] args) throws IOException {

        Dictionary dictionary;

        while (true){
            System.out.print("Choose dictionary type: 1 - Symbolic; 2 - Numeric : ");
            BufferedReader brInputForType = new BufferedReader(new InputStreamReader(System.in));
            String sInput = brInputForType.readLine();

            switch (sInput){
                case ("1"): {
                    String fileName = "Sym.txt";
                    String pattern = "^[a-z]+$";
                    int limit = 4;
                    dictionary = new Dictionary(fileName, pattern, limit);

                    break;
                }
                case ("2"): {
                    String fileName = "Nym.txt";
                    String pattern = "^[0-9]+$";
                    int limit = 5;
                    dictionary = new Dictionary(fileName, pattern, limit);

                    break;
                }
                default:
                    System.out.println("Недопустимая команда");
                    return;
            }

            System.out.print("Choose dictionary's destiny: 1 - Search; 2 - Show all; 3 - Add to the end; 4 - Delete : ");

            BufferedReader brInputForOperation = new BufferedReader(new InputStreamReader(System.in));
            String sInputForOperation = brInputForOperation.readLine();

           switch (sInputForOperation) {
                case ("1"): {
                    dictionary.search();
                    break;
                }
                case ("2"): {
                    dictionary.showAll();
                    break;
                }

                case ("3"): {
                    dictionary.add();
                    break;
                }
                case ("4"): {
                    dictionary.deleteEntry();
                    break;
                }
                default: System.out.println("Недопустимая команда");
           }
        }
    }
}




