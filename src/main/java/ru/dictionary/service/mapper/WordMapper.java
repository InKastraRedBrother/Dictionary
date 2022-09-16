package ru.dictionary.service.mapper;

import org.springframework.stereotype.Component;
import ru.dictionary.model.Word;
import ru.dictionary.model.dto.RequestAddPairWordsDTO;

import java.util.UUID;

@Component
public class WordMapper {

    public Word fromDTOToEntity(RequestAddPairWordsDTO requestAddPairWordsDTO) {
        Word word = new Word();
        word.setWordId(UUID.randomUUID());
        word.setWordValue(requestAddPairWordsDTO.getWordKey());
        word.setLanguageId(requestAddPairWordsDTO.getLanguageSourceId());
        return word;
    }

}

//    String key = requestAddPairWordsDTO.getWordKey();
//    String languageSourceId = requestAddPairWordsDTO.getLanguageSourceId();
//
//    Word wordKey = new Word();
//        wordKey.setWord_id(UUID.randomUUID().toString());
//                wordKey.setWord_value(key);
//                wordKey.setLanguage_id(languageSourceId);
//
//
//                String value = requestAddPairWordsDTO.getWordValue();
//                String targetSourceId = requestAddPairWordsDTO.getLanguageTargetId();
//
//                Word wordValue = new Word();
//                wordValue.setWord_id(UUID.randomUUID().toString());
//                wordKey.setWord_value(value);
//                wordKey.setLanguage_id(targetSourceId);

