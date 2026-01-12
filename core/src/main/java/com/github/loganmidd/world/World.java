package com.github.loganmidd.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.github.loganmidd.Game.Game;
import com.github.loganmidd.effects.PlayerEffect;
import com.github.loganmidd.entity.Crystal;
import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.entity.Player;
import com.github.loganmidd.entity.enemies.Enemy;
import com.github.loganmidd.structures.Block;
import com.github.loganmidd.tiled.TMap;
import com.github.loganmidd.tiled.TMapEnemyPaths;
import com.github.loganmidd.ui.LevelUpUI;
import com.github.loganmidd.ui.PlayerHUD;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.waves.WaveManager;

/**
 * Manages the game world, including entities, blocks, the camera, map rendering, and game logic updates.
 * 
 * @author Logan Middendorf
 */
public final class World implements Disposable {
    private int frameNumber;
    private List<Block> blocks;
    private List<Block> blocksToAdd;
    private List<Entity> entities;
    private List<Entity> entitiesToAdd;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private static World instance;
    private static boolean isInitialized = false;
    private float zoomFactor = 1500f; // Number is arbitrary
    private TMap tMap; 
    private TMapEnemyPaths paths;
    private LevelUpUI levelUpUI;
    private PlayerHUD playerHUD;
    private WaveManager manager;
    private Game game;

    /**
     * Private constructor to enforce the singleton pattern.
     * Initializes lists, the SpriteBatch, Camera, and ShapeRenderer.
     */
    private World() {
        this.blocks = new ArrayList<>();
        this.blocksToAdd = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.entitiesToAdd = new ArrayList<>();
        this.spriteBatch = new SpriteBatch();
        // Initial Camera
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera(zoomFactor, zoomFactor*(h/w));
        this.camera.position.set(this.camera.viewportWidth / 2f, this.camera.viewportHeight / 2f, 0);
        this.camera.update();		
        this.shapeRenderer = new ShapeRenderer();
        this.frameNumber = 0;
        this.manager = new WaveManager();
    }

    /**
     * Retrieves the singleton instance of the World.
     * Initializes the instance if it has not been created yet.
     *
     * @return The single instance of World.
     */
    public static World getWorld() {
        if (!isInitialized) {
            isInitialized = true;
            instance = new World();
        }
        return instance;
    }

///////////////////////////////////////////////////////////
///                 Getters && Setters (etc)            ///
///////////////////////////////////////////////////////////

    /**
     * Retrieves the list of blocks in the world.
     *
     * @return The list of Block objects.
     */
    public List<Block>    getBlocks()        { return this.blocks; }

    /**
     * Retrieves the list of entities in the world.
     *
     * @return The list of Entity objects.
     */
    public List<Entity>   getEntities()      { return this.entities; }

    /**
     * Retrieves the camera used for viewing the world.
     *
     * @return The Camera object.
     */
    public Camera         getCamera()        { return this.camera; }

    /**
     * Retrieves the ShapeRenderer used for drawing geometric shapes.
     *
     * @return The ShapeRenderer object.
     */
    public ShapeRenderer  getShapeRenderer() { return this.shapeRenderer; }

    /**
     * Retrieves the SpriteBatch used for rendering sprites.
     *
     * @return The SpriteBatch object.
     */
    public SpriteBatch    getSpriteBatch()   { return this.spriteBatch; }

    /**
     * Retrieves the tiled map associated with the world.
     *
     * @return The TMap object.
     */
    public TMap           getTMap()          { return this.tMap; }

    /**
     * Retrieves the enemy paths defined by the tiled map.
     *
     * @return The TMapEnemyPaths object.
     */
    public TMapEnemyPaths getEnemyPaths()    { return this.paths; }

    /**
     * Retrieves the current frame number.
     *
     * @return The frame number (tick count).
     */
    public int            getFrameNumber()   { return this.frameNumber; }

    /**
     * Retrieves the WaveManager responsible for handling enemy waves.
     *
     * @return The WaveManager object.
     */
    public WaveManager    getWaveManager()   { return this.manager; }

