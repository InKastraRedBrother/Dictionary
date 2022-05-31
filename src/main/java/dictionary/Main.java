package dictionary;

import dictionary.controller.DictionaryInitialization;
import dictionary.controller.IOStream;
import dictionary.view.Start;

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
        DictionaryInitialization dictionaryInitialization = new DictionaryInitialization(ioStream);
        Start start = new Start(dictionaryInitialization);
        start.runApp();
    }
}




