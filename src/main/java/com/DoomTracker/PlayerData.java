package com.DoomTracker;

import java.util.Arrays;

public class PlayerData
{
    public int[] floorsSinceCloth;
    public int[] floorsSinceEye;
    public int[] floorsSinceTreads;
    public int[] floorsSincePet;
    public int[] floorsSinceUnique;

    public PlayerData() {
        this.floorsSinceCloth = new int[9];
        this.floorsSinceEye = new int[9];
        this.floorsSinceTreads = new int[9];
        this.floorsSincePet = new int[9];
        this.floorsSinceUnique = new int[9];
    }

    public PlayerData(int[] cloth, int[] eye, int[] treads, int[] pet, int[] unique) {
        this.floorsSinceCloth = Arrays.copyOf(cloth, cloth.length);
        this.floorsSinceEye = Arrays.copyOf(eye, eye.length);
        this.floorsSinceTreads = Arrays.copyOf(treads, treads.length);
        this.floorsSincePet = Arrays.copyOf(pet, pet.length);
        this.floorsSinceUnique = Arrays.copyOf(unique, unique.length);
    }
}
