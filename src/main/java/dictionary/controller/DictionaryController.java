package dictionary.controller;
 import dictionary.model.Dictionary;
 import dictionary.model.File;
 import dictionary.model.Row;

 import java.io.IOException;

public class DictionaryController {
    Dictionary dictionary;
    File file;
    Row row;

    public DictionaryController(Dictionary dictionary, File file, Row row) {
        this.dictionary = dictionary;
        this.file = file;
        this.row = row;

    }

    public String setDictionaryLanguage(String s) throws IOException {
        if(s.equals("1")) {
            row.language.setLanguage("^[a-z]{4}");
            file.setFileName("Symbolic.txt");

        } else if (s.equals("2")) {
            row.language.setLanguage("^[0-9]{5}");
            file.setFileName("Numeric.txt");

        }
        return "Language has been set - " + row.language.getLanguage();
    }

    public String setPairWord(String s) throws IOException {
        row.setKey(s);
        row.setValue(s);
        return dictionary.add(s);
    }
}
