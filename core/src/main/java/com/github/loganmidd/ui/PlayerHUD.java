package com.github.loganmidd.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.loganmidd.entity.Player;
import com.github.loganmidd.world.World;

public class PlayerHUD implements UI {
    private Stage stage;
    private Player player;
    private Label label;
    private Skin skin;
    private boolean isDisposed;

    public PlayerHUD(Player player) {
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.player = player;
        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(0.2f, 0.2f, 0.2f, 0.5f);
        pixmap.fill();
        Texture background = new Texture(pixmap);
        pixmap.dispose();

        BitmapFont font = new BitmapFont();
        LabelStyle style = new LabelStyle();
        style.font = font;
        style.fontColor = Color.WHITE;

        this.skin = new Skin();
        skin.add("default", font);
        skin.add("default", style);
        skin.add("default", background);
        this.makeLabel();

    }

    private void makeLabel() {
        String labelText = "Towers Keys : (D) (X)";
        labelText += "\nAttacks : (F) (C)";
        labelText += "\nStart Wave: (G)";
        labelText += "\nHealth : " + this.player.getCurrentHealth() + "/" + this.player.getMaxHealth();
        labelText += "\nAttack Damage : " + this.player.getAttackDamage();
        labelText += "\nLevel : " + this.player.getLevel();
        labelText += "\nExp : " + this.player.getExp() + "/" + this.player.getExpToNextLevel();
        labelText += "\nEnemies remaining : " + World.getWorld().getEnemyCount();
        labelText += "\nWave : " + World.getWorld().getWaveManager().getWaveNumber();
        

        this.label = new Label(labelText, this.skin);
        this.label.setX(0);
        this.label.setY(0);

        this.stage.clear();
        this.stage.addActor(this.label);
    }

    @Override
    public void dispose() {
        if (!this.isDisposed) {
            this.isDisposed = true;
            this.stage.dispose();
            this.player.dispose();
            this.skin.dispose();
        }
    }

    @Override
    public void render() {
        if (this.isDisposed) {
            return;
        }
        float delta = Gdx.graphics.getDeltaTime();
        this.makeLabel();
        this.stage.getCamera().update();
        this.stage.act(delta);
        this.stage.draw();
    }

    public void resize(int width, int height) {
        if (!this.isDisposed) {
            this.stage.getViewport().getCamera().viewportWidth = width;
            this.stage.getViewport().getCamera().viewportHeight =  height/width;
            this.stage.getViewport().getCamera().update();
            this.makeLabel();
        }
    }
}
