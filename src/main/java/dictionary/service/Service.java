package dictionary.service;

import dictionary.dao.Dao;
import dictionary.exception.SomeKindOfError;

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

    public String showAllRows() throws SomeKindOfError {
        return dao.showAll();
    }

    public boolean addRow(String key, String value) throws SomeKindOfError {
        return dao.add(key, value);
    }

    public boolean deleteRow(String key) throws SomeKindOfError {
        return dao.delete(key);
    }

    public String searchRow(String key) throws SomeKindOfError {
        return dao.search(key);
    }
}
