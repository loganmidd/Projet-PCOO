package com.github.loganmidd.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.imageutils.TextureRenderer;
import com.github.loganmidd.structures.Block;
import com.github.loganmidd.tiled.TMap;

public final class World {
    private List<Block> blocks;
    private List<Entity> entities;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private static World instance;
    private static boolean isInitialized = false;
    private float zoomFactor = 1000f; // Number is arbitrary
    private TMap tMap; 

    private World() {
        this.blocks = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.spriteBatch = new SpriteBatch();
        // Initial Camera
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera(zoomFactor, zoomFactor*(h/w));
        this.camera.position.set(this.camera.viewportWidth / 2f, this.camera.viewportHeight / 2f, 0);
        this.camera.update();		
        this.shapeRenderer = new ShapeRenderer();
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

    public List<Block> getBlocks()          { return this.blocks; }
    public List<Entity> getEntities()       { return this.entities; }
    public Camera getCamera()               { return this.camera; }
    public ShapeRenderer getShapeRenderer() { return this.shapeRenderer; }
    public SpriteBatch getSpriteBatch()     { return this.spriteBatch; }
    public TMap getTMap()                   { return this.tMap; }
    public void addBlock(Block block) { 
        blocks.add(block);
    }
    public void addEntity(Entity entity, String pathToTexture) { 
        this.entities.add(entity); 
        TextureRenderer t = new TextureRenderer(this.spriteBatch);
        t.setPath(pathToTexture);
        entity.setRenderer(t);
        
    }

    public void removeBlock(Block block)    { this.blocks.remove(block); }
    public void removeEntity(Entity entity) { this.entities.remove(entity); }
    
    public void setCamera(OrthographicCamera newCamera)    { this.camera = newCamera; }
    public void setSpriteBatch(SpriteBatch newSpriteBatch) { this.spriteBatch = newSpriteBatch; }

///////////////////////////////////////////////////////////
///                        Logic                        ///
///////////////////////////////////////////////////////////


    public void logic() {
        for (Block block : this.blocks) {
            block.logic();
        }

        for (Entity entity : this.entities) {
            entity.logic();
        }
        // Center camera on player (currently only entity)
        Entity e = this.getEntities().get(0);
        this.camera.position.x = e.getX() + e.getWidth() / 2;
        this.camera.position.y = e.getY() + e.getWidth() / 2;
    }

    public void input() {
        for (Entity entity : this.entities) {
            entity.input();
        }
    }

    public void render() {

        this.camera.update();
        this.spriteBatch.setProjectionMatrix(this.camera.combined);
        // Uses Shaperenderer
        // Blocks should render "under" map
        for (Block block : this.blocks) {
            block.render();
        }
        this.tMap.render();
        // Uses textures
        this.spriteBatch.begin();
        for (Entity entity : this.entities) {
            entity.render();
        }
        this.spriteBatch.end();
        
    }
    
    public void dispose() {
        this.spriteBatch.dispose();
    }

    public void resize(int width, int height) {
        this.camera.viewportWidth = zoomFactor;
        this.camera.viewportHeight = zoomFactor * height/width;
        this.camera.update();
    }

    public void loadTiledMap(String filePath) {
        filePath = "/home/Partage/L2/PCOO/projet/tiled/minimal.tmx"; // Testing purposes
        this.tMap = new TMap(filePath, camera); 
    }
    
}
