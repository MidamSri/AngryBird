package com.midam.angrybird.praanee;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Seesha extends Actor {
    private Texture texture;

    public Seesha() {
        texture = new Texture("glassplank.png");
        setSize(texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(texture, getX(), getY(), getWidth() / 2, getHeight() / 2,
            getWidth(), getHeight(), 1, 1, getRotation(),
            0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }


    public void dispose() {
        texture.dispose();
    }
}
