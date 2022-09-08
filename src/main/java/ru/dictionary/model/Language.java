package ru.dictionary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
public class Language {
    private String languageId;
    private String languageName;
    private String languageRule;



//    public Language(String languageName, String languageRule) {  ///TODO А НАДО ЛИ. делаю это в languageService
//        this.languageId = UUID.randomUUID().toString();
//        this.languageName = languageName;
//        this.languageRule = languageRule;
//    }
//
//    public Language(String languageId, String languageName, String languageRule) {
//        this.languageId = languageId;
//        this.languageName = languageName;
//        this.languageRule = languageRule;
//    }
}
