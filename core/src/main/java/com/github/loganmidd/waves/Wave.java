package com.github.loganmidd.waves;

import java.util.ArrayList;

import com.github.loganmidd.entity.enemies.Enemy;

/**
 * Represents a wave of enemies.
 * <p>
 * This class manages a collection of {@link Enemy} instances, providing methods
 * to add, remove, and retrieve the enemies in the wave.
 * </p>
 */
public class Wave {
    /** The list of enemies currently in this wave. */
    public ArrayList<Enemy> enemies;

    /**
     * Constructs a new Wave with an empty list of enemies.
     */
    public Wave() {
        this.enemies = new ArrayList<>();
    }

    /**
     * Adds an enemy to this wave.
     *
     * @param enemy The enemy to add.
     */
    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    /**
     * Removes a specific enemy from this wave.
     *
     * @param enemy The enemy to remove.
     */
    public void removeEnemies(Enemy enemy) {
        this.enemies.remove(enemy);
    }

    /**
     * Retrieves the list of enemies in this wave.
     *
     * @return The list of enemies.
     */
    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

}