package ru.dictionary.model.dto;

import lombok.Data;

@Data
public class RequestAddPairWordsDTO {
    private String languageSourceId;
    private String languageTargetId;
    private String wordKey;
    private String wordValue;
}
