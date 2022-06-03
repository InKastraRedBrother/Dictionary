package dictionary.service;

import dictionary.dao.Dao;

import java.io.IOException;

/**
 * Establishes a set of available operations and coordinates the application's response in each operation.
 */
public class Service {
    Dao dao;

    /**
     * Constructor for DI
     *
     * @param dao DI
     */
    public Service(Dao dao) {
        this.dao = dao;
    }

    public String showAllRows() throws IOException {
        return dao.showAll();
    }

    public boolean addRow(String key, String value) throws IOException {
        return dao.add(key, value);
    }

    public boolean deleteRow(String key) throws IOException {
        return dao.delete(key);
    }

    public String searchRow(String key) throws IOException {
        return dao.search(key);
    }
}
