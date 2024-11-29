package com.midam.angrybird.praanee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Gulel extends Actor {
    public Texture gulelTexture;

    public Gulel() {
        gulelTexture = new Texture(Gdx.files.internal("slingshot.png"));
        setSize(gulelTexture.getWidth(), gulelTexture.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(gulelTexture, getX(), getY(), getWidth(), getHeight());
    }

    public void dispose() {
        gulelTexture.dispose();
    }
}
