package dictionary.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Declare, initialize and return map
 */
public class DictionaryInit {

    /**
     * Input key mask for Symbolic java.dictionary.model.Dictionary
     */
    private static final String PATTERN_SYM = "^[a-z]{4}+$";
    /**
     * Input key mask for Numeric java.dictionary.model.Dictionary
     */
    private static final String PATTERN_NUM = "^[0-9]{5}+$";
    /**
     * Filename of Symbolic java.dictionary.model.Dictionary
     */
    public static final String FILE_SYM = "sym.txt";
    /**
     * Filename of Numeric java.dictionary.model.Dictionary
     */
    public static final String FILE_NUM = "num.txt";

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
        hashMap.put("1", new ArrayList<>(
                List.of(PATTERN_SYM, FILE_SYM)));
        hashMap.put("2", new ArrayList<>(
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
