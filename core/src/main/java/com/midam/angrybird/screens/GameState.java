package com.midam.angrybird.screens;

import com.badlogic.gdx.math.Vector2;
import com.midam.angrybird.praanee.LalChidiyaan;
import com.midam.angrybird.praanee.Seesha;
import com.midam.angrybird.praanee.Suar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    public int currentBirdIndex;
    public float birdPositionX;
    public float birdPositionY;
    public List<Boolean> seeshasVisible;
    public List<Boolean> suarsVisible;
    public List<Vector2> seeshasPositions;
    public List<Vector2> suarsPositions;

    public int score;
    public boolean isLaunched;
    public Vector2 velocity;

    public GameState() {
        seeshasVisible = new ArrayList<>();
        suarsVisible = new ArrayList<>();
        seeshasPositions = new ArrayList<>();
        suarsPositions = new ArrayList<>();
    }
}
