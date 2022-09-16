package ru.dictionary.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Language {
    private UUID languageId;
    private String languageName;
    private String languageRule;

}
