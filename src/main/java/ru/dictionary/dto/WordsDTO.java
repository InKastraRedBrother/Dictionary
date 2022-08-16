package ru.dictionary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.dictionary.model.Row;

import java.util.List;

@Data
@AllArgsConstructor
public class WordsDTO {
    private String id;
    private List<Row> listRows;

}
