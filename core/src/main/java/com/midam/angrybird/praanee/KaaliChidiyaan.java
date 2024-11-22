package com.midam.angrybird.praanee;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;

public class KaaliChidiyaan extends Actor {
    private Sprite sprite;

    public KaaliChidiyaan() {
        Texture texture = new Texture("blackchidiya.png");
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
