package com.midam.angrybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.midam.angrybird.GussaelChidiyaan;

public class Level1Screen implements Screen {
    private final GussaelChidiyaan game;
    private Stage stage;
    private Texture logoTexture;
    private Table mainTable;
    public Viewport gameport = StartingScreen.gameport;

    public Level1Screen(GussaelChidiyaan game) {
        this.game = game;
        stage = new Stage(new StretchViewport(1820, 980));
        Gdx.input.setInputProcessor(stage);

        logoTexture = new Texture(Gdx.files.internal("level_back.jpg"));

        mainTable = new Table();
        mainTable.center();

        // Back Button
        Texture backButtonTexture = new Texture(Gdx.files.internal("goback.png"));
        TextureRegionDrawable backDrawable = new TextureRegionDrawable(backButtonTexture);
        ImageButton backButton = new ImageButton(backDrawable);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Back button clicked");
                game.setScreen(new ChooseLevelScreen(game));
            }
        });

        // Load Button
        Texture loadButtonTexture = new Texture(Gdx.files.internal("savebutton.png"));
        TextureRegionDrawable loadDrawable = new TextureRegionDrawable(loadButtonTexture);
        ImageButton loadButton = new ImageButton(loadDrawable);
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Load button clicked");
                GussaelChidiyaan.Loaded_Level = 1;
            }
        });

        // Restart Button
        Texture restartButtonTexture = new Texture(Gdx.files.internal("restartbutton.png")); // Replace with your restart button texture
        TextureRegionDrawable restartDrawable = new TextureRegionDrawable(restartButtonTexture);
        ImageButton restartButton = new ImageButton(restartDrawable);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Restart button clicked");
                game.setScreen(new Level1Screen(game)); // Restart Level 1
            }
        });

        backButton.setSize(50, 50);
        loadButton.setSize(50, 50);
        restartButton.setSize(50, 50); // Set size for restart button

        backButton.setPosition(stage.getWidth() - 60, 10);
        loadButton.setPosition(stage.getWidth() - 120, 10);
        restartButton.setPosition(10, stage.getHeight() - 60); // Position at top left

        stage.addActor(mainTable);
        stage.addActor(backButton);
        stage.addActor(loadButton);
        stage.addActor(restartButton); // Add the restart button to the stage
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the background
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        game.batch.begin();
        game.batch.draw(logoTexture, 0, 0, stage.getWidth(), stage.getHeight());
        game.batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        mainTable.setPosition(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2 - 200);
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
        logoTexture.dispose();
        // Dispose of the button textures
    }
}
