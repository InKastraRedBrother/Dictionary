package dictionary;

import dictionary.controller.DictionaryController;
import dictionary.controller.IOStream;
import dictionary.model.Dictionary;
import dictionary.model.File;
import dictionary.model.Language;
import dictionary.model.Row;
import dictionary.view.ViewDictionary;

import java.io.IOException;

/**
 * Point of entry
 */
class Main {
     /**
     * run console application
     * @param args null
     */
    public static void main(String[] args) throws IOException {
        IOStream ioStream = new IOStream();
        Language language = new Language();
        File file = new File();
        Row row = new Row(language);
        Dictionary dictionary = new Dictionary(file, language, ioStream);
        DictionaryController dictionaryController = new DictionaryController(dictionary, file, row);
        ViewDictionary viewDictionary = new ViewDictionary(dictionaryController);
        viewDictionary.runApp();
    }
}




