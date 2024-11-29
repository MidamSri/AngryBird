package com.midam.angrybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.midam.angrybird.GussaelChidiyaan;

public class ChooseLevelScreen implements Screen {
    private final GussaelChidiyaan game;
    private Stage stage;
    private Texture logoTexture;
    private Texture[] levelTextures;
    private Texture closeButtonTexture;
    private Texture dummyButton;
    private Table mainTable;


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
                public void clicked(InputEvent event, float x, float y) {
                    switch (levelIndex) {
                        case 0:
                            game.setScreen(new Level1Screen(game));
                            break;
                        case 1:
                            game.setScreen(new Level2Screen(game));
                            break;
                        case 2:
                            game.setScreen(new Level3Screen(game));
                            break;
                        case 3:
                            // game.setScreen(new Level4Screen(game));
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
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new StartingScreen(game));
            }
        });

        closeButtonTexture = new Texture(Gdx.files.internal("quitButtonFINAL.png"));
        TextureRegionDrawable closeDrawable = new TextureRegionDrawable(closeButtonTexture);
        ImageButton closeButton = new ImageButton(closeDrawable);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


        dummyButton = new Texture(Gdx.files.internal("SettingButton.png"));
        TextureRegionDrawable dummyy = new TextureRegionDrawable(dummyButton);
        ImageButton dummyButton = new ImageButton(dummyy);
        dummyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                Gdx.app.exit();
                System.out.println("Dummy Clicked");
                game.setScreen(new VictoryScreen(game));

            }
        });

        backButton.setSize(50, 50);
        backButton.setPosition(stage.getWidth() - 60, 10);

        closeButton.setSize(100, 100);
        closeButton.setPosition(stage.getWidth() - 110, stage.getHeight() - 110);

        dummyButton.setSize(200, 200);
        dummyButton.setPosition(20, 200);

        stage.addActor(mainTable);
        stage.addActor(backButton);
        stage.addActor(closeButton);
        stage.addActor(dummyButton);



    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        mainTable.setPosition(stage.getWidth() / 2, stage.getHeight() / 2 - 200);
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
        closeButtonTexture.dispose();
    }
}
