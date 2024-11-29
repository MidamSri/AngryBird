package com.midam.angrybird.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.midam.angrybird.GussaelChidiyaan;
import com.midam.angrybird.praanee.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;


public class Level2Screen implements Screen {
    private final GussaelChidiyaan game;
    private Music music;
    private Music music2;
    private Stage stage;
    private Texture logoTexture;
    private Table mainTable;
    public Viewport gameport = StartingScreen.gameport;

    private PeeliChidiyaan redBird;
    private PeeliChidiyaan redBird1;
    private PeeliChidiyaan redBird2;
    private PeeliChidiyaan[] birds;
    private PeeliChidiyaan currentBird;
    private int currentBirdIndex = 2;

    private Gulel gulel;
    private Loha Loha1;
    private Loha Loha2;
    private Loha Loha3;
    private Loha Loha4;
    private Loha Loha5;
    private Suar suar1;
    private Suar suar2;

    private float dragOffsetX, dragOffsetY;
    private boolean isDragging = false;
    private boolean isLaunched = false;
    private boolean isKhatam = false;
    private float launchTime = 0;
    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();

    private static final float BOUNCE_DURATION = 0.5f;
    private static final float BOUNCE_HEIGHT = 100f;
    private boolean isAnimating = false;
    private float animationTime = 0f;
    private Vector2 startPosition = new Vector2();
    private Vector2 endPosition = new Vector2();

    private final float CIRCLE_CENTER_X = 320;
    private final float CIRCLE_CENTER_Y = 460;
    private final float CIRCLE_RADIUS = 85;

    private ShapeRenderer shapeRenderer;
    private static final int TRAJECTORY_POINTS = 20;
    private static final float GRAVITY = -9.81f * 1000;
    private static final float MAX_VELOCITY = 800f;
    private boolean showTrajectory = false;
    private Vector2 launchVelocity = new Vector2();

    public Level2Screen(GussaelChidiyaan game) {
        this.game = game;
        stage = new Stage(new StretchViewport(1820, 980));
        Gdx.input.setInputProcessor(stage);
        shapeRenderer = new ShapeRenderer();

        logoTexture = new Texture(Gdx.files.internal("level_back.jpg"));
        mainTable = new Table();
        mainTable.center();

        music = GussaelChidiyaan.manager.get("music/title_theme.mp3", Music.class);
        music.setLooping(true);
        music.stop();

        music2 = GussaelChidiyaan.manager.get("music/ambient_green_jungleish.mp3", Music.class);
        music2.setLooping(true);
        music2.play();

        initializeObjects();
        initializeBirds();
        setupButtons();
        setupBirdInputListener();

        addActorsToStage();
    }

    private void checkCollisions() {
        if (!isLaunched) return;

        Rectangle birdBounds = new Rectangle(
            currentBird.getX(),
            currentBird.getY(),
            currentBird.getWidth(),
            currentBird.getHeight()
        );

        Loha[] Lohas = {Loha1, Loha2, Loha3, Loha4, Loha5};
        for (Loha Loha : Lohas) {
            Rectangle LohaBounds = new Rectangle(
                Loha.getX(),
                Loha.getY(),
                Loha.getWidth(),
                Loha.getHeight()
            );

            if (birdBounds.overlaps(LohaBounds)) {
                handleLohaCollision(Loha);
            }
        }

        Suar[] suars = {suar1, suar2};
        for (Suar suar : suars) {
            Rectangle suarBounds = new Rectangle(
                suar.getX(),
                suar.getY(),
                suar.getWidth(),
                suar.getHeight()
            );

            if (birdBounds.overlaps(suarBounds)) {
                handleSuarCollision(suar);
            }
        }
    }

    // Handle collision with Loha (e.g., apply damage or remove)
    private void handleLohaCollision(Loha Loha) {
        // Reduce Loha health or remove it
        Loha.setVisible(false);
        stage.getActors().removeValue(Loha, true);

        // Optional: Add score or play sound effect
    }

