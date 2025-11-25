package com.github.loganmidd.structures;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.loganmidd.world.World;

public class Block {
    private float x; // Bottom corner
    private float y; // of block
    private float height;
    private float length;
    private boolean trueCollision;
    
    public Block(float x, float y, float height, float length) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.length = length;
        this.trueCollision = false; // By default
    }

///////////////////////////////////////////////////////////
///                 Getters && Setters                  ///
///////////////////////////////////////////////////////////

    public float   getX()               { return this.x; }
    public float   getY()               { return this.y; }
    public float   getHeight()          { return this.height; }
    public float   getLength()          { return this.length; }
    public boolean hasTrueCollision()   { return this.trueCollision; }

    public void setX(float x)           { this.x = x; }
    public void setY(float y)           { this.y = y; }
    public void setHeight(float height) { this.height = height; }
    public void setLength(float length) { this.length = length; }
    public void setTrueCollision(boolean state) { this.trueCollision = state; }

///////////////////////////////////////////////////////////
///                        Logic                        ///
///////////////////////////////////////////////////////////

    public void logic() {

    }

    public void render() {
        // FOR DEBUGGING
        World instance = World.getWorld();
        ShapeRenderer sh = instance.getShapeRenderer();
        sh.setProjectionMatrix(instance.getCamera().combined);
        sh.begin(ShapeType.Filled);
        sh.setColor(0, 1, 1, 1);
        sh.rect(this.x, this.y, this.length, this.height);
        sh.end();
    }


}
