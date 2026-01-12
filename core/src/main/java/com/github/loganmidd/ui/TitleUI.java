package com.github.loganmidd.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Manages the user interface for the game's title screen.
 * <p>
 * This class sets up the background, game title image, and a play button.
 * It handles the rendering of these elements and manages user input for the
 * play button via an injected {@link InputListener}.
 * </p>
 * 
 * @author Logan Middendorf
 */
public class TitleUI implements GameUI {
    private Stage stage;
    private SpriteBatch batch;

    private Image gameTitle;
    private ImageButton playButton;
    private Texture bg;
    
    /**
     * Constructs a new TitleUI and initializes all visual components.
     * <p>
     * Sets up the stage, the background texture, the title image, and the play button.
     * It also assigns the provided listener to the play button and registers the stage
     * as the input processor.
     * </p>
     * 
     * @param listener The InputListener to attach to the play button.
     */
    public TitleUI(InputListener listener) {
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.batch = new SpriteBatch();

        // Background
        this.bg = new Texture("titlebg.png");
        this.bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        // Game title actor
        this.gameTitle = new Image(new Texture("title.png"));
        this.gameTitle.setScale(1, 1);
        this.gameTitle.setOrigin(Align.center);

        // Play game
        this.playButton = new ImageButton(
            new TextureRegionDrawable(new Texture("playbutton.png"))
        );

        Gdx.input.setInputProcessor(this.stage);
        this.playButton.addListener(listener);

        this.showActors();

    }

    /**
     * Configures and adds the UI actors to the stage.
     * <p>
     * Calculates appropriate sizes and positions for the game title and play button
     * based on the current screen dimensions. It adds these actors to the stage
     * and clears any existing actions.
     * </p>
     */
    public void showActors() {
        this.stage.addActor(this.gameTitle);
        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        // Set initial positions
        float titleX = (width - this.gameTitle.getWidth()) * 0.5f;
        float titleY = (height - this.gameTitle.getHeight()) * 0.75f;

        this.gameTitle.setPosition(titleX, titleY);
        this.gameTitle.setScale(5);
        
        float ratio = width/height;
        float playButtonWidth = width*0.2f;
        float playButtonHeight = playButtonWidth / ratio;
        this.playButton.setSize(playButtonWidth, playButtonHeight);
        this.playButton.getImageCell().width(playButtonWidth).height(playButtonHeight);

        this.gameTitle.clearActions();

        // Play Button
        this.playButton.setPosition(
            (width - this.playButton.getWidth()) * 0.5f,
            (height - this.playButton.getHeight()) * 0.25f);
        this.playButton.clearActions();
        this.stage.addActor(this.playButton);
    }

    /**
     * Returns the Stage associated with this UI.
     * 
     * @return The {@link Stage} used for rendering and input handling.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Updates the viewport dimensions.
     * <p>
     * This method should be called when the window size changes to ensure the UI
     * is correctly resized and centered.
     * </p>
     * 
     * @param width The new width of the viewport.
     * @param height The new height of the viewport.
     */
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
    }

    /**
     * Renders the UI elements to the screen.
     * <p>
     * This method draws the background texture using the SpriteBatch and then
     * updates and draws the Stage, which contains the title and play button.
     * </p>
     */
    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        //this.batch.setProjectionMatrix(this.stage.getCamera().combined);

        // Render background
        this.batch.begin();
        this.batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch.end();

        // Render scene
        this.stage.act(delta);
        this.stage.draw();
    }

    /**
     * Releases all resources held by the UI.
     * <p>
     * Disposes of the stage, background texture, and sprite batch to prevent
     * memory leaks.
     * </p>
     */
    @Override
    public void dispose() {
        this.stage.dispose();
        this.bg.dispose();
        this.batch.dispose();
    }
    
}