    // Handle collision with suar (e.g., apply damage or remove)
    private void handleSuarCollision(Suar suar) {
        // Reduce suar health or remove it
        suar.setVisible(false);
        stage.getActors().removeValue(suar, true);

        // Optional: Add score or play sound effect
    }

    private boolean jeetgye() {
        boolean allBirdsUsed = true;
        for (PeeliChidiyaan bird : birds) {
            if (isLaunched || !isKhatam) {
                allBirdsUsed = false;
                break;
            }
        }

        Suar[] suars = {suar1, suar2};
        boolean allSuarsDefeated = true;
        for (Suar suar : suars) {
            if (suar.isVisible()) {
                allSuarsDefeated = false;
                break;
            }
        }

        return allBirdsUsed && allSuarsDefeated;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update bird animation
        updateBirdAnimation(delta);

        // Update bird's position if launched
        if (isLaunched && !isAnimating) {
            launchTime += delta;

            // Apply projectile motion equations
            position.x += velocity.x * delta;
            position.y += velocity.y * delta + 0.5f * GRAVITY * delta * delta;

            // Check collisions
            checkCollisions();

            // If the bird reaches ground, stop motion and switch to next bird
            if (position.y <= 300) {
                isLaunched = false;
                velocity.y = 0;
                position.y = 300;
                isKhatam = true;
                currentBird.setPosition(position.x, position.y);
                switchToNextBird();
            } else {
                velocity.y += GRAVITY * delta;
                currentBird.setPosition(position.x, position.y);
            }
        }

        if (jeetgye()) {
            game.setScreen(new VictoryScreen(game));
            return;
        }

        // Draw everything
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        game.batch.begin();
        game.batch.draw(logoTexture, 0, 0, stage.getWidth(), stage.getHeight());
        game.batch.end();

        // Draw the stage and trajectory if enabled
        stage.act();
        stage.draw();

        if (showTrajectory) {
            shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
            drawTrajectory();
        }
    }

    private void initializeBirds() {
        redBird = new PeeliChidiyaan();
        redBird.setPosition(100, 300);
        redBird.setSize(redBird.getWidth() * 0.25f, redBird.getHeight() * 0.25f);

        redBird1 = new PeeliChidiyaan();
        redBird1.setPosition(200, 300);
        redBird1.setSize(redBird.getWidth(), redBird.getHeight());

        redBird2 = new PeeliChidiyaan();
        redBird2.setPosition(CIRCLE_CENTER_X, CIRCLE_CENTER_Y);
        redBird2.setSize(redBird.getWidth(), redBird.getHeight());

        birds = new PeeliChidiyaan[]{redBird, redBird1, redBird2};
        currentBird = birds[currentBirdIndex];

        // Store positions for animation
        startPosition.set(200, 300);
        endPosition.set(CIRCLE_CENTER_X, CIRCLE_CENTER_Y);
    }

    private void initializeObjects() {
        gulel = new Gulel();
        gulel.setPosition(300, 300);

        Loha1 = new Loha();
        Loha1.setPosition(1130, 360);
        Loha1.setRotation(90);
        Loha1.setSize(Loha1.getWidth() * 0.14f, Loha1.getHeight() * 0.14f);

        Loha2 = new Loha();
        Loha2.setPosition(1270, 360);
        Loha2.setRotation(90);
        Loha2.setSize(Loha1.getWidth(), Loha1.getHeight());

        Loha3 = new Loha();
        Loha3.setPosition(1200, 440);
        Loha3.setSize(Loha1.getWidth(), Loha1.getHeight());

        Loha4 = new Loha();
        Loha4.setPosition(1170, 520);
        Loha4.setRotation(73);
        Loha4.setSize(Loha1.getWidth(), Loha1.getHeight());

        Loha5 = new Loha();
        Loha5.setPosition(1230, 520);
        Loha5.setRotation(108);
        Loha5.setSize(Loha1.getWidth(), Loha1.getHeight());

        suar1 = new Suar();
        suar1.setPosition(1250, 300);
        suar1.setSize(suar1.getWidth() * 0.2f, suar1.getHeight() * 0.2f);

        suar2 = new Suar();
        suar2.setPosition(1250, 460);
        suar2.setSize(suar1.getWidth(), suar1.getHeight());
    }

