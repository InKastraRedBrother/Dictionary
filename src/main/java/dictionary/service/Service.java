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
}
