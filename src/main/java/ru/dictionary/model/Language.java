package ru.dictionary.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Language {
    private UUID languageUUID;
    private String languageName;
    private String languageRule;
}
