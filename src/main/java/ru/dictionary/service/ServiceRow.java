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

            Word wordKey = serviceWord.getWordByUUID(listWithRawRow.getWordKeyUUID());
            Word wordValue = serviceWord.getWordByUUID(listWithRawRow.getWordValueUUID());
            builtRow.setKey(wordKey.getWordValue());
            builtRow.setValue(wordValue.getWordValue());
            Language languageKeyWord = serviceLanguage.getLanguageByUUID(wordKey.getWordLanguageUUID());
            Language languageValueWord = serviceLanguage.getLanguageByUUID(wordValue.getWordLanguageUUID());
            builtRow.setNameLanguageOfKey(languageKeyWord.getLanguageName());
            builtRow.setNameLanguageOfValue(languageValueWord.getLanguageName());

            builtRowList.add(builtRow);
        }
        return builtRowList;
    }

    public List<BuiltRow> findAllBySelectedLanguageUUID(UUID languageSourceUUID, UUID languageTargetUUID) {


        List<Word> listWords = serviceWord.getListByLanguageUUID(languageSourceUUID);
        List<Row> listRows = dao.findAll();
        List<BuiltRow> listBuiltRow = new ArrayList<>();
        for (Word word : listWords) {
            BuiltRow builtRow = new BuiltRow();
            UUID keyWord = word.getWordUUID();
            for (Row row : listRows) {
                if (row.getWordKeyUUID().equals(keyWord)) {
                    builtRow.setNameLanguageOfKey(serviceLanguage.getLanguageByUUID(languageSourceUUID).getLanguageName());
                    builtRow.setKey(word.getWordValue());
                    Word wordTemp = serviceWord.getWordByUUID(row.getWordValueUUID());
                    builtRow.setValue(wordTemp.getWordValue());
                    builtRow.setNameLanguageOfValue(serviceWord.getLanguageByWordUUID(wordTemp.getWordLanguageUUID()).getLanguageName());
                    listBuiltRow.add(builtRow);
                }
            }
        }
        return listBuiltRow;
    }

    public void addPair(RequestAddPairWordsDTO requestAddPairWordsDTO) {

        UUID wordKeyUUID = UUID.randomUUID();
        UUID wordValueUUID = UUID.randomUUID();

        serviceWord.addWord(wordKeyUUID, requestAddPairWordsDTO.getWordKey(), requestAddPairWordsDTO.getLanguageSourceUUID());
        serviceWord.addWord(wordValueUUID, requestAddPairWordsDTO.getWordValue(), requestAddPairWordsDTO.getLanguageTargetUUID());

        Row row = new Row();
        row.setRowUUID(UUID.randomUUID());
        row.setWordKeyUUID(wordKeyUUID);
        row.setWordValueUUID(wordValueUUID);

        dao.save(row);
    }

    public boolean deleteRowByKey(String key, DictionaryParameters dictionaryParameters) {
        return dao.deleteByKey(key, dictionaryParameters.getFileName());
    }

    public Optional<Row> findRowByKey(String key, DictionaryParameters dictionaryParameters) {
        return dao.findByKey(key, dictionaryParameters.getFileName());
    }
}