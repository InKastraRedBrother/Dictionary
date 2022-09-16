package ru.dictionary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dictionary.config.DictionaryParameters;
import ru.dictionary.dao.InterfaceDAOWord;
import ru.dictionary.model.Language;
import ru.dictionary.model.Row;
import ru.dictionary.model.Word;
import ru.dictionary.model.dto.BuiltRow;
import ru.dictionary.model.dto.RequestAddPairWordsDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Establishes a set of available operations and coordinates the application's response in each operation.
 */
@Component
@AllArgsConstructor
public class ServiceRow {

    InterfaceDAOWord dao;
    ServiceWord serviceWord;
    ServiceLanguage serviceLanguage;


    public List<BuiltRow> findAllRows() {

        List<Row> listWithRawRows = dao.findAll();

        List<BuiltRow> builtRowList = new ArrayList<>();

        for (Row listWithRawRow : listWithRawRows) {

            BuiltRow builtRow = new BuiltRow();

            Word wordKey = serviceWord.getWordById(listWithRawRow.getWordKeyUUID());
            Word wordValue = serviceWord.getWordById(listWithRawRow.getWordValueUUID());
            builtRow.setKey(wordKey.getWordValue());
            builtRow.setValue(wordValue.getWordValue());
            Language languageKeyWord = serviceLanguage.getLanguageById(wordKey.getWordLanguageUUID());
            Language languageValueWord = serviceLanguage.getLanguageById(wordValue.getWordLanguageUUID());
            builtRow.setNameLanguageOfKey(languageKeyWord.getLanguageName());
            builtRow.setNameLanguageOfValue(languageValueWord.getLanguageName());

            builtRowList.add(builtRow);
        }
        return builtRowList;
    }

    public List<BuiltRow> findAllBySelectedLanguageId(UUID languageSourceId, UUID languageTargetId) {


        List<Word> listWords = serviceWord.getListByLanguageUUID(languageSourceId);
        List<Row> listRows = dao.findAll();
        List<BuiltRow> listBuiltRow = new ArrayList<>();
        for (Word word : listWords) {
            BuiltRow builtRow = new BuiltRow();
            UUID keyWord = word.getWordUUID();
            for (Row row : listRows) {
                if (row.getWordKeyUUID().equals(keyWord)) {
                    builtRow.setNameLanguageOfKey(serviceLanguage.getLanguageById(languageSourceId).getLanguageName());
                    builtRow.setKey(word.getWordValue());
                    Word wordTemp = serviceWord.getWordById(row.getWordValueUUID());
                    builtRow.setValue(wordTemp.getWordValue());
                    builtRow.setNameLanguageOfValue(serviceWord.getLanguageByWordId(wordTemp.getWordLanguageUUID()).getLanguageName());
                    listBuiltRow.add(builtRow);
                }
            }
        }
        return listBuiltRow;
    }

    public void addPair(RequestAddPairWordsDTO requestAddPairWordsDTO) {

        UUID uuidWordKey = UUID.randomUUID();
        UUID uuidWordValue = UUID.randomUUID();

        serviceWord.addWord(uuidWordKey, requestAddPairWordsDTO.getWordKey(), requestAddPairWordsDTO.getLanguageSourceId());
        serviceWord.addWord(uuidWordValue, requestAddPairWordsDTO.getWordValue(), requestAddPairWordsDTO.getLanguageTargetId());

        Row row = new Row();
        row.setRowUUID(UUID.randomUUID());
        row.setWordKeyUUID(uuidWordKey);
        row.setWordValueUUID(uuidWordValue);

        dao.save(row);
    }

    public boolean deleteRowByKey(String key, DictionaryParameters dictionaryParameters) {
        return dao.deleteByKey(key, dictionaryParameters.getFileName());
    }

    public Optional<Row> findRowByKey(String key, DictionaryParameters dictionaryParameters) {
        return dao.findByKey(key, dictionaryParameters.getFileName());
    }
}