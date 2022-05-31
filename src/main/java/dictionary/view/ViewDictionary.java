package dictionary.view;

import dictionary.controller.DictionaryController;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;


public class ViewDictionary {
    DictionaryController dictionaryController;

    private static final String INPUT_KEY_MESSAGE = "Input key";
    private static final String INPUT_VALUE_MESSAGE = "Input value";

    private static final  String SYMBOLIC_DICTIONARY_INPUT = "1";
    private static final  String NUMERIC_DICTIONARY_INPUT = "2";
    private static final  String MESSAGE_CHOSE_DICTIONARY = "Chose Dictionary:" + SYMBOLIC_DICTIONARY_INPUT + " - Symbolic; " + NUMERIC_DICTIONARY_INPUT + " - Numeric ";

    private static final  String OPERATION_ADD= "1";
    private static final  String OPERATION_SHOW_ALL = "2";
    private static final  String OPERATION_SEARCH = "3";
    private static final  String OPERATION_DELETE = "4";
    private static final  String MESSAGE_CHOSE_OPERATION = "Chose operation: " + OPERATION_ADD + " - add;  " + OPERATION_SHOW_ALL + " - showAll; " + OPERATION_SEARCH + " - search; " + OPERATION_DELETE + " - delete; ";

    private static final  String VALID_OPTION_DICTIONARY_PATTERN = "^[1-2]{1}";

    private static final  String MESSAGE_INVALID_INPUT= "Invalid input. Try again";

    public ViewDictionary(DictionaryController dictionaryController) {
        this.dictionaryController = dictionaryController;
    }

    public void runApp() throws IOException {
        while (true) {
            String s = inputInviter(MESSAGE_CHOSE_DICTIONARY);
            if (s.matches(VALID_OPTION_DICTIONARY_PATTERN)) {
                System.out.println(dictionaryController.setDictionaryLanguage(s));
            } else {
                System.out.println(MESSAGE_INVALID_INPUT);
                continue;
            }
            s = inputInviter(MESSAGE_CHOSE_OPERATION);
            if (s.equals(OPERATION_ADD)) {

            } else if (s.equals(OPERATION_SHOW_ALL)) {

            } else if (s.equals(OPERATION_SEARCH)) {

            } else if (s.equals(OPERATION_DELETE)) {

            } else {
                System.out.println(MESSAGE_INVALID_INPUT);
            }
        }
    }

    public String inputInviter(String message){
        System.out.println(message);
        Console console = System.console();
        Scanner sc = new Scanner(System.in);
        if (console != null){
            return console.readLine();
        } else {
            return sc.nextLine();
        }
    }
}
