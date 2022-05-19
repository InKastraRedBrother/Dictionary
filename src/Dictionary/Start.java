package Dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Start {


    private static final String COMMAND_SEARCH = "search";
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_SHOW_ALL = "show";
    CommunicationWithConsole communicationWithConsole;
    Dictionary dictionary;
    private final Map<String, Dictionary> hashMap;

    public Start(CommunicationWithConsole communicationWithConsole, Map<String, Dictionary> hashMap) {
        this.communicationWithConsole = communicationWithConsole;
        this.hashMap = hashMap;
    }

    /**
     *
     */

    public void runApp() {

        while (true) {

            System.out.println();

            communicationWithConsole.choseDictionary();  //дописать условие
            String s = communicationWithConsole.inputInConsole();
            dictionary = hashMap.get(s);

            communicationWithConsole.choseOperation();
            s = communicationWithConsole.inputInConsole();

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
