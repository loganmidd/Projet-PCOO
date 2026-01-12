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

    public List<Block>    getBlocks()        { return this.blocks; }
    public List<Entity>   getEntities()      { return this.entities; }
    public Camera         getCamera()        { return this.camera; }
    public ShapeRenderer  getShapeRenderer() { return this.shapeRenderer; }
    public SpriteBatch    getSpriteBatch()   { return this.spriteBatch; }
    public TMap           getTMap()          { return this.tMap; }
    public TMapEnemyPaths getEnemyPaths()    { return this.paths; }
    public int            getFrameNumber()   { return this.frameNumber; }
    public WaveManager    getWaveManager()   { return this.manager; }
    public Game           getGame()          { return this.game; }
    public int            getEnemyCount() {
        int count = 0;
        for (Entity entity : this.entities) {
            if (entity.isEnemy()) {
                count++;
            }
        }
        return count;
    }
    
    public void addBlock(Block block) { 
        blocks.add(block);
    }
    public void addEntity(Entity entity) { 
        this.entitiesToAdd.add(entity); 

        if (entity.isEnemy()) {
            Enemy enemy = (Enemy) entity;
            enemy.initEnemyPath();
        }
    }

    public void removeBlock(Block block)    { this.blocks.remove(block); this.blocksToAdd.remove(block); }
    public void removeEntity(Entity entity) { this.entities.remove(entity); this.entitiesToAdd.remove(entity); }
    
    public void setCamera(OrthographicCamera newCamera)    { this.camera = newCamera; }
    public void setSpriteBatch(SpriteBatch newSpriteBatch) { this.spriteBatch = newSpriteBatch; }
    public void setGame(Game game) { this.game = game; this.getWaveManager().setGame(game); }

///////////////////////////////////////////////////////////
///                        Logic                        ///
///////////////////////////////////////////////////////////


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

    public void input() {
        for (Entity entity : new ArrayList<>(this.entities)) {
            if (entity.getClass().equals(Player.class)) {
                Player p = (Player) entity;
                p.input();
            }
        }
    }

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

    public boolean isWaveOver() {
        return this.getEnemyCount() == 0;
    }
    
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