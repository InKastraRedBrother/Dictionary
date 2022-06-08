package dictionary.view;

import dictionary.exception.DictionaryNotFoundException;
import dictionary.service.Service;

import java.io.Console;
import java.util.Optional;
import java.util.Scanner;

/**
 * Provides console work
 */
public class ViewDictionary {

    private static final String INPUT_KEY_MESSAGE = "Input key";
    private static final String INPUT_VALUE_MESSAGE = "Input value";
    private static final String OPERATION_ADD = "1";
    private static final String OPERATION_SHOW_ALL = "2";
    private static final String OPERATION_SEARCH = "3";
    private static final String OPERATION_DELETE = "4";
    private static final String MESSAGE_CHOSE_OPERATION = "Chose operation: " + OPERATION_ADD + " - add;  " + OPERATION_SHOW_ALL + " - showAll; " + OPERATION_SEARCH + " - search; " + OPERATION_DELETE + " - delete; ";
    private static final String MESSAGE_INVALID_INPUT = "Invalid input. Try again";
    private static final String MESSAGE_ROW_EXIST = "Row with key - %s was founded - %s";
    private static final String MESSAGE_ROW_NOT_EXIST = "Didn't find row with key - %s";
    private static final String MESSAGE_ROW_DELETED = "Row with key WAS deleted";
    private static final String MESSAGE_ROW_NOT_DELETED = "Row with key WAS NOT deleted";
    private static final String MESSAGE_ERROR = "Something bad happened";
    private static final String MESSAGE_ADD = "Add key - %s value - %s";

    Service service;

    /**
     * Constructor
     *
     * @param service DI
     */
    public ViewDictionary(Service service) {
        this.service = service;
    }

    /**
     * Entry point of the program
     * Input and output info in console
     */
    public void runApp() {
        while (true) {
            String s;
            try {
                s = inputInviter(MESSAGE_CHOSE_OPERATION);
                if (s.equals(OPERATION_ADD)) {
                    String key = inputInviter(INPUT_KEY_MESSAGE);
                    String value = inputInviter(INPUT_VALUE_MESSAGE);
                    if (service.addRow(key, value)) {
                        System.out.printf(MESSAGE_ADD, key, value);
                    } else {
                        System.out.println(MESSAGE_INVALID_INPUT);
                    }
                    System.out.println();
                } else if (s.equals(OPERATION_SHOW_ALL)) {
                    System.out.println(service.showAllRows());
                } else if (s.equals(OPERATION_SEARCH)) {
                    String key = inputInviter(INPUT_KEY_MESSAGE);
                    Optional<String> output = service.searchRow(key);
                    if (output.isPresent()) {
                        System.out.printf(MESSAGE_ROW_EXIST, key, output.get() + System.lineSeparator());
                    } else {
                        System.out.printf(MESSAGE_ROW_NOT_EXIST, key + System.lineSeparator());
                    }

                } else if (s.equals(OPERATION_DELETE)) {
                    String key = inputInviter(INPUT_KEY_MESSAGE);
                    if (service.deleteRow(key)) {
                        System.out.println(MESSAGE_ROW_DELETED);
                    } else {
                        System.out.println(MESSAGE_ROW_NOT_DELETED);
                    }
                } else {
                    System.out.println(MESSAGE_INVALID_INPUT);
                }
            } catch (DictionaryNotFoundException e) {
                System.out.println(MESSAGE_ERROR + e.getMessage());
            }
        }
    }

    /**
     * Requesting user input via valid input type
     *
     * @param message show message in console
     * @return inputted in console String
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
