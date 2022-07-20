package dictionary.exception;

/**
 * Unchecked exception. Will be thrown when program somehow cannot reach dictionary storage.
 */
public class DictionaryNotFoundException extends RuntimeException {
    public DictionaryNotFoundException() {
        super("Problem with dictionary! Cannot proceed operation.");
    }
}
