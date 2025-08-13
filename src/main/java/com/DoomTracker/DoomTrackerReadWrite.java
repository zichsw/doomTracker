package com.DoomTracker;

import com.google.gson.Gson;

import java.io.*;


public class DoomTrackerReadWrite {
    private File file;
    private Gson gson = new Gson();

    public DoomTrackerReadWrite(File folder, String rsn) {
        if (!folder.exists()) {
            folder.mkdirs();
        }
        this.file = new File(folder, rsn + ".json");
    }

    public static class Data{
        public int [] floorsSinceCloth;
        public int [] floorsSinceEye;
        public int [] floorsSinceTreads;
        public int [] floorsSincePet;
        public int [] floorsSinceUnique;



        public Data(){
            this.floorsSinceCloth = new int[9];
            this.floorsSinceEye = new int[9];
            this.floorsSinceTreads = new int[9];
            this.floorsSincePet = new int[9];
            this.floorsSinceUnique = new int[9];



        }

        public Data(int[] floorsSinceCloth, int[] floorsSinceEye,  int[] floorsSinceTreads, int [] floorsSincePet, int [] floorsSinceUnique) {
            this.floorsSinceCloth = floorsSinceCloth;
            this.floorsSinceEye = floorsSinceEye;
            this.floorsSinceTreads = floorsSinceTreads;
            this.floorsSincePet = floorsSincePet;
            this.floorsSinceUnique = floorsSinceUnique;
        }
    }

    public Data read(){
        if (!file.exists()) {
            return new Data();
        }
        try (FileReader reader = new FileReader(file)){
            Data loaded = gson.fromJson(reader, Data.class);

            // bad data
            if (loaded == null || loaded.floorsSinceUnique == null || loaded.floorsSinceUnique.length != 9) {
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
