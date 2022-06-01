package dictionary;

import dictionary.dao.Dao;
import dictionary.service.Service;
import dictionary.view.ViewDictionary;

import java.io.IOException;

/**
 * Point of entry
 */
class Main {
    /**
     * run console application
     *
     * @param args null
     */
    public static void main(String[] args) throws IOException {
        Dao dao = new Dao();
        Service service = new Service(dao);
        ViewDictionary viewDictionary = new ViewDictionary(service);
        viewDictionary.runApp();
    }
}




