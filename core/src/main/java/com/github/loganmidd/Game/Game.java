package com.github.loganmidd.Game;

import java.util.Map;

import com.github.loganmidd.effects.PlayerEffect;
import com.github.loganmidd.waves.Wave;

/**
 * Defines the core structure and state of a game instance.
 */
public interface Game {
    /**
     * Retrieves the wave at the specified index.
     *
     * @param n The zero-based index of the wave to retrieve.
     * @return The Wave object corresponding to the index.
     */
    public Wave getNthWave(int n);
    
    /**
     * Retrieves the file path to the map data.
     *
     * @return The string path to the map resource.
     */
    public String getMapPath();
    
    /**
     * Retrieves the current active effects applied to the player.
     *
     * @return A Map containing PlayerEffects and their associated duration or intensity values.
     */
    public Map<PlayerEffect, Float> getPlayerEffects();
}