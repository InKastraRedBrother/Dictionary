package ru.dictionary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model of row in application
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Row {
    Word word;
    private long id_row;
    private long id_word_key;
    private long id_word_value;
    private long dictionary_id;

    public String getWordById(long id){
        return word.getWord_value();
    }

}
