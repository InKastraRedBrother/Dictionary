package dictionary.view;

import dictionary.model.Dictionary;
import dictionary.controller.DictionaryInitialization;

import java.io.Console;
import java.util.Objects;
import java.util.Scanner;

/**
 * Close provide consoleApp view and selection of dictionary and operation
 */
public class Start {
    /**  Option for Symbolic java.dictionary.model.Dictionary
     */
    public static final String FILE_SYM = "1";
    /**  Option for Numeric java.dictionary.model.Dictionary
     */
    public static final String FILE_NUM = "2";
    private static final String DICTIONARY_SELECTION = "Choose dictionary type: " + FILE_SYM + " - Symbolic; " + FILE_NUM + " - Numeric : ";
    private static final String COMMAND_SEARCH = "1";
    private static final String COMMAND_SHOW_ALL = "2";
    private static final String COMMAND_ADD = "3";
    private static final String COMMAND_DELETE = "4";
    private static final String OPERATION_SELECTION = "Choose dictionary's destiny: " + COMMAND_SEARCH + " - Search; " + COMMAND_SHOW_ALL  + " - Show all; " + COMMAND_ADD + "- Add to the end; " + COMMAND_DELETE + " - Delete : ";
    /** Mask for java.dictionary.model.Dictionary selection
     */
    private static final String DICTIONARY_PATTERN = "^[1-2]{1}+$";
    /** Mask for operation selection
     */
    private static final String OPERATION_PATTERN = "^[1-4]{1}+$";
    private static final String ERR_INVALID_OPERATION = "Invalid operation";
    Dictionary dictionary;
    DictionaryInitialization dictionaryInitialization;

    /** Constructor for DI
     * @param dictionaryInitialization DI
     */
    public Start(DictionaryInitialization dictionaryInitialization) {
        this.dictionaryInitialization = dictionaryInitialization;
    }

    /**
     * Method where user choose java.dictionary.model.Dictionary type and operation via input
     */
    public void runApp() {
        while (true) {
            System.out.println(DICTIONARY_SELECTION);
            String s = consoleChooser();
            if (s.matches(DICTIONARY_PATTERN)) {
                dictionary = dictionaryInitialization.getS(s);

                s = consoleChooser().trim().strip();

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
                    break;
                }
            } else {
                System.out.println(ERR_INVALID_OPERATION);
            }
        }
    }

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
