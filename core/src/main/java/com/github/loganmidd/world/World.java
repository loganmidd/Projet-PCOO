package com.github.loganmidd.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.structures.Block;

public class World {
    private List<Block> blocks;
    private List<Entity> entities;
    private Camera camera;
    private ShapeRenderer shapeRenderer;

    public World(Camera camera) {
        this.blocks = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.camera = camera;
        this.shapeRenderer = new ShapeRenderer();
    }

///////////////////////////////////////////////////////////
///                 Getters && Setters (etc)            ///
///////////////////////////////////////////////////////////

    public List<Block> getBlocks()          { return this.blocks; }
    public List<Entity> getEntities()       { return this.entities; }
    public Camera getCamera()               { return this.camera; }
    public ShapeRenderer getShapeRenderer() { return this.shapeRenderer; }

    public void addBlock(Block block) { 
        this.blocks.add(block);
        block.setWorld(this); 
    }
    public void addEntity(Entity entity) { 
        this.entities.add(entity); 
        entity.setWorld(this);
    }

    public void removeBlock(Block block)    { this.blocks.remove(block); }
    public void removeEntity(Entity entity) { this.entities.remove(entity); }
    
    public void setCamera(Camera camera) { this.camera = camera; }

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
    }

    public void input() {
        for (Entity entity : this.entities) {
            entity.input();
        }
    }

    public void render() {
        for (Block block : this.blocks) {
            block.render();
        }

        for (Entity entity : this.entities) {
            entity.render();
        }
    }
    
    
}
