package org.example.randomCatFact.serialization;

import org.example.randomCatFact.http.CatFact;

import java.io.*;


public class CatFactFileSerializer {
    private static final String DEFAULT_FILENAME = "catfact.txt";

    public void serializeCatFact(CatFact fact){
        try (FileOutputStream fileOut = new FileOutputStream(DEFAULT_FILENAME);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(fact);
            System.out.println("Факт про котов сереализован в файл: " + DEFAULT_FILENAME);

            ProcessBuilder pb = new ProcessBuilder("notepad.exe", DEFAULT_FILENAME);
            pb.start();

        } catch (IOException e) {
            System.err.println("Ошибка сереализации: " + e.getMessage());
        }
    }

    public CatFact deserializeCatFact(){
        try (FileInputStream fileIn = new FileInputStream(DEFAULT_FILENAME);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            CatFact fact = (CatFact) in.readObject();
            System.out.println("Факт про котов десереализован из файла: " + DEFAULT_FILENAME);
            return fact;

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка десeреализации: " + e.getMessage());
            return null;
        }
    }
}
