package Dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * Point of entry
 */
class Main {
    private static final String PATTERN_SYM = "^[a-z]{4}+$";
    private static final String PATTERN_NUM = "^[0-9]{5}+$";


    public static void main(String[] args) {
        CommunicationWithConsole communicationWithConsole = new CommunicationWithConsole();
        IOStream ioStream = new IOStream();
        Map<String, Dictionary>  hashMap = createDictionaries(ioStream);
        Start start = new Start(communicationWithConsole, hashMap);
        start.runApp();
    }

    public static Map<String, Dictionary> createDictionaries(IOStream ioStream){
        Map<String, Dictionary> hashMap = new HashMap<>();
        hashMap.put(CommunicationWithConsole.FILE_SYM, new Dictionary(CommunicationWithConsole.FILE_SYM, PATTERN_SYM, ioStream));
//        hashMap.put(CommunicationWithConsole.FILE_NUM, new Dictionary(CommunicationWithConsole.FILE_NUM, PATTERN_NUM, ioStream));
        hashMap.put(CommunicationWithConsole.FILE_NUM, new Dictionary(CommunicationWithConsole.FILE_SYM, PATTERN_SYM, ioStream));
        return hashMap;
    }
}




