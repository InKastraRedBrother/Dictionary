package ru.dictionary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model of row in application
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Row {
    private static final String KEY_VALUE_SEPARATOR = " ";
    private String key;
    private String value;

    @Override
    public String toString() {
        return this.key + KEY_VALUE_SEPARATOR + this.value;
    }
}
