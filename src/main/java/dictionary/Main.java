package dictionary;

import dictionary.config.DictionaryConfiguration;
import dictionary.dao.Dao;
import dictionary.dao.DaoInterface;
import dictionary.service.Service;
import dictionary.view.ViewDictionary;

/**
 * Point of entry
 */
class Main {
    /**
     * run console application
     *
     * @param args null
     */
    public static void main(String[] args) {
        DictionaryConfiguration dictionaryConfiguration = new DictionaryConfiguration();
        DaoInterface dao = new Dao();
        Service service = new Service(dao);
        ViewDictionary viewDictionary = new ViewDictionary(service, dictionaryConfiguration);
        viewDictionary.runApp();
    }
}




