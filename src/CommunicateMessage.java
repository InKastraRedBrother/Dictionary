/**
 * Printing messages
 */
public class CommunicateMessage {

    public static final String FILE_SYM = "sym";
    public static final String FILE_NUM = "num";
    private static final String DICTIONARY_SELECTION = "Choose dictionary type: " + FILE_SYM + " - Symbolic; " + FILE_NUM + " - Numeric : ";
    private static final String OPERATION_SELECTION = "Choose dictionary's destiny: 1 - Search; 2 - Show all; 3 - Add to the end; 4 - Delete : ";
    private static final String ERR_INVALID_OPERATION = "Invalid operation";
    private static final String INPUT_KEY = "Enter key";
    private static final String INPUT_VALUE = "Input value";
    private static final String PRINT_ERR_KEY_NOT_FOUND = "Key not found";

    public static void choseDictionary(){
        System.out.println(DICTIONARY_SELECTION);
    }
    public static void choseOperation() {
        System.out.println(OPERATION_SELECTION);
    }
    public static void errMessageUnsupportedOperation() {
        System.out.println(ERR_INVALID_OPERATION);
    }
    public static void inputKey(){
        System.out.println(INPUT_KEY);
    }
    public static void inputValue(){
        System.out.println(INPUT_VALUE);
    }
    public static void printErrMask(){
        System.out.println("Введенные ключ или значение не соотвестсвуют ограничениям");
    }
    public static void printErrKeyNotFound(){
        System.out.println(PRINT_ERR_KEY_NOT_FOUND);
    }

    public static void printDeleteEntry(String s){
        System.out.println("String with key " + s + " has been deleted");
    }


}
