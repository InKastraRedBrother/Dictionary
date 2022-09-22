package ru.dictionary.exception;

/**
 * Unchecked exception. Will be thrown when program somehow cannot reach ru.dictionary storage.
 */
public class DictionaryNotFoundException extends RuntimeException {

    public DictionaryNotFoundException() {
        super("Problem with ru.dictionary! Cannot proceed operation.");
    }

    public DictionaryNotFoundException(String message) {
        super(message);
    }
}
