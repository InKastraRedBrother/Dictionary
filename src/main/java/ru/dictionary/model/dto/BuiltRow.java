package ru.dictionary.model.dto;

import lombok.Data;

@Data
public class BuiltRow {
    private String key;
    private String value;
    private String nameLanguageOfKey;
    private String nameLanguageOfValue;
}