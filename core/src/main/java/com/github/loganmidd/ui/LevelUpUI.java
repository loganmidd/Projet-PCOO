package com.github.loganmidd.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.loganmidd.effects.PlayerEffect;
import com.github.loganmidd.entity.Player;

/**
 * Manages the user interface for the level-up screen, allowing the player to select new effects.
 */
public class LevelUpUI implements GameUI {
    private Stage stage;
    private List<PlayerEffect> effects;
    private Player player;
    private List<Button> buttons;
    private boolean isDisposed;
    private Skin skin;

    /**
     * Constructs a new LevelUpUI and initializes the stage and skin.
     *
     * @param player The player associated with this UI
     */
    public LevelUpUI(Player player) {
        this.effects = new ArrayList<>();
        this.player = player;
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(0.2f, 0.2f, 0.2f, 0.5f);
        pixmap.fill();
        Texture background = new Texture(pixmap);

        pixmap.setColor(0.4f, 0.4f, 0.4f, 0.5f);
        pixmap.fill();
        Texture backgroundHold = new Texture(pixmap);
        pixmap.dispose();

        BitmapFont font = new BitmapFont();
        TextButtonStyle style = new TextButtonStyle();
        
        this.skin = new Skin();
        this.skin.add("default", font);
        style.font = font;

        style.up = new TextureRegionDrawable(background);
        style.over = new TextureRegionDrawable(backgroundHold);
        skin.add("default", style);

        this.isDisposed = false;
        this.createMenu();
    }

    /**
     * Adds an effect to the list and updates the menu.
     *
     * @param playerEffect The effect to add
     */
    public void addEffect(PlayerEffect playerEffect) {
        this.effects.add(playerEffect);
        this.createMenu();
    }

    /**
     * Removes an effect from the list and updates the menu.
     *
     * @param playerEffect The effect to remove
     */
    public void removeEffect(PlayerEffect playerEffect) {
        this.effects.remove(playerEffect);
        this.createMenu();
    }

    /**
     * Returns the list of available effects.
     *
     * @return The list of PlayerEffect objects
     */
    public List<PlayerEffect> getEffects() {
        return this.effects;
    }

    /**
     * Returns the player associated with this UI.
     *
     * @return The Player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player associated with this UI.
     *
     * @param player The Player object to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Creates the menu buttons based on the current list of effects.
     */
    public void createMenu() {
        float width = Gdx.graphics.getWidth() * 0.7f;
        float height = Gdx.graphics.getHeight() * 0.7f;
        int columns = this.effects.size();
        int i = 0;
        float cornerX = (Gdx.graphics.getWidth() - width)/2f;
        float cornerY = (Gdx.graphics.getHeight() - height)/2f;

        this.buttons = new ArrayList<>();
        this.stage.clear();

        for (PlayerEffect effect : this.effects) {

            TextButton button = new TextButton(effect.getName() + "\n" + effect.getDescription(), skin);
            button.setHeight(height * 0.9f);
            button.setWidth(width / columns);
            button.setPosition(cornerX + i*width/columns, cornerY);
            button.getLabel().setWrap(true);
            
            button.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    getPlayer().addEffect(effect);
                    dispose();
                    return true;
                } 
            });

            this.buttons.add(button);
            i++;
        
        }

        for (Button button : this.buttons) {
            this.stage.addActor(button);
        }
    }

    /**
     * Renders the UI.
     */
    public void render() {
        if (this.isDisposed) {
            return;
        }
        float delta = Gdx.graphics.getDeltaTime();
        this.stage.getCamera().update();
        this.stage.act(delta);
        this.stage.draw();
    }

    /**
     * Disposes of resources used by the UI.
     */
    public void dispose() {
        this.stage.dispose();
        this.isDisposed = true;
    }

    /**
     * Handles resize events for the UI.
     *
     * @param width The new width
     * @param height The new height
     */
    public void resize(int width, int height) {
        if (!this.isDisposed) {
            this.stage.getViewport().getCamera().viewportWidth = width;
            this.stage.getViewport().getCamera().viewportHeight =  height/width;
            this.stage.getViewport().getCamera().update();
        }
    }
    
}

/**
 * UI interface implementation for LevelUpUI.
 */
interface UI {
    void render();
    void dispose();
    void resize(int width, int height);
}