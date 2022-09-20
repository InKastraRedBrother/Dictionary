package ru.dictionary.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestAddPairWordsDTO {
    private UUID languageSourceUUID;
    private UUID languageTargetUUID;
    private String wordKey;
    private String wordValue;
}
