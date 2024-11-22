package com.midam.angrybird.praanee;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;

public class NeeliChidiyaan extends Actor {
    private Sprite sprite;

    public NeeliChidiyaan() {
        Texture texture = new Texture("BlueBird.png");
        sprite = new Sprite(texture);
        setSize(sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        sprite.setPosition(getX(), getY());
        sprite.setSize(getWidth(), getHeight());
        sprite.draw(batch, alpha);
    }

    //    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }
}
