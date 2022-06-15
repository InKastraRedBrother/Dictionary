package dictionary.service;

import dictionary.config.DictionaryInitialization;
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

    public List<Row> findAllRows(DictionaryInitialization dictionaryInitialization) {
        return dao.findAll(dictionaryInitialization.getFileName());
    }

    public boolean addRow(String key, String value, DictionaryInitialization dictionaryInitialization) {
        if (key.matches(dictionaryInitialization.getMask())) {
            return dao.save(key, value, dictionaryInitialization.getFileName());
        }
        return false;
    }

    public boolean deleteRowByKey(String key, DictionaryInitialization dictionaryInitialization) {
        return dao.deleteByKey(key, dictionaryInitialization.getFileName());
    }

    public Optional<Row> findRowByKey(String key, DictionaryInitialization dictionaryInitialization) {
        return dao.findByKey(key, dictionaryInitialization.getFileName());
    }
}
