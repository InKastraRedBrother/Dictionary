package dictionary.view;

import dictionary.dao.Dao;
import dictionary.exception.DictionaryNotFoundException;
import dictionary.model.DictionaryInit;
import dictionary.model.Row;
import dictionary.service.Service;

import java.io.Console;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

/**
 * Provides input output work.
 */
public class ViewDictionary {

    private static final String INPUT_KEY_MESSAGE = "Input key";
    private static final String INPUT_VALUE_MESSAGE = "Input value";
    private static final String OPERATION_SAVE = "1";
    private static final String OPERATION_FIND_ALL = "2";
    private static final String OPERATION_FIND_BY_KEY = "3";
    private static final String OPERATION_DELETE_BY_KEY = "4";
    private static final String MESSAGE_CHOSE_OPERATION = "Chose operation: " + OPERATION_SAVE + " - save;  " + OPERATION_FIND_ALL + " - findAll; " + OPERATION_FIND_BY_KEY + " - find by key; " + OPERATION_DELETE_BY_KEY + " - delete by key; ";
    private static final String MESSAGE_INVALID_INPUT = "Invalid input. Try again";
    private static final String MESSAGE_ROW_EXIST = "Row with key - %s was founded - %s %n";
    private static final String MESSAGE_ROW_NOT_EXIST = "Didn't find row with key - %s %n";
    private static final String MESSAGE_ROW_DELETED = "Row with key - %s WAS deleted %n";
    private static final String MESSAGE_ROW_NOT_DELETED = "Row with key - %s WAS NOT deleted %n";
    private static final String MESSAGE_ERROR = "Something bad happened";
    private static final String MESSAGE_ADD = "Add key - %s value - %s %n";
    private static final String MESSAGE_CHOSE_DICTIONARY = "Choose dictionary. 1 - symbolic; 2 - numeric";

    Service service;

    /**
     * Constructor.
     *
     * @param service DI.
     */
    public ViewDictionary(Service service) {
        this.service = service;
    }

    DictionaryInit dictionaryInit = new DictionaryInit();

    /**
     * Entry point of the program.
     * Input and output info in console.
     */
    public void runApp() {
        while (true) {
            String s;
            s = inputInviter(MESSAGE_CHOSE_DICTIONARY);
            ArrayList<String> prop = dictionaryInit.getEntry(s);
            if (prop != null) {
                try {
                    s = inputInviter(MESSAGE_CHOSE_OPERATION);
                    if (s.equals(OPERATION_SAVE)) {
                        String key = inputInviter(INPUT_KEY_MESSAGE);
                        String value = inputInviter(INPUT_VALUE_MESSAGE);
                        if (service.addRow(key, value, prop)) {
                            System.out.printf(MESSAGE_ADD, key, value);
                        } else {
                            System.out.println(MESSAGE_INVALID_INPUT);
                        }
                        System.out.println();
                    } else if (s.equals(OPERATION_FIND_ALL)) {
                        for (Row e : service.findAllRows(prop)) {
                            System.out.println(e);
                        }
                    } else if (s.equals(OPERATION_FIND_BY_KEY)) {
                        String key = inputInviter(INPUT_KEY_MESSAGE);
//                        Optional<Row> output = service.findRowByKey(key, prop);

//                        if (output.isPresent()) {
//                            System.out.printf(MESSAGE_ROW_EXIST, key, output.get());
//                        } else {
//                            System.out.printf(MESSAGE_ROW_NOT_EXIST, key);
//                        }

                    } else if (s.equals(OPERATION_DELETE_BY_KEY)) {
                        String key = inputInviter(INPUT_KEY_MESSAGE);
//                        if (service.deleteRowByKey(key, prop)) {
//                            System.out.printf(MESSAGE_ROW_DELETED, key);
//                        } else {
//                            System.out.printf(MESSAGE_ROW_NOT_DELETED, key);
//                        }
                    } else {
                        System.out.println(MESSAGE_INVALID_INPUT);
                    }
                } catch (DictionaryNotFoundException e) {
                    System.out.println(MESSAGE_ERROR + e.getMessage());
                }
            }
        }
    }

    /**
     * Requesting user input via valid input type.
     *
     * @param message show message in console.
     * @return inputted in console String.
     */
    private String inputInviter(String message) {
        System.out.println(message);
        Console console = System.console();
        Scanner sc = new Scanner(System.in);
        if (console != null) {
            return console.readLine();
        } else {
            return sc.nextLine();
        }
    }
}
