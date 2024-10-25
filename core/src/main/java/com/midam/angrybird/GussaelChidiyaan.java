package com.midam.angrybird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.midam.angrybird.screens.StartingScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GussaelChidiyaan extends Game {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 208;
    public static Runtime.Version gamePort;
    public SpriteBatch batch;
    public static int Loaded_Level = 1;

    @Override
    public void create() {
        batch = new SpriteBatch();
         setScreen(new StartingScreen(this));
    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
