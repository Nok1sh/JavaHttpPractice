package org.example.randomFact.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class FactDatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:facts.db";
    private Dao<FactEntity, Integer> factDao;

    public void init() throws SQLException {
        ConnectionSource connectionSource = new JdbcConnectionSource(DATABASE_URL);
        factDao = DaoManager.createDao(connectionSource, FactEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, FactEntity.class);
    }

    public void saveFact(FactEntity fact) throws SQLException {
        factDao.create(fact);
    }

    public List<FactEntity> getAllFacts() throws SQLException {
        return factDao.queryForAll();
    }
}