package ru.dictionary.model;

import lombok.*;

/**
 * Model of row in application
 */
@Data
public class Row {

    private String idRow;
    private String idWordKey;
    private String idWordValue;

}
