package Dictionary;

import java.util.Objects;

/**
 * Close provide consoleApp view and selection of dictionary and operation
 */
public class Start {
    /**  Option for Symbolic Dictionary
     */
    public static final String FILE_SYM = "1";
    /**  Option for Numeric Dictionary
     */
    public static final String FILE_NUM = "2";
    private static final String DICTIONARY_SELECTION = "Choose dictionary type: " + FILE_SYM + " - Symbolic; " + FILE_NUM + " - Numeric : ";
    private static final String COMMAND_SEARCH = "1";
    private static final String COMMAND_SHOW_ALL = "2";
    private static final String COMMAND_ADD = "3";
    private static final String COMMAND_DELETE = "4";
    private static final String OPERATION_SELECTION = "Choose dictionary's destiny: " + COMMAND_SEARCH + " - Search; " + COMMAND_SHOW_ALL  + " - Show all; " + COMMAND_ADD + "- Add to the end; " + COMMAND_DELETE + " - Delete : ";
    /** Mask for Dictionary selection
     */
    private static final String DICTIONARY_PATTERN = "^[1-2]{1}+$";
    /** Mask for operation selection
     */
    private static final String OPERATION_PATTERN = "^[1-4]{1}+$";
    private static final String ERR_INVALID_OPERATION = "Invalid operation";

    CommunicationWithConsole communicationWithConsole;
    Dictionary dictionary;
    DictionaryInitialization dictionaryInitialization;

    /** Constructor for DI
     * @param communicationWithConsole DI
     * @param dictionaryInitialization DI
     */
    public Start(CommunicationWithConsole communicationWithConsole, DictionaryInitialization dictionaryInitialization) {
        this.communicationWithConsole = communicationWithConsole;
        this.dictionaryInitialization = dictionaryInitialization;
    }

    /**
     * Method where user choose Dictionary type and operation via input
     */
    public void runApp() {
        while (true) {

            String s = communicationWithConsole.inputInConsole(DICTIONARY_SELECTION, DICTIONARY_PATTERN);
            dictionary = dictionaryInitialization.getS(s);

            s = communicationWithConsole.inputInConsole(OPERATION_SELECTION, OPERATION_PATTERN).trim().strip();

            if (Objects.equals(s, COMMAND_SEARCH)) {
                dictionary.search();
            } else if (Objects.equals(s, COMMAND_SHOW_ALL)) {
                dictionary.showAll();
            } else if (Objects.equals(s, COMMAND_ADD)) {
                dictionary.add();
            } else if (Objects.equals(s, COMMAND_DELETE)) {
                dictionary.deleteEntry();
            } else {
                System.out.println(ERR_INVALID_OPERATION);
                break;}
        }
    }
}
