package Dictionary;

import java.util.Map;

/**
 *
 */
public class Start {
    public static final String FILE_SYM = "sym";
    public static final String FILE_NUM = "num";
    private static final String DICTIONARY_SELECTION = "Choose dictionary type: " + FILE_SYM + " - Symbolic; " + FILE_NUM + " - Numeric : ";
    private static final String OPERATION_SELECTION = "Choose dictionary's destiny: 1 - Search; 2 - Show all; 3 - Add to the end; 4 - Delete : ";
    private static final String COMMAND_SEARCH = "search";
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_SHOW_ALL = "show";
    private static final String DICTIONARY_PATTERN = "^[1-2]{1}+$";
    private static final String OPERATION_PATTERN = "^[1-4]{1}+$";
    CommunicationWithConsole communicationWithConsole;
    Dictionary dictionary;
    private final Map<String, Dictionary> hashMap;

    public Start(CommunicationWithConsole communicationWithConsole, Map<String, Dictionary> hashMap) {
        this.communicationWithConsole = communicationWithConsole;
        this.hashMap = hashMap;
    }

    public void runApp() {

        while (true) {

            String s = communicationWithConsole.inputInConsole(DICTIONARY_SELECTION, DICTIONARY_PATTERN);
            dictionary = hashMap.get(s);

            s = communicationWithConsole.inputInConsole(OPERATION_SELECTION, OPERATION_PATTERN);

            switch (s) {
                case ("1"):
                case (COMMAND_SEARCH): {
                    dictionary.search();
                    break;
                }
                case ("2"):
                case (COMMAND_SHOW_ALL): {
                    dictionary.showAll();
                    break;
                }
                case ("3"):
                case (COMMAND_ADD): {
                    dictionary.add();
                    break;
                }
                case ("4"):
                case (COMMAND_DELETE): {
                    dictionary.deleteEntry();
                    break;
                }
                default:
                    communicationWithConsole.errMessageUnsupportedOperation();
            }
        }
    }

}
