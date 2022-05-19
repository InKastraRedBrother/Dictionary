package Dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * Point of entry
 */
class Main {
    private static final String PATTERN_SYM = "^[a-z]{4}+$";
    private static final String PATTERN_NUM = "^[0-9]{5}+$";
    public static final String FILE_SYM = "sym";
    public static final String FILE_NUM = "num";


    public static void main(String[] args) {

        IOStream ioStream = new IOStream();
        CommunicationWithConsole communicationWithConsole = new CommunicationWithConsole();
        MaskVerification maskVerification = new MaskVerification(communicationWithConsole);
        Map<String, Dictionary>  hashMap = createDictionaries(ioStream, communicationWithConsole);
        Start start = new Start(communicationWithConsole, hashMap, maskVerification);
        start.runApp();
    }

    public static Map<String, Dictionary> createDictionaries(IOStream ioStream, CommunicationWithConsole communicationWithConsole){
        Map<String, Dictionary> hashMap = new HashMap<>();
        hashMap.put("1", new Dictionary(FILE_SYM, PATTERN_SYM, ioStream, communicationWithConsole));
        hashMap.put("2", new Dictionary(FILE_NUM, PATTERN_NUM, ioStream, communicationWithConsole));

        return hashMap;
    }
}




