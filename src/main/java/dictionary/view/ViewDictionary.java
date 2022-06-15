package dictionary.view;

import dictionary.exception.DictionaryNotFoundException;
import dictionary.model.DictionaryInit;
import dictionary.model.Row;
import dictionary.service.Service;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
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
    private static final String MESSAGE_ERROR = "Something bad happened. ";
    private static final String MESSAGE_ADD = "Add key - %s value - %s %n";
    private static final String MESSAGE_CHOSE_DICTIONARY = "Choose dictionary. 1 - symbolic; 2 - numeric";

    private static final int FILENAME_SERIAL_NUMBER = 1;

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
            String dictionarySelection = inputInviter(MESSAGE_CHOSE_DICTIONARY);
            ArrayList<String> initedDictionary = dictionaryInit.getEntry(dictionarySelection);
            if (initedDictionary != null) {
                try {
                    String operationSelection = inputInviter(MESSAGE_CHOSE_OPERATION);
                    if (operationSelection.equals(OPERATION_SAVE)) {
                        String key = inputInviter(INPUT_KEY_MESSAGE);
                        String value = inputInviter(INPUT_VALUE_MESSAGE);
                        if (service.addRow(key, value, initedDictionary)) {
                            System.out.printf(MESSAGE_ADD, key, value);
                        } else {
                            System.out.println(MESSAGE_INVALID_INPUT);
                        }
                        System.out.println();
                    } else if (operationSelection.equals(OPERATION_FIND_ALL)) {
                        for (Row e : service.findAllRows(initedDictionary.get(FILENAME_SERIAL_NUMBER))) {
                            System.out.println(e);
                        }
                    } else if (operationSelection.equals(OPERATION_FIND_BY_KEY)) {
                        String key = inputInviter(INPUT_KEY_MESSAGE);
                        Optional<Row> output = service.findRowByKey(key, initedDictionary);

                        if (output.isPresent()) {
                            System.out.printf(MESSAGE_ROW_EXIST, key, output.get());
                        } else {
                            System.out.printf(MESSAGE_ROW_NOT_EXIST, key);
                        }

                    } else if (operationSelection.equals(OPERATION_DELETE_BY_KEY)) {
                        String key = inputInviter(INPUT_KEY_MESSAGE);
                        if (service.deleteRowByKey(key, initedDictionary)) {
                            System.out.printf(MESSAGE_ROW_DELETED, key);
                        } else {
                            System.out.printf(MESSAGE_ROW_NOT_DELETED, key);
                        }
                    } else {
                        System.out.println(MESSAGE_INVALID_INPUT);
                    }
                } catch (DictionaryNotFoundException e) {
                    System.out.println(MESSAGE_ERROR + Arrays.toString(e.getStackTrace()));
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
