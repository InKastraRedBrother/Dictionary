package dictionary.exception;

public class SomeKindOfError extends RuntimeException{
        public SomeKindOfError() {
            super("Problem with dictionary!");
        }
}
