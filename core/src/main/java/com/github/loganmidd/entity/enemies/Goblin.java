package com.github.loganmidd.entity.enemies;

/**
 * Represents a Goblin enemy.
 * <p>
 * This class defines a specific type of Enemy with a default speed and a specific texture.
 */
public class Goblin extends Enemy {
    /**
     * Constructs a Goblin at the specified coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Goblin(float x, float y) {
        super(x, y);
        this.setSpeed(2f);
    }

    /**
     * Constructs a Goblin at the origin (0, 0).
     */
    public Goblin() {
        this(0, 0);
    }

    /**
     * Returns the file path to the Goblin's texture asset.
     *
     * @return The texture path string.
     */
    public String getTexturePath() {
        return "goblin.png";
    }
    
} 
