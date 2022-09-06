package ru.dictionary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Language {
    private long language_id;
    private String language_name;
    private String language_rule;
}
