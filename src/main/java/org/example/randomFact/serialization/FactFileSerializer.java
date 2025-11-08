package org.example.randomFact.serialization;

import org.example.randomFact.http.Fact;

import java.io.*;


public class FactFileSerializer {
    private static final String DEFAULT_FILENAME = "fact.txt";

    public void serializeFact(Fact fact){
        try (FileOutputStream fileOut = new FileOutputStream(DEFAULT_FILENAME);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(fact);
            System.out.println("Факт сереализован в файл: " + DEFAULT_FILENAME);

            ProcessBuilder pb = new ProcessBuilder("notepad.exe", DEFAULT_FILENAME);
            pb.start();

        } catch (IOException e) {
            System.err.println("Ошибка сереализации: " + e.getMessage());
        }
    }

    public Fact deserializeFact(){
        try (FileInputStream fileIn = new FileInputStream(DEFAULT_FILENAME);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            Fact fact = (Fact) in.readObject();
            System.out.println("Факт десереализован из файла: " + DEFAULT_FILENAME);
            return fact;

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка десeреализации: " + e.getMessage());
            return null;
        }
    }
}
