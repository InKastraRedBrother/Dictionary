package Dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * Creating, fill and return map
 */
public class DictionaryInitialization {
    /** Input key mask for Symbolic Dictionary
     */
    private static final String PATTERN_SYM = "^[a-z]{4}+$";
    /** Input key mask for Numeric Dictionary
     */
    private static final String PATTERN_NUM = "^[0-9]{5}+$";
    /** Filename of Symbolic Dictionary
     */
    public static final String FILE_SYM = "sym";
    /** Filename of Numeric Dictionary
     */
    public static final String FILE_NUM = "num";
    IOStream ioStream;
    CommunicationWithConsole communicationWithConsole;
    Map<String, Dictionary> hashMap;

    public DictionaryInitialization(CommunicationWithConsole communicationWithConsole, IOStream ioStream) {
        this.ioStream = ioStream;
        this.communicationWithConsole = communicationWithConsole;
        createDictionaries();
    }

    /**
     * Create entry in hashMap with specific parameters
     */
    private void createDictionaries(){
        hashMap = new HashMap<>();
        hashMap.put("1", new Dictionary(FILE_SYM, PATTERN_SYM, ioStream, communicationWithConsole));
        hashMap.put("2", new Dictionary(FILE_NUM, PATTERN_NUM, ioStream, communicationWithConsole));
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
