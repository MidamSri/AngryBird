package com.midam.angrybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.midam.angrybird.GussaelChidiyaan;


public class VictoryScreen implements Screen {
    private final GussaelChidiyaan game;
    private Stage stage;
    private Texture backgroundTexture;
    private Texture quitButtonTexture;
    private Texture backButtonTexture;

    public VictoryScreen(GussaelChidiyaan game) {
        this.game = game;
        stage = new Stage(new StretchViewport(1820, 980));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("victoryscreen.png"));

        quitButtonTexture = new Texture(Gdx.files.internal("quitButtonFINAL.png"));
        TextureRegionDrawable quitDrawable = new TextureRegionDrawable(quitButtonTexture);
        ImageButton quitButton = new ImageButton(quitDrawable);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        quitButton.setSize(100, 100);
        quitButton.setPosition(stage.getWidth() - 110, stage.getHeight() - 110);

        backButtonTexture = new Texture(Gdx.files.internal("goback.png"));
        TextureRegionDrawable backDrawable = new TextureRegionDrawable(backButtonTexture);
        ImageButton backButton = new ImageButton(backDrawable);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new ChooseLevelScreen(game));
            }
        });
        backButton.setSize(100, 100);
        backButton.setPosition(stage.getWidth() - 110, 20);

        stage.addActor(quitButton);
        stage.addActor(backButton);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(stage.getCamera().combined);
        game.batch.begin();
        game.batch.draw(backgroundTexture, 0, 0, stage.getWidth(), stage.getHeight());
        game.batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        quitButtonTexture.dispose();
        backButtonTexture.dispose();
    }
}
