package ru.dictionary.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestAddPairWordsDTO {
    private UUID languageOfKeyUUID;
    private UUID languageOfValueUUID;
    private String wordKey;
    private String wordTranslation;
}
