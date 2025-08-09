package com.example;

import com.google.gson.Gson;

import java.io.*;


public class RLReadWrite {
    private File file;
    private Gson gson = new Gson();

    public RLReadWrite(File folder, String rsn) {
        if (!folder.exists()) {
            folder.mkdirs();
        }
        this.file = new File(folder, rsn + ".json");
    }

    public static class Data{
        public int [] floors;
        public double petChance;
        public double lootChance;

        public Data(){
            this.floors = new int[9];

        }

        public Data(int [] floors) {
            this.floors = floors;
        }
    }

    public Data read(){
        if (!file.exists()) {
            return new Data();
        }
        try (FileReader reader = new FileReader(file)){
            Data loaded = gson.fromJson(reader, Data.class);

            // bad data
            if (loaded == null || loaded.floors == null || loaded.floors.length != 9) {
                return new Data();
            }
            return loaded;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Data();
        } catch (IOException e) {
            e.printStackTrace();
            return new Data();
        }
    }

    public void write(Data data){
        try (FileWriter writer = new FileWriter(file)){
            gson.toJson(data, writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
