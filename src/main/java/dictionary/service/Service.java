package dictionary.service;

import dictionary.config.DictionaryParameters;
import dictionary.dao.Dao;
import dictionary.model.Row;

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

    public List<Row> findAllRows(DictionaryParameters dictionaryParameters) {
        return dao.findAll(dictionaryParameters.getFileName());
    }

    public boolean addRow(String key, String value, DictionaryParameters dictionaryParameters) {
        if (key.matches(dictionaryParameters.getMask())) {
            return dao.save(key, value, dictionaryParameters.getFileName());
        }
        return false;
    }

    public boolean deleteRowByKey(String key, DictionaryParameters dictionaryParameters) {
        return dao.deleteByKey(key, dictionaryParameters.getFileName());
    }

    public Optional<Row> findRowByKey(String key, DictionaryParameters dictionaryParameters) {
        return dao.findByKey(key, dictionaryParameters.getFileName());
    }
}
