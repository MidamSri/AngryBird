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
import com.midam.angrybird.praanee.Gulel;
import com.midam.angrybird.praanee.LalChidiyaan;
import com.midam.angrybird.praanee.Seesha;
import com.midam.angrybird.praanee.Suar;

public class Level1Screen implements Screen {
    private final GussaelChidiyaan game;
    private Stage stage;
    private Texture logoTexture;
    private Table mainTable;
    public Viewport gameport = StartingScreen.gameport;

    private LalChidiyaan redBird;
    private LalChidiyaan redBird1;
    private LalChidiyaan redBird2;

    private Gulel gulel;

    private Seesha seesha1;
    private Seesha seesha2;
    private Seesha seesha3;

    private Suar suar1;
    private Suar suar2;
    private Suar suar3;

    public Level1Screen(GussaelChidiyaan game) {
        this.game = game;
        stage = new Stage(new StretchViewport(1820, 980));
        Gdx.input.setInputProcessor(stage);

        logoTexture = new Texture(Gdx.files.internal("level_back.jpg"));

        mainTable = new Table();
        mainTable.center();

        redBird = new LalChidiyaan();
        redBird.setPosition(100, 300);
        redBird.setSize(redBird.getWidth() * 0.05f, redBird.getHeight() * 0.05f);

        redBird1 = new LalChidiyaan();
        redBird1.setPosition(200, 300);
        redBird1.setSize(redBird.getWidth(), redBird.getHeight());

        redBird2 = new LalChidiyaan();
        redBird2.setPosition(320, 460);
        redBird2.setSize(redBird.getWidth(), redBird.getHeight());

        gulel = new Gulel();
        gulel.setPosition(300, 300);

        seesha1 = new Seesha();
        seesha1.setPosition(1200, 360);
        seesha1.setRotation(90);
        seesha1.setSize(seesha1.getWidth() * 0.075f, seesha1.getHeight() * 0.075f);

        seesha2 = new Seesha();
        seesha2.setPosition(1200, 490);
        seesha2.setRotation(90);
        seesha2.setSize(seesha1.getWidth(), seesha1.getHeight());

        seesha3 = new Seesha();
        seesha3.setPosition(1200, 563);
        seesha3.setSize(seesha1.getWidth(), seesha1.getHeight());

        suar1 = new Suar();
        suar1.setPosition(1200, 580);
        suar1.setSize(suar1.getWidth() * 0.2f, suar1.getHeight() * 0.2f);

        suar2 = new Suar();
        suar2.setPosition(1300, 580);
        suar2.setSize(suar1.getWidth(), suar1.getHeight());

        stage.addActor(redBird);
        stage.addActor(redBird1);
        stage.addActor(gulel);
        stage.addActor(redBird2);
        stage.addActor(seesha1);
        stage.addActor(seesha2);
        stage.addActor(seesha3);
        stage.addActor(suar1);
        stage.addActor(suar2);

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

        Texture restartButtonTexture = new Texture(Gdx.files.internal("restartbutton.png"));
        TextureRegionDrawable restartDrawable = new TextureRegionDrawable(restartButtonTexture);
        ImageButton restartButton = new ImageButton(restartDrawable);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Restart button clicked");
                game.setScreen(new Level1Screen(game));
            }
        });

        Texture closeButtonTexture = new Texture(Gdx.files.internal("quitButtonFINAL.png"));
        TextureRegionDrawable closeDrawable = new TextureRegionDrawable(closeButtonTexture);
        ImageButton closeButton = new ImageButton(closeDrawable);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Close button clicked");
                Gdx.app.exit();
            }
        });

        backButton.setSize(50, 50);
        loadButton.setSize(50, 50);
        restartButton.setSize(50, 50);
        closeButton.setSize(50, 50);

        backButton.setPosition(stage.getWidth() - 60, 10);
        loadButton.setPosition(stage.getWidth() - 120, 10);
        restartButton.setPosition(10, stage.getHeight() - 60);
        closeButton.setPosition(stage.getWidth() - 60, stage.getHeight() - 60);

        stage.addActor(mainTable);
        stage.addActor(backButton);
        stage.addActor(loadButton);
        stage.addActor(restartButton);
        stage.addActor(closeButton);
    }

    @Override
    public void show() {
    }

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
        mainTable.setPosition(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2 - 200);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        logoTexture.dispose();
        redBird.dispose();
        gulel.dispose();
        seesha1.dispose();
        seesha2.dispose();
        seesha3.dispose();
        suar1.dispose();
        suar2.dispose();
    }
}
