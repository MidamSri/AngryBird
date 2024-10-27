package com.midam.angrybird.praanee;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Suar extends Actor {
    private Texture texture;

    public Suar() {
        texture = new Texture("piggy.png");
        setSize(texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(texture, getX(), getY(), getWidth() / 2, getHeight() / 2,
            getWidth(), getHeight(), 1, 1, getRotation(),
            0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

//    @Override
    public void dispose() {
        texture.dispose();
    }
}
