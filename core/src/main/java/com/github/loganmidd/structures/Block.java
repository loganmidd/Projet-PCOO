package com.github.loganmidd.structures;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.loganmidd.world.World;

public class Block {
    private float x; // Bottom corner
    private float y; // of block
    private float height;
    private float length;
    private World world;
    
    public Block(float x, float y, float height, float length) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.length = length;
    }

///////////////////////////////////////////////////////////
///                 Getters && Setters                  ///
///////////////////////////////////////////////////////////

    public float getX()      { return this.x; }
    public float getY()      { return this.y; }
    public float getHeight() { return this.height; }
    public float getLength() { return this.length; }
    public World getWorld()  { return this.world; }

    public void setX(float x)           { this.x = x; }
    public void setY(float y)           { this.y = y; }
    public void setHeight(float height) { this.height = height; }
    public void setLength(float length) { this.length = length; }
    public void setWorld(World world)   { this.world = world; }

///////////////////////////////////////////////////////////
///                        Logic                        ///
///////////////////////////////////////////////////////////

    public void logic() {

    }

    public void render() {
        ShapeRenderer sh = this.world.getShapeRenderer();
        sh.begin(ShapeType.Filled);
        sh.setColor(0, 1, 1, 1);
        sh.rect(this.x, this.y, this.length, this.height);
        sh.end();
    }


}
