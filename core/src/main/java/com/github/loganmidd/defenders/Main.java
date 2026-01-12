package com.github.loganmidd.defenders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.loganmidd.Game.ExampleGame;
import com.github.loganmidd.Game.Game;
import com.github.loganmidd.ui.TitleUI;
import com.github.loganmidd.world.World;

/**
 * The main application adapter handling the game lifecycle.
 * <p>
 * This class manages the initialization, updating, rendering, and disposal of the game world and UI.
 * It transitions from the title screen to the game world upon user interaction.
 * </p>
 * 
 * @author Logan Middendorf
 */
public class Main extends ApplicationAdapter {

    private World world;
    private Game game;
    private TitleUI ui;

    /**
     * Creates the application.
     * <p>
     * Initializes the {@link Game} instance and sets up the {@link TitleUI} with an input listener
     * to trigger the game start.
     * </p>
     */
    @Override
    public void create() {
        this.game = new ExampleGame();
        this.ui = new TitleUI(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                initWorld();
                return true;
            } 
        });
        
    }

    /**
     * Initializes the game world.
     * <p>
     * Retrieves the singleton {@link World} instance, loads the map defined by the current game,
     * and disposes of the title screen UI. The game instance is assigned to the world.
     * </p>
     */
    public void initWorld() {
        this.world = World.getWorld();
        this.world.loadTiledMap(this.game.getMapPath());
        this.ui.getStage().dispose();
        this.ui = null;
        this.world.setGame(this.game);
    }

    /**
     * Handles window resize events.
     * <p>
     * Delegates the resize event to the active component (world or UI) if it exists.
     * </p>
     * 
     * @param width The new width in pixels.
     * @param height The new height in pixels.
     */
    @Override
    public void resize(int width, int height) {
        if (this.world != null) {
            this.world.resize(width, height);
        } else if (this.ui != null) { 
            this.ui.resize(width, height);
        }
        
    }

    /**
     * Processes user input.
     * <p>
     * Delegates input handling to the world if it exists.
     * </p>
     */
    public void input() {
       this.world.input();
    }

    /**
     * Updates the game logic.
     * <p>
     * Delegates logic updates to the world if it exists.
     * </p>
     */
    public void logic() { 
        this.world.logic();
    }

    /**
     * Renders the current frame.
     * <p>
     * Clears the screen and renders the world or UI depending on the current game state.
     * </p>
     */
    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        if (this.world != null) {
            this.input();
            this.logic();
            this.world.render();
        }

        if (this.ui != null) {
            this.ui.render();
        }
    }   

    /**
     * Disposes of resources.
     * <p>
     * Cleans up the world or UI resources to prevent memory leaks.
     * </p>
     */
    @Override
    public void dispose() {
        if (this.world != null) {
            this.world.dispose();
        } else {
            this.ui.getStage().dispose();
        }
    }
}