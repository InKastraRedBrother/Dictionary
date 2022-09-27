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
        List<Language> listLanguage = serviceLanguage.getAllLanguages();
        List<Word> listWord = serviceWord.findAllWords();

        Map<UUID, Language> hashMapLanguage = listLanguage.stream().collect(Collectors.toMap(Language::getLanguageUUID, language -> language));
        Map<UUID, Word> hashMapWord = listWord.stream().collect(Collectors.toMap(Word::getWordUUID, word -> word));

        List<BuiltRow> builtRowList = new ArrayList<>();

        for (Row row : listRow) {
            BuiltRow builtRow = new BuiltRow();

            Word wordKey = hashMapWord.get(row.getWordKeyUUID());
            Word wordValue = hashMapWord.get(row.getWordTranslationUUID());

            Language languageKey = hashMapLanguage.get(wordKey.getWordLanguageUUID());
            Language languageValue = hashMapLanguage.get(wordValue.getWordLanguageUUID());

            builtRow.setRowUUID(row.getRowUUID());

            builtRow.setWordKey(wordKey.getWordValue());
            builtRow.setWordTranslation(wordValue.getWordValue());
            builtRow.setNameLanguageOfKey(languageKey.getLanguageName());
            builtRow.setNameLanguageOfTranslation(languageValue.getLanguageName());

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
                    builtRow.setWordKey(word.getWordValue());
                    Word wordTemp = serviceWord.getWordByUUID(row.getWordTranslationUUID());
                    builtRow.setWordTranslation(wordTemp.getWordValue());
                    builtRow.setNameLanguageOfTranslation(serviceWord.getLanguageByWordUUID(languageTargetUUIDFromString).getLanguageName());
                    builtRowList.add(builtRow);
                }
            }
        }
        return builtRowList;
    }

    public SuccessMessage addPair(RequestAddPairWordsDTO requestAddPairWordsDTO) {

        Language languageSource = serviceLanguage.getLanguageByUUID(requestAddPairWordsDTO.getLanguageOfKeyUUID());
        Language languageTarget = serviceLanguage.getLanguageByUUID(requestAddPairWordsDTO.getLanguageOfValueUUID());

        Word wordKeyFromStorage = serviceWord.findWordByWordsValue(requestAddPairWordsDTO.getWordKey());
        Word wordTranslationFromStorage = serviceWord.findWordByWordsValue(requestAddPairWordsDTO.getWordTranslation());


        if (requestAddPairWordsDTO.getWordKey().matches(languageSource.getLanguageRule()) && requestAddPairWordsDTO.getWordTranslation().matches(languageTarget.getLanguageRule())) {

            Row row = new Row();
            UUID wordKeyUUID = UUID.randomUUID();
            UUID wordTranslationUUID = UUID.randomUUID();

            if (wordKeyFromStorage == null && wordTranslationFromStorage == null) {
                serviceWord.addWord(wordKeyUUID, requestAddPairWordsDTO.getWordKey(), requestAddPairWordsDTO.getLanguageOfKeyUUID());
                serviceWord.addWord(wordTranslationUUID, requestAddPairWordsDTO.getWordTranslation(), requestAddPairWordsDTO.getLanguageOfValueUUID());
            } else if (wordKeyFromStorage != null && wordTranslationFromStorage == null) {
                wordKeyUUID = wordKeyFromStorage.getWordUUID();
                serviceWord.addWord(wordTranslationUUID, requestAddPairWordsDTO.getWordTranslation(), requestAddPairWordsDTO.getLanguageOfValueUUID());
            } else if (wordKeyFromStorage == null && wordTranslationFromStorage != null) {
                serviceWord.addWord(wordKeyUUID, requestAddPairWordsDTO.getWordKey(), requestAddPairWordsDTO.getLanguageOfKeyUUID());
                wordTranslationUUID = wordTranslationFromStorage.getWordUUID();
            } else if (wordKeyFromStorage != null && wordTranslationFromStorage != null) {
                wordKeyUUID = wordKeyFromStorage.getWordUUID();
                wordTranslationUUID = wordTranslationFromStorage.getWordUUID();
            }
            if (rowDAO.findRowByKeyAndValue(wordKeyFromStorage.getWordUUID(), wordTranslationFromStorage.getWordUUID()) == null) {

                row.setRowUUID(UUID.randomUUID());
                row.setWordKeyUUID(wordKeyUUID);
                row.setWordTranslationUUID(wordTranslationUUID);

                rowDAO.save(row);
            } else {
                successMessage.setMessage("Row already exists");
                successMessage.setSuccessful(false);
                return successMessage;
            }
            successMessage.setMessage("Pair have been added" + requestAddPairWordsDTO.getWordKey() + " : " + requestAddPairWordsDTO.getWordTranslation());
            successMessage.setSuccessful(true);

        } else {
            successMessage.setMessage("Pattern mismatch");
            successMessage.setSuccessful(false);
        }
        return successMessage;
    }

    public boolean deleteById(String uuid) {
        UUID uuidForDelete = UUID.fromString(uuid);
        Row row = rowDAO.findById(uuidForDelete);
        if (rowDAO.findListRowByWordUUID(row.getWordKeyUUID()).size() <= SINGLE_ENTRY) {
            serviceWord.deleteWordByUUID(row.getWordKeyUUID());
        }
        if (rowDAO.findListRowByWordUUID(row.getWordTranslationUUID()).size() <= SINGLE_ENTRY) {
            serviceWord.deleteWordByUUID(row.getWordTranslationUUID());
        }
        return rowDAO.deleteById(uuidForDelete);
    }

    public List<BuiltRow> findRowsByWordTranslation(String wordValueFromView) {

        List<Word> listWord = serviceWord.getListByWordValue(wordValueFromView);//TODO КОСЯК, ЛИСТ ВОРДОВ И ЛИСТ ЗНАЧЕНИЙ
        List<Row> listRow = rowDAO.findAllByListWords(listWord);
        List<Language> listLanguage = serviceLanguage.getAllLanguages();

        Map<UUID, Language> hashMapLanguage = listLanguage.stream().collect(Collectors.toMap(Language::getLanguageUUID, language -> language));
        Map<UUID, Word> hashMapWord = listWord.stream().collect(Collectors.toMap(Word::getWordUUID, word -> word));

        List<BuiltRow> builtRowList = new ArrayList<>();

        for (Row row : listRow) {
            BuiltRow builtRow = new BuiltRow();
            Word wordKey;
            Word wordValue;
            if (hashMapWord.get(row.getWordKeyUUID()) != null) {
                wordKey = hashMapWord.get(row.getWordKeyUUID());
                wordValue = serviceWord.getWordByUUID(row.getWordTranslationUUID());
            } else {
                wordKey = serviceWord.getWordByUUID(row.getWordKeyUUID());
                wordValue = hashMapWord.get(row.getWordTranslationUUID());
            }

            Language languageKey = hashMapLanguage.get(wordKey.getWordLanguageUUID());
            Language languageValue = hashMapLanguage.get(wordValue.getWordLanguageUUID());

            builtRow.setRowUUID(row.getRowUUID());

            builtRow.setWordKey(wordKey.getWordValue());
            builtRow.setWordTranslation(wordValue.getWordValue());
            builtRow.setNameLanguageOfKey(languageKey.getLanguageName());
            builtRow.setNameLanguageOfTranslation(languageValue.getLanguageName());

            builtRowList.add(builtRow);
        }
        return builtRowList;
    }
}