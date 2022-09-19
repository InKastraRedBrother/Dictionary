package ru.dictionary.model;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class Language {
    private UUID languageUUID;
    private String languageName;
    private String languageRule;

}
