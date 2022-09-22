package ru.dictionary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dictionary.dao.InterfaceDAOWord;
import ru.dictionary.model.Language;
import ru.dictionary.model.Row;
import ru.dictionary.model.Word;
import ru.dictionary.model.dto.BuiltRow;
import ru.dictionary.model.dto.RequestAddPairWordsDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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

        List<Row> listRow = dao.findAll();
        List<Language> listLanguage = serviceLanguage.findAllLanguages();
        List<Word> listWord = serviceWord.findAllWords();

        Map<UUID, Language> hashMapLanguage = listLanguage.stream().collect(Collectors.toMap(Language::getLanguageUUID, language -> language));
        Map<UUID, Word> hashMapWord = listWord.stream().collect(Collectors.toMap(Word::getWordUUID, word -> word));

        List<BuiltRow> builtRowList = new ArrayList<>();

        for (Row row : listRow) {
            BuiltRow builtRow = new BuiltRow();

            Word wordKey = hashMapWord.get(row.getWordKeyUUID());
            Word wordValue = hashMapWord.get(row.getWordValueUUID());

            Language languageKey = hashMapLanguage.get(wordKey.getWordLanguageUUID());
            Language languageValue = hashMapLanguage.get(wordValue.getWordLanguageUUID());

            builtRow.setRowUUID(row.getRowUUID());

            builtRow.setKey(wordKey.getWordValue());
            builtRow.setKeyUUID(wordKey.getWordUUID());
            builtRow.setValue(wordValue.getWordValue());
            builtRow.setValueUUID(wordKey.getWordUUID());
            builtRow.setNameLanguageOfKey(languageKey.getLanguageName());
            builtRow.setNameLanguageOfValue(languageValue.getLanguageName());

            builtRowList.add(builtRow);
        }
        return builtRowList;
    }


    public List<BuiltRow> findAllBySelectedLanguageUUID(UUID languageSourceUUID, UUID languageTargetUUID) {

        List<Word> listWords = serviceWord.getListByLanguageUUID(languageSourceUUID);
        List<Row> listRows = dao.findAll();
        List<BuiltRow> builtRowList = new ArrayList<>();
        for (Word word : listWords) {
            BuiltRow builtRow = new BuiltRow();
            UUID keyWord = word.getWordUUID();
            for (Row row : listRows) {
                builtRow.setRowUUID(row.getRowUUID());
                if (row.getWordKeyUUID().equals(keyWord)) {
                    builtRow.setNameLanguageOfKey(serviceLanguage.getLanguageByUUID(languageSourceUUID).getLanguageName());
                    builtRow.setKey(word.getWordValue());
                    Word wordTemp = serviceWord.getWordByUUID(row.getWordValueUUID());
                    builtRow.setValue(wordTemp.getWordValue());
                    builtRow.setNameLanguageOfValue(serviceWord.getLanguageByWordUUID(languageTargetUUID).getLanguageName());
                    builtRowList.add(builtRow);
                }
            }
        }
        return builtRowList;
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

    public boolean deleteRowByKey(UUID uuid) {
        Row row = dao.findById(uuid);
        serviceWord.deleteWordByUUID(row.getWordKeyUUID());
        serviceWord.deleteWordByUUID(row.getWordValueUUID());

        return dao.deleteByKey(uuid);
    }

    public List<BuiltRow> findRowByWordValue(String wordValueFromView) {

        List<Row> listRow = dao.findAll();
        List<Language> listLanguage = serviceLanguage.findAllLanguages();
        List<Word> listWord = serviceWord.getListWordsByWordValue(wordValueFromView);

        Map<UUID, Language> hashMapLanguage = listLanguage.stream().collect(Collectors.toMap(Language::getLanguageUUID, language -> language));
        Map<UUID, Word> hashMapWord = listWord.stream().collect(Collectors.toMap(Word::getWordUUID, word -> word));
        List<Row> cleanListRow = new ArrayList<>();
        for (Row row : listRow) {
            for (Word word : listWord) {
                if ( row.getWordKeyUUID().equals(word.getWordUUID()) || row.getWordValueUUID().equals(word.getWordUUID())){
                    cleanListRow.add(row);
                }
            }

        }
        List<BuiltRow> builtRowList = new ArrayList<>();

        for (Row row : cleanListRow) {
            BuiltRow builtRow = new BuiltRow();
            Word wordKey;
            Word wordValue;
            if (hashMapWord.get(row.getWordKeyUUID()) != null) {
                wordKey = hashMapWord.get(row.getWordKeyUUID());
                wordValue = serviceWord.getWordByUUID(row.getWordValueUUID());
            } else {
                wordKey = serviceWord.getWordByUUID(row.getWordKeyUUID());
                wordValue = hashMapWord.get(row.getWordValueUUID());
            }

            Language languageKey = hashMapLanguage.get(wordKey.getWordLanguageUUID());
            Language languageValue = hashMapLanguage.get(wordValue.getWordLanguageUUID());

            builtRow.setRowUUID(row.getRowUUID());

            builtRow.setKey(wordKey.getWordValue());
            builtRow.setKeyUUID(wordKey.getWordUUID());
            builtRow.setValue(wordValue.getWordValue());
            builtRow.setValueUUID(wordKey.getWordUUID());
            builtRow.setNameLanguageOfKey(languageKey.getLanguageName());
            builtRow.setNameLanguageOfValue(languageValue.getLanguageName());

            builtRowList.add(builtRow);
        }
        return builtRowList;
    }
}