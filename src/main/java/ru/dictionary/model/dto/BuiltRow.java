package ru.dictionary.model.dto;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class BuiltRow {
    private UUID rowUUID;
    private String key;
    private UUID keyUUID;
    private String value;
    private UUID valueUUID;
    private String nameLanguageOfKey;
    private String nameLanguageOfValue;
}