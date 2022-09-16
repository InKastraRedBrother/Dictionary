package ru.dictionary.model;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class Word {
    private UUID wordId;
    private String wordValue;
    private UUID languageId;
}
