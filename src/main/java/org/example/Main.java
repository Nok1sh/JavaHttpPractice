package org.example;

import org.example.randomCatFact.db.CatFactDatabaseManager;
import org.example.randomCatFact.db.CatFactEntity;
import org.example.randomCatFact.http.CatFact;
import org.example.randomCatFact.http.CatFactHTTPException;
import org.example.randomCatFact.http.CatFactService;
import org.example.randomCatFact.serialization.CatFactFileSerializer;

import org.example.randomFact.db.FactDatabaseManager;
import org.example.randomFact.db.FactEntity;
import org.example.randomFact.http.Fact;
import org.example.randomFact.http.FactHTTPException;
import org.example.randomFact.http.FactService;
import org.example.randomFact.serialization.FactFileSerializer;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger log = Logger.getLogger(Main.class.getName());
    private static final CatFactService catFactService = new CatFactService();
    private static final CatFactFileSerializer catfileSerializer = new CatFactFileSerializer();
    private static final CatFactDatabaseManager catFactDatabaseManager = new CatFactDatabaseManager();
    private static final FactService factService = new FactService();
    private static final FactFileSerializer fileSerializer = new FactFileSerializer();
    private static final FactDatabaseManager factDatabaseManager = new FactDatabaseManager();

    public static void main(String[] args) throws SQLException {
        catFactDatabaseManager.init();
        factDatabaseManager.init();
        boolean flagSwapHttp = true;  // false - рандомный факт | true - факты про котов
        System.out.println("Выберите действие\nSWAP - поменять факты\nHTTP - получить факт\n" +
                "FILE - получить последний факт\nDB - получить все сохранённые факты\nEXIT - выход");
        mainCycle: while (true) {
            switch (scanner.nextLine()) {
                case ("SWAP") -> {
                    flagSwapHttp = !flagSwapHttp;
                    System.out.println("Выбраны: " + (flagSwapHttp ? "факты про котов" : "обычные факты"));
                }
                case ("HTTP") ->
                        getFromHttp(flagSwapHttp);
                case ("FILE") ->
                        showFromFile(flagSwapHttp);
                case ("DB") -> {
                    System.out.println("База данных с : " + (flagSwapHttp ? "фактами про котов" : "обычными фактами"));
                    showFromDB(flagSwapHttp);
                }
                case ("EXIT") -> {
                    break mainCycle;
                }
            }
        }
    }

    private static void showFromDB(boolean flagSwapHttp) {
        if (flagSwapHttp){
            try {
                for (var fact : catFactDatabaseManager.getAllFacts()) {
                    System.out.println(fact);
                }
            } catch (SQLException e) {
                log.severe("SQL error " + e);
            }
        } else {
            try {
                for (var fact : factDatabaseManager.getAllFacts()) {
                    System.out.println(fact);
                }
            } catch (SQLException e) {
                log.severe("SQL error " + e);
            }
        }

    }

    private static void showFromFile(boolean flagSwapHttp) {
        if (flagSwapHttp){
            CatFact catFact = catfileSerializer.deserializeCatFact();
            System.out.println(catFact);
        } else {
            Fact fact = fileSerializer.deserializeFact();
            System.out.println(fact);
        }

    }

    private static void getFromHttp(boolean flagSwapHttp) {
        if (flagSwapHttp){
            try {
                var catFact = catFactService.getRandomFact();
                catfileSerializer.serializeCatFact(catFact);
                var entity = new CatFactEntity(catFact.fact());
                catFactDatabaseManager.saveFact(entity);
                System.out.println(catFact);

            } catch (CatFactHTTPException e) {
                log.severe("HTTP error" + e);
            } catch (SQLException e) {
                log.severe("SQL error " + e);
            }
        } else {
            try {
                var fact = factService.getRandomFact();
                fileSerializer.serializeFact(fact);
                var entity = new FactEntity(fact.text());
                factDatabaseManager.saveFact(entity);
                System.out.println(fact);

            } catch (FactHTTPException e) {
                log.severe("HTTP error" + e);
            } catch (SQLException e) {
                log.severe("SQL error " + e);
            }
        }
    }
}