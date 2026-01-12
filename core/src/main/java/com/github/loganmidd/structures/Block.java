package com.github.loganmidd.structures;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.github.loganmidd.world.World;

/**
 * Represents a rectangular block in the world.
 * <p>
 * The block is defined by its position (bottom-left corner), height, and length.
 * It maintains a collision state and provides rendering capabilities for debugging.
 * 
 * @author Logan Middendorf
 */
public class Block {
    private float x; // Bottom corner
    private float y; // of block
    private float height;
    private float length;
    private boolean trueCollision;
    
    /**
     * Constructs a new Block with specified dimensions and position.
     * 
     * @param x      The x-coordinate of the bottom-left corner.
     * @param y      The y-coordinate of the bottom-left corner.
     * @param height The height of the block.
     * @param length The length (width) of the block.
     */
    public Block(float x, float y, float height, float length) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.length = length;
        this.trueCollision = false; // By default
    }

    /**
     * Constructs a new Block from a Rectangle object.
     * 
     * @param r The rectangle defining the block's position and dimensions.
     */
    public Block(Rectangle r) {
        this(r.getX(), r.getY(), r.getHeight(), r.getWidth());
    } 

///////////////////////////////////////////////////////////
///                 Getters && Setters                  ///
///////////////////////////////////////////////////////////

    /**
     * Gets the x-coordinate of the bottom-left corner.
     * 
     * @return The x-coordinate.
     */
    public float   getX()               { return this.x; }
    
    /**
     * Gets the y-coordinate of the bottom-left corner.
     * 
     * @return The y-coordinate.
     */
    public float   getY()               { return this.y; }
    
    /**
     * Gets the height of the block.
     * 
     * @return The height.
     */
    public float   getHeight()          { return this.height; }
    
    /**
     * Gets the length (width) of the block.
     * 
     * @return The length.
     */
    public float   getLength()          { return this.length; }
    
    /**
     * Checks if the block has a true collision state.
     * 
     * @return True if collision is enabled, false otherwise.
     */
    public boolean hasTrueCollision()   { return this.trueCollision; }

    /**
     * Sets the x-coordinate of the bottom-left corner.
     * 
     * @param x The new x-coordinate.
     */
    public void setX(float x)           { this.x = x; }
    
    /**
     * Sets the y-coordinate of the bottom-left corner.
     * 
     * @param y The new y-coordinate.
     */
    public void setY(float y)           { this.y = y; }
    
    /**
     * Sets the height of the block.
     * 
     * @param height The new height.
     */
    public void setHeight(float height) { this.height = height; }
    
    /**
     * Sets the length (width) of the block.
     * 
     * @param length The new length.
     */
    public void setLength(float length) { this.length = length; }
    
    /**
     * Sets the collision state of the block.
     * 
     * @param state The new collision state.
     */
    public void setTrueCollision(boolean state) { this.trueCollision = state; }

///////////////////////////////////////////////////////////
///                        Logic                        ///
///////////////////////////////////////////////////////////

    /**
     * Executes logic updates for the block.
     * Currently does nothing.
     */
    public void logic() {

    }

    /**
     * Renders the block using the ShapeRenderer for debugging purposes.
     * Renders a filled cyan rectangle.
     */
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