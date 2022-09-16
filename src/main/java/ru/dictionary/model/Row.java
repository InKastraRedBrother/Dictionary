package ru.dictionary.model;

import lombok.Data;

import java.util.UUID;

/**
 * Model of row in application
 */
@Data
public class Row {
    private UUID rowUUID;
    private UUID wordKeyUUID;
    private UUID wordValueUUID;
}
