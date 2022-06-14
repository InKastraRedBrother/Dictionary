package dictionary.service;

import dictionary.dao.Dao;
import dictionary.model.Row;

import java.util.ArrayList;
import java.util.List;
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

    public List<Row> findAllRows(String fileName) {
        return dao.findAll(fileName);
    }

    public boolean addRow(String key, String value, ArrayList<String> prop) {
        if (key.matches(prop.get(0))) {
            return dao.save(key, value, prop.get(1));
        }
        return false;
    }

    public boolean deleteRowByKey(String key, ArrayList<String> prop) {
        return dao.deleteByKey(key, prop.get(1));
    }

    public Optional<Row> findRowByKey(String key, ArrayList<String> prop) {
        return dao.findByKey(prop.get(1), key);
    }
}
