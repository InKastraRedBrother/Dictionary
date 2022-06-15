package dictionary.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Declare, initialize and return map
 */
public class DictionaryInit {

    private static final String PATTERN_SYM = "^[a-z]{4}+$";
    private static final String PATTERN_NUM = "^[0-9]{5}+$";
    public static final String FILE_SYM = "sym.txt";
    public static final String FILE_NUM = "num.txt";
    public static final String FIRST_DICTIONARY = "1";
    public static final String SECOND_DICTIONARY = "2";

    Map<String, ArrayList<String>> hashMap;

    /**
     * Constructor for add entries in hashMap
     */
    public DictionaryInit() {
        createDictionaries();
    }

    /**
     * Create entries in hashMap with specific parameters
     */
    private void createDictionaries() {
        hashMap = new HashMap<>();
        hashMap.put(FIRST_DICTIONARY, new ArrayList<>(
                List.of(PATTERN_SYM, FILE_SYM)));
        hashMap.put(SECOND_DICTIONARY, new ArrayList<>(
                List.of(PATTERN_NUM, FILE_NUM)));
    }

    /** Get chosen entry from map
     * @param s get String 1 or 2 by that pre-initialized dictionary will be chosen
     * @return chosen Map
     */
    public ArrayList<String> getEntry(String s) {
        return hashMap.get(s);
    }
}