    private void setupBirdInputListener() {
        InputListener birdListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (isKhatam || isAnimating || event.getTarget() != currentBird) return false;

                isDragging = true;
                showTrajectory = true;
                dragOffsetX = x;
                dragOffsetY = y;
                currentBird.toFront();
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (isLaunched || isAnimating || event.getTarget() != currentBird) return;

                if (isDragging) {
                    float newX = event.getStageX() - dragOffsetX;
                    float newY = event.getStageY() - dragOffsetY;

                    newX = Math.min(newX, CIRCLE_CENTER_X);
                    newY = Math.min(newY, CIRCLE_CENTER_Y);

                    float dx = newX - CIRCLE_CENTER_X;
                    float dy = newY - CIRCLE_CENTER_Y;
                    float distance = (float) Math.sqrt(dx * dx + dy * dy);

                    if (distance > CIRCLE_RADIUS) {
                        float scale = CIRCLE_RADIUS / distance;
                        newX = CIRCLE_CENTER_X + dx * scale;
                        newY = CIRCLE_CENTER_Y + dy * scale;
                    }

                    float velocityScale = distance / CIRCLE_RADIUS;
                    float angle = (float) Math.atan2(CIRCLE_CENTER_Y - newY, CIRCLE_CENTER_X - newX);
                    launchVelocity.x = (float) Math.cos(angle) * MAX_VELOCITY * velocityScale;
                    launchVelocity.y = (float) Math.sin(angle) * MAX_VELOCITY * velocityScale;

                    currentBird.setPosition(newX, newY);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (isLaunched || isAnimating || event.getTarget() != currentBird) return;

                isDragging = false;
                showTrajectory = false;
                isLaunched = true;
                launchTime = 0;
                position.set(currentBird.getX(), currentBird.getY());
                velocity.set(launchVelocity);
            }
        };

