package dictionary.model;

/**
 * Model of row in application
 */
public class Row {
    private static final String KEY_VALUE_SEPARATOR = " ";
    String key;
    String value;

    public Row(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.key + KEY_VALUE_SEPARATOR + this.value;
    }
}
