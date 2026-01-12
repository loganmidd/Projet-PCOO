package com.github.loganmidd.entity;

import com.github.loganmidd.utils.Point;

/**
 * Represents a Crystal entity.
 * <p>
 * A Crystal is a specific type of {@link Combattant} characterized by high health (200),
 * zero attack damage, and immobility. It serves as a stationary objective or base within the game world.
 * It is identifiable via the {@link #isCrystal()} method and uses a specific texture.
 */
public class Crystal extends Combattant {

    /**
     * Constructs a Crystal at the specified coordinates.
     * <p>
     * This constructor initializes the Crystal with:
     * <ul>
     *   <li>Maximum Health: 200</li>
     *   <li>Current Health: 200</li>
     *   <li>Attack Damage: 0</li>
     *   <li>Movable: false</li>
     * </ul>
     *
     * @param x The x-coordinate of the Crystal.
     * @param y The y-coordinate of the Crystal.
     */
    public Crystal(float x, float y) {
        super(x, y);
        this.setMovable(false);
        
        this.setMaxHealth(200);
        this.setCurrentHealth(200);
        this.setAttackDamage(0);
    }

    /**
     * Constructs a Crystal at the specified Point location.
     *
     * @param point The Point representing the location of the Crystal.
     */
    public Crystal(Point point) {
        this(point.getX(), point.getY());
    }

    /**
     * Returns the path to the texture resource used for rendering this Crystal.
     *
     * @return The file path "crystal.png".
     */
    @Override
    public String getTexturePath() {
        return "crystal.png";
    }

    /**
     * Determines if this entity is a Crystal.
     *
     * @return {@code true} always, as this is a Crystal instance.
     */
    @Override 
    public boolean isCrystal() {
        return true;
    }

}