        // Add listener to all birds
        for (PeeliChidiyaan bird : birds) {
            bird.addListener(birdListener);
        }
    }

    private void addActorsToStage() {
        stage.addActor(gulel);
        stage.addActor(redBird);
        stage.addActor(redBird1);
        stage.addActor(redBird2);
        stage.addActor(Loha1);
        stage.addActor(Loha2);
        stage.addActor(Loha3);
        stage.addActor(Loha4);
        stage.addActor(Loha5);
        stage.addActor(suar1);
        stage.addActor(suar2);
    }

    private void setupButtons() {
        // Create back button
        Texture backButtonTexture = new Texture(Gdx.files.internal("goback.png"));
        TextureRegionDrawable backDrawable = new TextureRegionDrawable(backButtonTexture);
        ImageButton backButton = new ImageButton(backDrawable);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChooseLevelScreen(game));
            }
        });

        // Create load button
        Texture loadButtonTexture = new Texture(Gdx.files.internal("savebutton.png"));
        TextureRegionDrawable loadDrawable = new TextureRegionDrawable(loadButtonTexture);
        ImageButton loadButton = new ImageButton(loadDrawable);
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GussaelChidiyaan.Loaded_Level = 1;
            }
        });

        // Create restart button
        Texture restartButtonTexture = new Texture(Gdx.files.internal("restartbutton.png"));
        TextureRegionDrawable restartDrawable = new TextureRegionDrawable(restartButtonTexture);
        ImageButton restartButton = new ImageButton(restartDrawable);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Level2Screen(game));
            }
        });

        // Create close button
        Texture closeButtonTexture = new Texture(Gdx.files.internal("quitButtonFINAL.png"));
        TextureRegionDrawable closeDrawable = new TextureRegionDrawable(closeButtonTexture);
        ImageButton closeButton = new ImageButton(closeDrawable);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Set button sizes and positions
        backButton.setSize(50, 50);
        loadButton.setSize(50, 50);
        restartButton.setSize(50, 50);
        closeButton.setSize(50, 50);

        backButton.setPosition(stage.getWidth() - 60, 10);
        loadButton.setPosition(stage.getWidth() - 120, 10);
        restartButton.setPosition(10, stage.getHeight() - 60);
        closeButton.setPosition(stage.getWidth() - 60, stage.getHeight() - 60);

        // Add buttons to stage
        stage.addActor(mainTable);
        stage.addActor(backButton);
        stage.addActor(loadButton);
        stage.addActor(restartButton);
        stage.addActor(closeButton);
    }

    private void updateBirdAnimation(float delta) {
        if (isAnimating) {
            animationTime += delta;
            float progress = Math.min(animationTime / BOUNCE_DURATION, 1.0f);

            // Quadratic easing for smooth movement
            float x = startPosition.x + (endPosition.x - startPosition.x) * progress;

            // Add bounce effect using sine wave
            float bounceProgress = (float) Math.sin(progress * Math.PI);
            float bounceOffset = (1 - progress) * BOUNCE_HEIGHT * bounceProgress;
            float y = startPosition.y + (endPosition.y - startPosition.y) * progress + bounceOffset;

            currentBird.setPosition(x, y);

            // Animation complete
            if (progress >= 1.0f) {
                isAnimating = false;
                animationTime = 0f;
                currentBird.setPosition(endPosition.x, endPosition.y);
                isKhatam = false; // Reset for the next bird
            }
        }
    }

    private void switchToNextBird() {
        if (currentBirdIndex > 0) {
            currentBirdIndex--;
            currentBird = birds[currentBirdIndex];
            isAnimating = true;
            animationTime = 0f;
            startPosition.set(currentBird.getX(), currentBird.getY());
            isLaunched = false;
            isKhatam = false;
            showTrajectory = false;
        }
    }

    private void drawTrajectory() {
        if (!showTrajectory) return;

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 0.5f);

        float startX = currentBird.getX() + currentBird.getWidth() / 2;
        float startY = currentBird.getY() + currentBird.getHeight() / 2;

        int i = 0;
        while(true) {
            float t = i * 0.05f;
            float x = startX + launchVelocity.x * t;
            float y = startY + launchVelocity.y * t + 0.5f * GRAVITY * t * t;

            if (y <= 300) break;
            shapeRenderer.circle(x, y, 5);
            i++;
        }

        shapeRenderer.end();
    }

//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        // Update bird animation
//        updateBirdAnimation(delta);
//
//        // Update bird's position if launched
//        if (isLaunched && !isAnimating) {
//            launchTime += delta;
//
//            // Apply projectile motion equations
//            position.x += velocity.x * delta;
//            position.y += velocity.y * delta + 0.5f * GRAVITY * delta * delta;
//
//            // If the bird reaches ground, stop motion and switch to next bird
//            if (position.y <= 300) {
//                isLaunched = false;
//                velocity.y = 0;
//                position.y = 300;
//                isKhatam = true;
//                currentBird.setPosition(position.x, position.y);
//                switchToNextBird();
//            } else {
//                velocity.y += GRAVITY * delta;
//                currentBird.setPosition(position.x, position.y);
//            }
//        }
//
//        // Draw everything
//        game.batch.setProjectionMatrix(stage.getCamera().combined);
//        game.batch.begin();
//        game.batch.draw(logoTexture, 0, 0, stage.getWidth(), stage.getHeight());
//        game.batch.end();
//
//        // Draw the stage and trajectory if enabled
//        stage.act();
//        stage.draw();
//
//        if (showTrajectory) {
//            shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
//            drawTrajectory();
//        }
//    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        mainTable.setPosition(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2 - 200);
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        shapeRenderer.dispose();
        logoTexture.dispose();
        music.dispose();

        for (PeeliChidiyaan bird : birds) {
            bird.dispose();
        }

        gulel.dispose();
        Loha1.dispose();
        Loha2.dispose();
        Loha3.dispose();
        Loha4.dispose();
        Loha5.dispose();
        suar1.dispose();
        suar2.dispose();
    }

}
