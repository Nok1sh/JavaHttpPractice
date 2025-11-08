package org.example.randomCatFact.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class CatFactDatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:catfacts.db";
    private Dao<CatFactEntity, Integer> catFactDao;

    public void init() throws SQLException {
        ConnectionSource connectionSource = new JdbcConnectionSource(DATABASE_URL);
        catFactDao = DaoManager.createDao(connectionSource, CatFactEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, CatFactEntity.class);
    }

    public void saveFact(CatFactEntity fact) throws SQLException {
        catFactDao.create(fact);
    }

    public List<CatFactEntity> getAllFacts() throws SQLException {
        return catFactDao.queryForAll();
    }
}