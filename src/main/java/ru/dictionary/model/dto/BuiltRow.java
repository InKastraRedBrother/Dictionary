package ru.dictionary.model.dto;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class BuiltRow {
    private UUID rowUUID;
    private String key;
    private String value;
    private String nameLanguageOfKey;
    private String nameLanguageOfValue;
}