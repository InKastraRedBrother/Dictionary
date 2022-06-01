package dictionary.service;

import dictionary.dao.Dao;

public class Service {
    Dao dao;

    public Service(Dao dao) {
        this.dao = dao;
    }

    public String showAllRows() {
        return dao.showAll();
    }
    public String addRow(String key, String value) {
        return dao.add(key, value);
    }
    public String deleteRow(String key){
        return dao.delete(key);
    }
    public String searchRow(String key){
        return dao.search(key);
    }
}
