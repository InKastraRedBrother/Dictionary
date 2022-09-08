package ru.dictionary.model;

import lombok.Data;

@Data
public class Word {
    private String wordId;
    private String wordValue;
    private String languageId;
}
