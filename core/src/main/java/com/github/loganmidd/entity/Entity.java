package com.github.loganmidd.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.github.loganmidd.imageutils.TextureRenderer;
import com.github.loganmidd.structures.Block;
import com.github.loganmidd.world.World;

public abstract class Entity {
    private float x;
    private float y;
    private float dx;
    private float dy;
    private float width;
    private float height;
    private TextureRenderer renderer;

    
    public Entity(float x, float y) {
        this.width = 50f;  // Arbitrary but
        this.height = 50f; // default value
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
    }

///////////////////////////////////////////////////////////
///                 Getters && Setters                  ///
///////////////////////////////////////////////////////////

    public float getX()      { return x; }
    public float getY()      { return y; }
    public float getDx()     { return dx; }
    public float getDy()     { return dy; } 
    public float getWidth()  { return this.width; }
    public float getHeight() { return this.height; }
    public TextureRenderer getTextureRenderer() { return this.renderer; }
    
    public void setX(float x)           { this.x = x; }
    public void setY(float y)           { this.y = y; }
    public void setDx(float dx)         { this.dx = dx; }
    public void setDy(float dy)         { this.dy = dy; }
    
    public void setWidth(float width)   { 
        this.width = width; 
        if (this.renderer != null) {
            this.renderer.setWidth((int) width);
        }
    }

    public void setHeight(float height) { 
        this.height = height; 
        if (this.renderer != null) {
            this.renderer.setHeight((int) height);
        }
    }

    public void setRenderer(TextureRenderer textureRenderer) {
        this.renderer = textureRenderer;
        this.renderer.setHeight((int) this.getHeight());
        this.renderer.setWidth((int) this.getWidth());
    }

    public void addDx(float dx) { this.dx += dx; }
    public void addDy(float dy) { this.dy += dy; }

///////////////////////////////////////////////////////////
///                       Logic                         ///
///////////////////////////////////////////////////////////

    public void logic() {

        // In case of collision with Block
        List<Block> blocks = this.collision(World.getWorld().getBlocks());
        while (!blocks.isEmpty()) {
            Block block = blocks.get(0);
            float blockX = block.getX();
            float blockY = block.getY();
            float blockH = block.getHeight();
            float blockL = block.getLength();
            
            // Collision on the side
            if (blockY - this.height < this.y && this.y < blockY + blockH) {
                
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
                    this.y = blockY - this.height;
                    this.dy = 0;
                }
            }
            blocks = this.collision(World.getWorld().getBlocks());
        }
        
        // Movement
        this.x += this.dx;
        this.y += this.dy;
        this.dx *= 0.05f;
        this.dy *= 0.05f;
        
    }

    public List<Block> collision(List<Block> blocks) {
        List<Block> collisions = new ArrayList<>();
        Rectangle rec = new Rectangle(this.x + this.dx, this.y + this.dy, this.width, this.height);
        
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
        this.renderer.renderAt(x, y);
    }

}
