package dictionary.service;

import dictionary.config.DictionaryParameters;
import dictionary.dao.Dao;
import dictionary.dao.DaoInterface;
import dictionary.model.Row;

import java.util.List;
import java.util.Optional;

/**
 * Establishes a set of available operations and coordinates the application's response in each operation.
 */
public class Service {

    dictionary.dao.DaoInterface DaoInterface;

    /**
     * Constructor for DI.
     *
     * @param DaoInterface DI.
     */
    public Service(DaoInterface DaoInterface) {
        this.DaoInterface = DaoInterface;
    }

    public List<Row> findAllRows(DictionaryParameters dictionaryParameters) {
        return DaoInterface.findAll(dictionaryParameters.getFileName());
    }

    public boolean addRow(Row row, DictionaryParameters dictionaryParameters) {
        if (row.getKey().matches(dictionaryParameters.getMask())) {
            return DaoInterface.save(row, dictionaryParameters.getFileName());
        }
        return false;
    }

    public boolean deleteRowByKey(String key, DictionaryParameters dictionaryParameters) {
        return DaoInterface.deleteByKey(key, dictionaryParameters.getFileName());
    }

    public Optional<Row> findRowByKey(String key, DictionaryParameters dictionaryParameters) {
        return DaoInterface.findByKey(key, dictionaryParameters.getFileName());
    }
}
