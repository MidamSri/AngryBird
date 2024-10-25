package com.midam.angrybird.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.midam.angrybird.GussaelChidiyaan;


public class HUD {

    public Stage stage;
    private Viewport viewport;

    private Integer World_Timer;
    private float Time_Count;
    private Integer Score;

    Label Countdown_Label;
    Label Score_Label;
    Label Time_Label;
    Label Level_Label;
    Label World_Label;
    Label Chidiyaan_Label;

    public HUD(SpriteBatch sb) {
        World_Timer = 300;
        Time_Count = 0;
        Score = 0;

        viewport = new StretchViewport(GussaelChidiyaan.WIDTH, GussaelChidiyaan.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Countdown_Label = new Label(String.format("%03d", World_Timer),
            new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Score_Label = new Label(String.format("%06d", Score),
            new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Time_Label = new Label(String.format("TIME"),
            new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Level_Label = new Label(String.format("1-1"),
            new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        World_Label = new Label(String.format("WORLD"),
            new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Chidiyaan_Label = new Label(String.format("CHIDIYAAN"),
            new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(Chidiyaan_Label).expandX().padTop(10);
        table.add(World_Label).expandX().padTop(10);
        table.add(Time_Label).expandX().padTop(10);
        table.row();
        table.add(Score_Label).expandX();
        table.add(Level_Label).expandX();
        table.add(Countdown_Label).expandX();

        stage.addActor(table);
    }
}
