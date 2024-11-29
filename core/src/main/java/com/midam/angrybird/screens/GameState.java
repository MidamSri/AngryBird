package com.midam.angrybird.screens;

import java.io.Serializable;

public class GameState implements Serializable {
    private float[][] redBirdPositions; // Array of red bird positions
    private float[][] suarPositions;   // Array of pig positions
    private float[][] seeshaPositions; // Array of glass positions
    private int remainingBirds;        // Remaining birds
    private boolean isGameOver;        // Game over flag

    public GameState(float[][] redBirdPositions, float[][] suarPositions, float[][] seeshaPositions, int remainingBirds, boolean isGameOver) {
        this.redBirdPositions = redBirdPositions;
        this.suarPositions = suarPositions;
        this.seeshaPositions = seeshaPositions;
        this.remainingBirds = remainingBirds;
        this.isGameOver = isGameOver;
    }

    public float[][] getRedBirdPositions() {
        return redBirdPositions;
    }

    public float[][] getSuarPositions() {
        return suarPositions;
    }

    public float[][] getSeeshaPositions() {
        return seeshaPositions;
    }

    public int getRemainingBirds() {
        return remainingBirds;
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}
