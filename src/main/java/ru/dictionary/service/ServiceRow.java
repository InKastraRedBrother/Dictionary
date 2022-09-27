package ru.dictionary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dictionary.dao.InterfaceRowDAO;
import ru.dictionary.model.Language;
import ru.dictionary.model.Row;
import ru.dictionary.model.SuccessMessage;
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
    private static final int SINGLE_ENTRY = 1;

    InterfaceRowDAO rowDAO;
    ServiceWord serviceWord;
    ServiceLanguage serviceLanguage;
    SuccessMessage successMessage;

    public List<BuiltRow> findAllRows() {

        List<Row> listRow = rowDAO.findAll();
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
            builtRow.setValue(wordValue.getWordValue());
            builtRow.setNameLanguageOfKey(languageKey.getLanguageName());
            builtRow.setNameLanguageOfValue(languageValue.getLanguageName());

            builtRowList.add(builtRow);
        }
        return builtRowList;
    }

    public List<BuiltRow> findAllBySelectedLanguageUUID(String languageSourceUUID, String languageTargetUUID) {
        UUID languageSourceUUIDFromString = null;
        UUID languageTargetUUIDFromString = null;
        if (!languageSourceUUID.equals("")) {
            languageSourceUUIDFromString = UUID.fromString(languageSourceUUID);
        }
        if (!languageTargetUUID.equals("")) {
            languageTargetUUIDFromString = UUID.fromString(languageTargetUUID);
        }

        List<Word> listWords = serviceWord.getListByLanguageUUID(languageSourceUUIDFromString);
        List<Row> listRows = rowDAO.findAll();
        List<BuiltRow> builtRowList = new ArrayList<>();
        for (Word word : listWords) {
            BuiltRow builtRow = new BuiltRow();
            UUID keyWord = word.getWordUUID();
            for (Row row : listRows) {
                builtRow.setRowUUID(row.getRowUUID());
                if (row.getWordKeyUUID().equals(keyWord)) {
                    builtRow.setNameLanguageOfKey(serviceLanguage.getLanguageByUUID(languageSourceUUIDFromString).getLanguageName());
                    builtRow.setKey(word.getWordValue());
                    Word wordTemp = serviceWord.getWordByUUID(row.getWordValueUUID());
                    builtRow.setValue(wordTemp.getWordValue());
                    builtRow.setNameLanguageOfValue(serviceWord.getLanguageByWordUUID(languageTargetUUIDFromString).getLanguageName());
                    builtRowList.add(builtRow);
                }
            }
        }
        return builtRowList;
    }

    public SuccessMessage addPair(RequestAddPairWordsDTO requestAddPairWordsDTO) {

        Language languageSource = serviceLanguage.getLanguageByUUID(requestAddPairWordsDTO.getLanguageSourceUUID());
        Language languageTarget = serviceLanguage.getLanguageByUUID(requestAddPairWordsDTO.getLanguageTargetUUID());

        Word wordKeyFromStorage = serviceWord.findWordByWordsValue(requestAddPairWordsDTO.getWordKey());
        Word wordValueFromStorage = serviceWord.findWordByWordsValue(requestAddPairWordsDTO.getWordValue());


        if (requestAddPairWordsDTO.getWordKey().matches(languageSource.getLanguageRule()) && requestAddPairWordsDTO.getWordValue().matches(languageTarget.getLanguageRule())) {

            Row row = new Row();
            UUID wordKeyUUID = UUID.randomUUID();
            UUID wordValueUUID = UUID.randomUUID();

            if (wordKeyFromStorage == null && wordValueFromStorage == null) {
                serviceWord.addWord(wordKeyUUID, requestAddPairWordsDTO.getWordKey(), requestAddPairWordsDTO.getLanguageSourceUUID());
                serviceWord.addWord(wordValueUUID, requestAddPairWordsDTO.getWordValue(), requestAddPairWordsDTO.getLanguageTargetUUID());
            } else if (wordKeyFromStorage != null && wordValueFromStorage == null) {
                wordKeyUUID = wordKeyFromStorage.getWordUUID();
                serviceWord.addWord(wordValueUUID, requestAddPairWordsDTO.getWordValue(), requestAddPairWordsDTO.getLanguageTargetUUID());
            } else if (wordKeyFromStorage == null && wordValueFromStorage != null) {
                serviceWord.addWord(wordKeyUUID, requestAddPairWordsDTO.getWordKey(), requestAddPairWordsDTO.getLanguageSourceUUID());
                wordValueUUID = wordValueFromStorage.getWordUUID();
            } else if (wordKeyFromStorage != null && wordValueFromStorage != null) {
                wordKeyUUID = wordKeyFromStorage.getWordUUID();
                wordValueUUID = wordValueFromStorage.getWordUUID();
            }
            if (rowDAO.findRowByKeyAndValue(wordKeyFromStorage.getWordUUID(), wordValueFromStorage.getWordUUID()) == null) {

                row.setRowUUID(UUID.randomUUID());
                row.setWordKeyUUID(wordKeyUUID);
                row.setWordValueUUID(wordValueUUID);

                rowDAO.save(row);
            } else {
                successMessage.setMessage("Row already exists");
                successMessage.setSuccessful(false);
                return successMessage;
            }
            successMessage.setMessage("Pair have been added" + requestAddPairWordsDTO.getWordKey() + " : " + requestAddPairWordsDTO.getWordValue());
            successMessage.setSuccessful(true);

        } else {
            successMessage.setMessage("Pattern mismatch");
            successMessage.setSuccessful(false);
        }
        return successMessage;
    }

    public boolean deleteRowById(String uuid) {
        UUID uuidForDelete = UUID.fromString(uuid);
        Row row = rowDAO.findById(uuidForDelete);
        if (rowDAO.findListRowByWordUUID(row.getWordKeyUUID()).size() <= SINGLE_ENTRY) {
            serviceWord.deleteWordByUUID(row.getWordKeyUUID());
        }
        if (rowDAO.findListRowByWordUUID(row.getWordValueUUID()).size() <= SINGLE_ENTRY) {
            serviceWord.deleteWordByUUID(row.getWordValueUUID());
        }
        return rowDAO.deleteById(uuidForDelete);
    }

    public List<BuiltRow> findRowsByWordValue(String wordValueFromView) {

        List<Word> listWord = serviceWord.findListWordsByWordsValue(wordValueFromView);//TODO КОСЯК, ЛИСТ ВОРДОВ И ЛИСТ ЗНАЧЕНИЙ
        List<Row> listRow = rowDAO.findAllByListWords(listWord);
        List<Language> listLanguage = serviceLanguage.findAllLanguages();

        Map<UUID, Language> hashMapLanguage = listLanguage.stream().collect(Collectors.toMap(Language::getLanguageUUID, language -> language));
        Map<UUID, Word> hashMapWord = listWord.stream().collect(Collectors.toMap(Word::getWordUUID, word -> word));

        List<BuiltRow> builtRowList = new ArrayList<>();

        for (Row row : listRow) {
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
            builtRow.setValue(wordValue.getWordValue());
            builtRow.setNameLanguageOfKey(languageKey.getLanguageName());
            builtRow.setNameLanguageOfValue(languageValue.getLanguageName());

            builtRowList.add(builtRow);
        }
        return builtRowList;
    }
}