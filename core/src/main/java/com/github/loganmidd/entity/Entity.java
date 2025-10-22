package com.github.loganmidd.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.github.loganmidd.structures.Block;
import com.github.loganmidd.world.World;

public abstract class Entity {
    private float x;
    private float y;
    private float dx;
    private float dy;
    private float width;
    private World world;

    
    public Entity(float x, float y) {
        this.width = 50f;
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
    }

///////////////////////////////////////////////////////////
///                 Getters && Setters                  ///
///////////////////////////////////////////////////////////

    public float getX()  { return x; }
    public float getY()  { return y; }
    public float getDx() { return dx; }
    public float getDy() { return dy; }
    public World getWorld() {return this.world; } 
    public float getWidth() { return this.width; }
    
    public void setX(float x)   { this.x = x; }
    public void setY(float y)   { this.y = y; }
    public void setDx(float dx) { this.dx = dx; }
    public void setDy(float dy) { this.dy = dy; }
    public void setWorld(World world) { this.world = world; }
    public void setWidth(float width) { this.width = width; }

    public void addDx(float dx) { this.dx += dx; }
    public void addDy(float dy) { this.dy += dy; }

///////////////////////////////////////////////////////////
///                       Logic                         ///
///////////////////////////////////////////////////////////

    public void logic() {

        // In case of collision with Block
        for (Block block : this.collision(this.world.getBlocks())) {
            float blockX = block.getX();
            float blockY = block.getY();
            float blockH = block.getHeight();
            float blockL = block.getLength();
            
            // Collision on the side
            if (blockY - this.width < this.y && this.y < blockY + blockH) {
                
                // If the entity if moving to the right
                if (this.dx > 0) {
                    this.x = blockX - this.width;
                }
                // To the left
                else if (this.dx < 0) {
                    this.x = blockX + blockL;
                }
                this.dx = 0;
            }
            // Else, the collision is on the top or bottom
            else {
                // If the entity is moving downwards
                if (this.dy < 0 ) {
                    this.y = blockY + blockH;
                    this.dy = 0;
                } 
                // The entity is moving upwards 
                else {
                    this.y = blockY - this.width;
                    this.dy = 0;
                }
            }
        }
        
        // Movement
        this.x += this.dx;
        this.y += this.dy;
        
        this.dx *= 0.05f;
        this.dy *= 0.05f;
        
    }

    public List<Block> collision(List<Block> blocks) {
        List<Block> collisions = new ArrayList<>();
        Rectangle rec = new Rectangle(this.x + this.dx, this.y + this.dy, this.width, this.width);
        
        for (Block block : blocks) {
            // To use Rectangle.overlaps() method from LibGDX
            float blockX = block.getX();
            float blockY = block.getY();
            float blockWidth = block.getLength();
            float blockHeight = block.getHeight();
            Rectangle blockRectangle = new Rectangle(blockX, blockY, blockWidth, blockHeight);

            if (rec.overlaps(blockRectangle)) {
                collisions.add(block);
            }

        }
        return collisions;
    }

    public abstract void input(); // The user should not be able to modify an entity on input generally

    public void render() {
        // Render yellow square if the entity hasn't implemented the method
        ShapeRenderer sh = this.world.getShapeRenderer();
        sh.setProjectionMatrix(this.world.getCamera().combined);
        sh.begin(ShapeType.Filled);
        sh.setColor(1, 1, 0, 1);
        sh.rect(this.x, this.y, this.width, this.width);
        sh.end();
    }

}
