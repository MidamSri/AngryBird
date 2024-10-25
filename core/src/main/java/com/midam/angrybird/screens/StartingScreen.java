package com.midam.angrybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.midam.angrybird.GussaelChidiyaan;

public class StartingScreen implements Screen {
    private final GussaelChidiyaan game;
    private Stage stage;
    private Texture logoTexture;
    private Texture loginButtonTexture;
    private Texture signupButtonTexture;
    private Table mainTable;
    public static OrthographicCamera gamecam;
    public static Viewport gameport;

    public StartingScreen(GussaelChidiyaan game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new StretchViewport(1820, 980, gamecam);


        stage = new Stage(new StretchViewport(1820, 980));
        Gdx.input.setInputProcessor(stage);

        logoTexture = new Texture(Gdx.files.internal("LoadScreen.png")); // Background image

        loginButtonTexture = new Texture(Gdx.files.internal("login.png")); // Load your login button image
        signupButtonTexture = new Texture(Gdx.files.internal("signinButton.png")); // Load your signup button image


        TextureRegionDrawable loginDrawable = new TextureRegionDrawable(loginButtonTexture);
        TextureRegionDrawable signupDrawable = new TextureRegionDrawable(signupButtonTexture);

        ImageButton loginButton = new ImageButton(loginDrawable);
        ImageButton signupButton = new ImageButton(signupDrawable);


        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Login button clicked");
                game.setScreen(new SignupScreen(game));
            }
        });

        signupButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Signup button clicked");
                game.setScreen(new SignupScreen(game));
            }
        });


        mainTable = new Table();
        mainTable.center();
        mainTable.add(loginButton).size(400, 200).pad(25); // Set size for login button
        mainTable.row();
        mainTable.add(signupButton).size(400, 180).pad(25); // Set size for signup button


        stage.addActor(mainTable);
    }

    @Override
    public void show() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.batch.draw(logoTexture, 0, 0, gameport.getWorldWidth(), gameport.getWorldHeight());
        game.batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);
        stage.getViewport().update(width, height, true);


        mainTable.setSize(400, 400);
        mainTable.setPosition(gameport.getWorldWidth() / 2 - 200, gameport.getWorldHeight() / 2 - 450);
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
        loginButtonTexture.dispose();
        signupButtonTexture.dispose();
    }
}
