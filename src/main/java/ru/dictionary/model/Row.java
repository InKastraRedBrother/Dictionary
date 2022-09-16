package ru.dictionary.model;

import lombok.Data;

import java.util.UUID;

/**
 * Model of row in application
 */
@Data
public class Row {

    private UUID idRow;
    private UUID idWordKey;
    private UUID idWordValue;

}
