package ru.dictionary.config;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creating, fill and return map
 */
@Component
public class DictionaryConfiguration {

    private static final String PATTERN_SYM = "^[a-z]{4}+$";
    private static final String PATTERN_NUM = "^[0-9]{5}+$";
    private static final String FILE_SYM = "sym.txt";
    private static final String FILE_NUM = "num.txt";
    private static final String FIRST_DICTIONARY = "1";
    private static final String SECOND_DICTIONARY = "2";

    Map<String, DictionaryParameters> hashMap;

    public DictionaryConfiguration() {
        createDictionaries();
    }

    /**
     * Create entry in hashMap with specific parameters
     */
    private void createDictionaries() {
        hashMap = new HashMap<>();
        hashMap.put(FIRST_DICTIONARY, new DictionaryParameters(FILE_SYM, PATTERN_SYM));
        hashMap.put(SECOND_DICTIONARY, new DictionaryParameters(FILE_NUM, PATTERN_NUM));
    }

    /**
     * Chose dictionary
     *
     * @param inputDictionarySelection get String by that pre-initialized ru.dictionary will be chosen
     * @return chosen dictionary
     */
    public DictionaryParameters getSelectedDictionary(String inputDictionarySelection) {
        return hashMap.get(inputDictionarySelection);
    }
}
