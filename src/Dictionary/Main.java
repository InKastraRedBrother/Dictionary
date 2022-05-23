package Dictionary;

/**
 * Point of entry
 */
class Main {
     /**
     * run console application
     * @param args null
     */
    public static void main(String[] args) {
        IOStream ioStream = new IOStream();
        CommunicationWithConsole communicationWithConsole = new CommunicationWithConsole();
        DictionaryInitialization dictionaryInitialization = new DictionaryInitialization(communicationWithConsole, ioStream);
        Start start = new Start(communicationWithConsole,dictionaryInitialization);
        start.runApp();
    }
}




