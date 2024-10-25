package com.midam.angrybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.midam.angrybird.GussaelChidiyaan;

public class ChooseLevelScreen implements Screen {
    private final GussaelChidiyaan game;
    private Stage stage;
    private Texture logoTexture;
    private Texture[] levelTextures;
    private Table mainTable;
    public Viewport gameport = StartingScreen.gameport;
    public OrthographicCamera gamecam = StartingScreen.gamecam;

    public ChooseLevelScreen(GussaelChidiyaan game) {
        this.game = game;
        stage = new Stage(new StretchViewport(1820, 980));
        Gdx.input.setInputProcessor(stage);

        logoTexture = new Texture(Gdx.files.internal("levelmenuscreen.png"));


        levelTextures = new Texture[]{
            new Texture(Gdx.files.internal("level1button.png")),
            new Texture(Gdx.files.internal("level2button.png")),
            new Texture(Gdx.files.internal("level3button.png")),
            new Texture(Gdx.files.internal("level4button.png"))
        };


        ImageButton[] levelButtons = new ImageButton[levelTextures.length];
        for (int i = 0; i < levelTextures.length; i++) {
            TextureRegionDrawable drawable = new TextureRegionDrawable(levelTextures[i]);
            levelButtons[i] = new ImageButton(drawable);
            int levelIndex = i;
            levelButtons[i].addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    System.out.println("Level " + (levelIndex + 1) + " button clicked");
                    // TODO: Transition to the corresponding level screen
                    switch (levelIndex) {
                        case 0:
                            game.setScreen(new Level1Screen(game)); // Transition to Level 1
                            break;
                        case 1:
//                            game.setScreen(new Level2Screen(game)); // Transition to Level 2
                            break;
                        case 2:
//                            game.setScreen(new Level3Screen(game)); // Transition to Level 3
                            break;
                        case 3:
//                            game.setScreen(new Level4Screen(game)); // Transition to Level 4
                            break;
                        default:
                            break;
                    }
                }
            });
        }


        mainTable = new Table();
        mainTable.center();
        for (ImageButton levelButton : levelButtons) {
            mainTable.add(levelButton).size(200, 600).pad(25);
        }


        Texture backButtonTexture = new Texture(Gdx.files.internal("goback.png"));
        TextureRegionDrawable backDrawable = new TextureRegionDrawable(backButtonTexture);
        ImageButton backButton = new ImageButton(backDrawable);


        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Back button clicked");
                game.setScreen(new StartingScreen(game));
            }
        });


        Texture loadButtonTexture = new Texture(Gdx.files.internal("savebutton.png"));
        TextureRegionDrawable loadDrawable = new TextureRegionDrawable(loadButtonTexture);
        ImageButton loadButton = new ImageButton(loadDrawable);


        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Load button clicked");
                switch (GussaelChidiyaan.Loaded_Level - 1) {
                    case 0:
                        game.setScreen(new Level1Screen(game)); // Transition to Level 1
                        break;
                    case 1:
//                            game.setScreen(new Level2Screen(game)); // Transition to Level 2
                        break;
                    case 2:
//                            game.setScreen(new Level3Screen(game)); // Transition to Level 3
                        break;
                    case 3:
//                            game.setScreen(new Level4Screen(game)); // Transition to Level 4
                        break;
                    default:
                        break;
                }
            }
        });


        backButton.setSize(50, 50);
        loadButton.setSize(50, 50);


        backButton.setPosition(stage.getWidth() - 60, 10);
        loadButton.setPosition(stage.getWidth() - 120, 10);

        stage.addActor(mainTable);
        stage.addActor(backButton);
        stage.addActor(loadButton);
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
        for (Texture texture : levelTextures) {
            texture.dispose();
        }
    }
}
