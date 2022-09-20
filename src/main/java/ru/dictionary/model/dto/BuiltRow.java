package ru.dictionary.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BuiltRow {
    private UUID rowUUID;
    private String key;
    private String value;
    private String nameLanguageOfKey;
    private String nameLanguageOfValue;
}