package dictionary.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Creating, fill and return map
 */
public class DictionaryConfiguration {

    private static final String PATTERN_SYM = "^[a-z]{4}+$";
    private static final String PATTERN_NUM = "^[0-9]{5}+$";
    public static final String FILE_SYM = "sym.txt";
    public static final String FILE_NUM = "num.txt";
    public static final String FIRST_DICTIONARY = "1";
    public static final String SECOND_DICTIONARY = "2";

    Map<String, DictionaryInitialization> hashMap;

    public DictionaryConfiguration() {
        createDictionaries();
    }

    /**
     * Create entry in hashMap with specific parameters
     */
    private void createDictionaries() {
        hashMap = new HashMap<>();
        hashMap.put(FIRST_DICTIONARY, new DictionaryInitialization(FILE_SYM, PATTERN_SYM));
        hashMap.put(SECOND_DICTIONARY, new DictionaryInitialization(FILE_NUM, PATTERN_NUM));
    }

    /**
     * @param s get String 1 or 2 by that pre-initialized dictionary will be chosen
     * @return chosen Map
     */
    public DictionaryInitialization getS(String s) {
        return hashMap.get(s);
    }
}
