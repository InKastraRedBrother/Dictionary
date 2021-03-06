package ru.dictionary.service;

import ru.dictionary.config.DictionaryParameters;
import ru.dictionary.dao.Dao;
import ru.dictionary.dao.DaoInterface;
import ru.dictionary.model.Row;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Establishes a set of available operations and coordinates the application's response in each operation.
 */
@Component
public class Service {

    DaoInterface dao;

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

    public boolean addRow(Row row, DictionaryParameters dictionaryParameters) {
        if (row.getKey().matches(dictionaryParameters.getMask())) {
            return dao.save(row, dictionaryParameters.getFileName());
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
