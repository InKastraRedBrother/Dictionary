package ru.dictionary.model;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

/**
 * Model of row in application
 */
@Data
@ToString
class Row {
    private UUID rowUUID;
    private UUID wordKeyUUID;
    private UUID wordValueUUID;
}
