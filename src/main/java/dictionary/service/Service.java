package dictionary.service;

import dictionary.dao.Dao;
import dictionary.model.Row;

import java.util.Optional;

/**
 * Establishes a set of available operations and coordinates the application's response in each operation.
 */
public class Service {
    Dao dao;

    /**
     * Constructor for DI.
     *
     * @param dao DI.
     */
    public Service(Dao dao) {
        this.dao = dao;
    }

    public String findAllRows() {
        return dao.findAll();
    }

    public boolean addRow(String key, String value) {
        return dao.save(key, value);
    }

    public boolean deleteRowByKey(String key) {
        return dao.deleteByKey(key);
    }

    public Optional<Row> findRowByKey(String key) {
        return dao.findByKey(key);
    }
}
