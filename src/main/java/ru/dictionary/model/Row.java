package ru.dictionary.model;

/**
 * Model of row in application
 */
public class Row {
    private static final String KEY_VALUE_SEPARATOR = " ";
    private String key;
    private String value;

    public Row(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Row() {
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.key + KEY_VALUE_SEPARATOR + this.value;
    }
}
