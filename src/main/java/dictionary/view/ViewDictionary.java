package dictionary.view;

import dictionary.service.Service;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private static final String MESSAGE_ROW_NOT_EXIST = "Didn't find row";
    private static final String MESSAGE_ROW_DELETED = "Row with key WAS deleted";
    private static final String MESSAGE_ROW_NOT_DELETED = "Row with key WAS NOT deleted";
    private static final String MESSAGE_ERROR = "Something bad happened";

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
            s = inputInviter(MESSAGE_CHOSE_OPERATION);
            if (s.equals(OPERATION_ADD)) {
                String key = inputInviter(INPUT_KEY_MESSAGE);
                String value = inputInviter(INPUT_VALUE_MESSAGE);
                try {
                    if (service.addRow(key, value)) {
                        System.out.println(key + ":" + value + " added");
                    } else {
                        System.out.println(MESSAGE_INVALID_INPUT);
                    }
                } catch (IOException e) {
                    System.out.println(MESSAGE_ERROR + e.getMessage());
                }
                System.out.println();
            } else if (s.equals(OPERATION_SHOW_ALL)) {
                try {
                    System.out.println(service.showAllRows());
                } catch (IOException e) {
                    System.out.println(MESSAGE_ERROR + e.getMessage());
                }
            } else if (s.equals(OPERATION_SEARCH)) {
                String key = inputInviter(INPUT_KEY_MESSAGE);
                try {
                    if (service.searchRow(key) != null) {
                        System.out.println(service.searchRow(key));
                    } else {
                        System.out.println(MESSAGE_ROW_NOT_EXIST);
                    }
                } catch(IOException e) {
                    System.out.println(MESSAGE_ERROR + e.getMessage());
                }

            } else if (s.equals(OPERATION_DELETE)) {
                String key = inputInviter(INPUT_KEY_MESSAGE);
                try {
                    if (service.deleteRow(key)) {
                        System.out.println(MESSAGE_ROW_DELETED);
                    } else {
                        System.out.println(MESSAGE_ROW_NOT_DELETED);
                    }
                } catch (Exception e) {
                    System.out.println(MESSAGE_ERROR + e.getMessage());
                }
            } else {
                System.out.println(MESSAGE_INVALID_INPUT);
            }
        }
    }

    /**
     * Chooses input type
     *
     * @param message show message in console
     * @return inputted String
     */
    public String inputInviter(String message) {
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
