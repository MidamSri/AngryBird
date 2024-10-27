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

public class SignupScreen implements Screen {
    private final GussaelChidiyaan game;
    private Stage stage;
    private Texture logoTexture;
    private Texture nextButtonTexture;
    private Texture signupButtonTexture;
    private Table mainTable;
    public Viewport gameport = StartingScreen.gameport;
    public OrthographicCamera gamecam = StartingScreen.gamecam;

    public SignupScreen(GussaelChidiyaan game) {
        this.game = game;
        stage = new Stage(new StretchViewport(1820, 980));
        Gdx.input.setInputProcessor(stage);

        logoTexture = new Texture(Gdx.files.internal("loginpage.png"));


        nextButtonTexture = new Texture(Gdx.files.internal("nextbutton.png"));


        TextureRegionDrawable nextDrawable = new TextureRegionDrawable(nextButtonTexture);
        ImageButton nextButton = new ImageButton(nextDrawable);


        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                System.out.println("Next button clicked");
                game.setScreen(new ChooseLevelScreen(game));
            }
        });


        mainTable = new Table();
        mainTable.center();
        mainTable.add(nextButton).size(300, 200).pad(25);
        mainTable.row();


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


        backButton.setSize(50, 50);
        backButton.setPosition(stage.getWidth() - 60, 10);

        stage.addActor(mainTable);
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
        game.batch.draw(logoTexture, 0, 0, stage.getWidth(), stage.getHeight());
        game.batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        mainTable.setSize(200, 100);
        mainTable.setPosition(gameport.getWorldWidth() / 2 - 60, gameport.getWorldHeight() / 2 - 370);
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
        nextButtonTexture.dispose();
    }
}