    /**
     * Retrieves the Game instance associated with the world.
     *
     * @return The Game object.
     */
    public Game           getGame()          { return this.game; }

    /**
     * Counts the number of active enemy entities in the world.
     *
     * @return The count of enemies.
     */
    public int            getEnemyCount() {
        int count = 0;
        for (Entity entity : this.entities) {
            if (entity.isEnemy()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Adds a block to the world.
     * The block is added to a staging list and will be processed in the next logic update.
     *
     * @param block The Block to add.
     */
    public void addBlock(Block block) { 
        blocks.add(block);
    }

    /**
     * Adds an entity to the world.
     * The entity is added to a staging list and will be processed in the next logic update.
     * If the entity is an enemy, its path is initialized.
     *
     * @param entity The Entity to add.
     */
    public void addEntity(Entity entity) { 
        this.entitiesToAdd.add(entity); 

        if (entity.isEnemy()) {
            Enemy enemy = (Enemy) entity;
            enemy.initEnemyPath();
        }
    }

    /**
     * Removes a block from the world immediately.
     *
     * @param block The Block to remove.
     */
    public void removeBlock(Block block)    { this.blocks.remove(block); this.blocksToAdd.remove(block); }

    /**
     * Removes an entity from the world immediately.
     *
     * @param entity The Entity to remove.
     */
    public void removeEntity(Entity entity) { this.entities.remove(entity); this.entitiesToAdd.remove(entity); }
    
    /**
     * Sets a new camera for the world.
     *
     * @param newCamera The new OrthographicCamera.
     */
    public void setCamera(OrthographicCamera newCamera)    { this.camera = newCamera; }

    /**
     * Sets a new SpriteBatch for rendering.
     *
     * @param newSpriteBatch The new SpriteBatch.
     */
    public void setSpriteBatch(SpriteBatch newSpriteBatch) { this.spriteBatch = newSpriteBatch; }

    /**
     * Sets the Game instance and updates the WaveManager's reference to it.
     *
     * @param game The Game instance.
     */
    public void setGame(Game game) { this.game = game; this.getWaveManager().setGame(game); }

///////////////////////////////////////////////////////////
///                        Logic                        ///
///////////////////////////////////////////////////////////

    /**
     * Executes the main logic loop for the world.
     * Updates the frame count, processes pending block and entity additions, executes logic for all objects,
     * updates the camera position to follow the player, manages the PlayerHUD, disposes of out-of-bounds entities,
     * removes disposed entities, and advances the wave manager logic.
     */
    public void logic() {
        this.frameNumber++;
        this.blocks.addAll(this.blocksToAdd);
        this.entities.addAll(this.entitiesToAdd);
        this.entitiesToAdd.clear();
        this.blocksToAdd.clear();

        for (Block block : this.blocks) {
            block.logic();
        }

        for (Entity entity : this.entities) {
            entity.logic();
        }
        
        for (Entity e : this.entities) {
            if (e.getClass().equals(Player.class)) {
                this.camera.position.x = e.getCenterX();
                this.camera.position.y = e.getCenterY();
                if (this.playerHUD == null) {
                    this.playerHUD = new PlayerHUD((Player) e);
                }
            }
        }
        // Dispose of entities out of bounds
        Point bottomRightCorner = this.tMap.getCoordinatesOfTile(this.tMap.getWidth(), this.tMap.getHeight());
        for (Entity entity : this.getEntities()) {
            if (entity.getX() < 0 || entity.getX() > bottomRightCorner.getX()) {
                entity.dispose();
            } else if (entity.getY() < 0 || entity.getY() > bottomRightCorner.getY()) {
                entity.dispose();
            }
        }

        // Remove disposed entities
        List<Entity> disposed = new ArrayList<>();
        for (Entity entity :  this.getEntities()) {
            if (entity.isDisposed()) {
                disposed.add(entity);
            }
        }
        this.entities.removeAll(disposed);

        this.manager.logic();
    }

    /**
     * Handles input processing for the player entity.
     * Iterates through entities to find the Player and calls its input method.
     */
    public void input() {
        for (Entity entity : new ArrayList<>(this.entities)) {
            if (entity.getClass().equals(Player.class)) {
                Player p = (Player) entity;
                p.input();
            }
        }
    }

    /**
     * Renders the world environment and UI elements.
     * Updates the camera, sets the projection matrix, renders the tiled map, and renders the LevelUpUI and PlayerHUD if active.
     */
    public void render() {
        this.camera.update();
        this.spriteBatch.setProjectionMatrix(this.camera.combined);
        this.tMap.render();
        if (this.levelUpUI != null) {
            this.levelUpUI.render();
        }
        if (this.playerHUD != null) {
            this.playerHUD.render();
        }
    }

    /**
     * Renders all entities in the world.
     * Sorts entities by their Y-coordinate (from top to bottom) to ensure correct rendering order (painter's algorithm),
     * then renders each entity.
     */
    public void renderEntities() {
        this.spriteBatch.begin();
        this.entities.sort(new Comparator<Entity>() {

            @Override
            public int compare(Entity o1, Entity o2) {
                return -Float.compare(o1.getY(), o2.getY()); // Sort based on y coordinate
            }
        });

        for (Entity entity : this.entities) {
            entity.render();
        }
        
        this.spriteBatch.end();

    }

    /**
     * Checks if the current wave has ended.
     * A wave is considered over if there are no enemies remaining in the world.
     *
     * @return True if the enemy count is zero, false otherwise.
     */
    public boolean isWaveOver() {
        return this.getEnemyCount() == 0;
    }
    
    /**
     * Disposes of all resources held by the world.
     * Disposes of all entities, the SpriteBatch, the Tiled Map, and the LevelUpUI.
     */
    public void dispose() {
        for (Entity entity : this.entities) {
            entity.dispose();
        }
        this.spriteBatch.dispose();
        this.tMap.dispose();
        
        if (this.levelUpUI != null) {
            this.levelUpUI.dispose();
        }
    }

    /**
     * Handles window resize events.
     * Adjusts the camera viewport to maintain aspect ratio and resizes UI components.
     *
     * @param width  The new window width.
     * @param height The new window height.
     */
    public void resize(int width, int height) {
        this.camera.viewportWidth = zoomFactor;
        this.camera.viewportHeight = zoomFactor * height/width;
        this.camera.update();

        if (this.levelUpUI != null) {
            this.levelUpUI.resize(width, height);
        }

        if (this.playerHUD != null) {
            this.playerHUD.resize(width, height);
        }
    }

    /**
     * Loads a Tiled map from the specified file path.
     * Initializes the TMap, adds the Player at the spawn point, adds Crystals at their spawn points, and initializes enemy paths.
     *
     * @param filePath The path to the Tiled map file.
     */
    public void loadTiledMap(String filePath) {
        this.tMap = new TMap(filePath, camera); 
        this.addEntity(new Player(this.tMap.getPlayerSpawnPoint()));

        for (Point point : this.tMap.getCrystalSpawnPoints()) {
            Crystal crystal = new Crystal(0, 0);
            crystal.setCenterX(point.getX());
            crystal.setCenterY(point.getY());
            this.addEntity(crystal);
        }
        this.paths = new TMapEnemyPaths();
    }

    /**
     * Creates the LevelUpUI for the specified player.
     * Randomly selects 3 unique PlayerEffect upgrades based on their current weightings.
     *
     * @param player The Player instance to associate with the UI.
     */
    public void createLevelUpUI(Player player) {
        this.levelUpUI = new LevelUpUI(player);
        int upgradeChoiceCount = 3;
        Map<PlayerEffect, Float> effects = this.game.getPlayerEffects();
        float sum = 0; 
        for (float f : effects.values()) {
            sum += f;
        }

        float random;
        for (int i = 0; i< upgradeChoiceCount; i++) {
            random = (float) Math.random() * sum;
            for (PlayerEffect effect : effects.keySet()) {
                random -= effects.get(effect);
                if (random < 0) {
                    this.levelUpUI.addEffect(effect);
                    break;
                }
            }
        }
    }


}
