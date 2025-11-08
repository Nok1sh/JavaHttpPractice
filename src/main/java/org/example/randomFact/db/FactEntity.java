package org.example.randomFact.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "facts")
public class FactEntity {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String text;

    public FactEntity() {}

    public FactEntity(String text) {
        this.text = text;
    }

    @Override
    public String toString(){
        return String.format("%s: %s", id, text);
    }
}