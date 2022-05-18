package Dictionary;

public class Start {
    private static final String PATTERN_SYM = "^[a-z]{4}+$";
    private static final String PATTERN_NUM = "^[0-9]{5}+$";

    private static final String COMMAND_SEARCH = "search";
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_SHOW_ALL = "show";
    CommunicationWithConsole communicationWithConsole;

    public Start(CommunicationWithConsole communicationWithConsole) {
        this.communicationWithConsole = new CommunicationWithConsole();
    }

    public void runApp() {
        Dictionary dictionary;

        while (true) {

            CommunicateMessage.choseDictionary();
            String s = communicationWithConsole.inputInConsole();
            switch (s) {
                case (CommunicateMessage.FILE_SYM): {
                    dictionary = new Dictionary(CommunicateMessage.FILE_SYM, PATTERN_SYM);
                    break;
                }
                case (CommunicateMessage.FILE_NUM): {
                    dictionary = new Dictionary(CommunicateMessage.FILE_NUM, PATTERN_NUM);
                    break;
                }
                default:
                    CommunicateMessage.errMessageUnsupportedOperation();
                    return;
            }

            CommunicateMessage.choseOperation();

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
                    CommunicateMessage.errMessageUnsupportedOperation();
            }
        }
    }
}
