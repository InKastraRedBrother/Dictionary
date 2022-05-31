package dictionary.controller;

import dictionary.model.Dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * Declare, initialize and return map
 */
public class DictionaryInitialization {
    /** Input key mask for Symbolic java.dictionary.model.Dictionary
     */
    private static final String PATTERN_SYM = "^[a-z]{4}+$";
    /** Input key mask for Numeric java.dictionary.model.Dictionary
     */
    private static final String PATTERN_NUM = "^[0-9]{5}+$";
    /** Filename of Symbolic java.dictionary.model.Dictionary
     */
    public static final String FILE_SYM = "sym";
    /** Filename of Numeric java.dictionary.model.Dictionary
     */
    public static final String FILE_NUM = "num";

    IOStream ioStream;
    Map<String, Dictionary> hashMap;

    /**
     * Constructor for DI and creation dictionary's text file
     *
     * @param ioStream DI
     */
    public DictionaryInitialization(IOStream ioStream) {
        this.ioStream = ioStream;
        createDictionaries();
    }

    /**
     * Create entry in hashMap with specific parameters
     */
    private void createDictionaries(){
        hashMap = new HashMap<>();
        hashMap.put("1", new Dictionary(FILE_SYM, PATTERN_SYM, ioStream));
        hashMap.put("2", new Dictionary(FILE_NUM, PATTERN_NUM, ioStream));
    }

    /**
     *
     * @param s get String 1 or 2 by that pre-initialized dictionary will be chosen
     * @return chosen Map
     */
    public Dictionary getS(String s){
        return hashMap.get(s);
    }
}
