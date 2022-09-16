package ru.dictionary.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestAddPairWordsDTO {
    private UUID languageSourceId;
    private UUID languageTargetId;
    private String wordKey;
    private String wordValue;
}
