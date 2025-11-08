package org.example.randomCatFact.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "catfacts")
public class CatFactEntity {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String fact;

    public CatFactEntity() {}

    public CatFactEntity(String fact) {
        this.fact = fact;
    }

    @Override
    public String toString(){
        return String.format("%s: %s", id, fact);
    }
}