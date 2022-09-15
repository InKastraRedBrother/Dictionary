package ru.dictionary.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Word {
    private String wordId;
    private String wordValue;
    private String languageId;
}
