package com.epita.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
    public static int score = 10;
    public static void readCSV(String fileName) throws IOException {
        BufferedReader reader = null;
        try {
            String line = null;
            reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");
                System.out.println("id = " + words[0] + " date_of_birth=" + words[1] + " gender=" + words[2]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static void readFile(String fileName) throws IOException {
        BufferedReader reader = null;
        try {
            String line = null;
            reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                System.out.println("line = " + line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
