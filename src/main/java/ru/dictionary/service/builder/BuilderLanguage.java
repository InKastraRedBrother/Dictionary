package ru.dictionary.service.builder;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dictionary.model.Language;

@Service
@AllArgsConstructor
public class BuilderLanguage {
    private static final int LANGUAGE_ID_SERIAL_NUMBER = 0;
    private static final int LANGUAGE_NAME_SERIAL_NUMBER = 1;
    private static final int LANGUAGE_RULE_SERIAL_NUMBER = 2;

    public Language FromStringToEntity(String[] arrayStringOfRawLanguageRows) {

        Language language = new Language();

        language.setLanguageId(arrayStringOfRawLanguageRows[LANGUAGE_ID_SERIAL_NUMBER]);
        language.setLanguageName(arrayStringOfRawLanguageRows[LANGUAGE_NAME_SERIAL_NUMBER]);
        language.setLanguageRule(arrayStringOfRawLanguageRows[LANGUAGE_RULE_SERIAL_NUMBER]);

        return language;
    }

}
