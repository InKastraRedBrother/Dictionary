package ru.dictionary.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestAddPairWordsDTO {
    private UUID languageOfKeyUUID;
    private UUID languageOfTranslationUUID;
    private String wordKey;
    private String wordTranslation;
}
