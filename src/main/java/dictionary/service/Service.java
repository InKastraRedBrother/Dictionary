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
    private static final int MASK_SERIAL_NUMBER = 0;
    private static final int FILENAME_SERIAL_NUMBER = 1;
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
        if (key.matches(prop.get(MASK_SERIAL_NUMBER))) {
            return dao.save(key, value, prop.get(FILENAME_SERIAL_NUMBER));
        }
        return false;
    }

    public boolean deleteRowByKey(String key, ArrayList<String> prop) {
        return dao.deleteByKey(key, prop.get(FILENAME_SERIAL_NUMBER));
    }

    public Optional<Row> findRowByKey(String key, ArrayList<String> prop) {
        return dao.findByKey(prop.get(FILENAME_SERIAL_NUMBER), key);
    }
}
