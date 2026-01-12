package com.github.loganmidd.Game;

import java.util.HashMap;
import java.util.Map;

import com.github.loganmidd.effects.AddAttackDamageUpgrade;
import com.github.loganmidd.effects.HealToMaxHealth;
import com.github.loganmidd.effects.ImmortalityEffect;
import com.github.loganmidd.effects.PlayerEffect;
import com.github.loganmidd.entity.enemies.Goblin;
import com.github.loganmidd.waves.Wave;
import com.github.loganmidd.waves.WaveLoader;

/**
 * Provides a concrete implementation of the Game interface for the example game scenario.
 * <p>
 * This class is responsible for defining wave progression logic, map locations,
 * and the distribution of player effects.
 * 
 * @author Logan Middendorf
 */
public class ExampleGame implements Game {
    /**
     * Generates a Wave object based on the given level index.
     * <p>
     * If the index is 0, it loads a wave from a JSON file. For higher indices,
     * it generates a wave with an exponentially increasing number of Goblin enemies.
     *
     * @param n The zero-based index of the wave to retrieve.
     * @return A Wave instance containing the configured enemies.
     */
    @Override
    public Wave getNthWave(int n) {
        if (n == 0) {
            return WaveLoader.load("wave.json");
        }
        Wave wave = new Wave();
        for (int i=1; i<Math.pow(2, n+1); i++) {
            wave.addEnemy(new Goblin());
        }
        return wave;
    }

    /**
     * Retrieves the file path to the map resource.
     *
     * @return The string path to the example map file.
     */
    @Override
    public String getMapPath() {
        return "tiled/example.tmx";
    }

    /**
     * Retrieves the available player effects and their associated spawn weights.
     * <p>
     * The weights determine the relative probability of each effect appearing.
     * Higher values indicate higher probability.
     *
     * @return A Map linking PlayerEffect instances to their Float weight values.
     */
    @Override
    public Map<PlayerEffect, Float> getPlayerEffects() {
        HashMap<PlayerEffect, Float> map = new HashMap<>();
        map.put(new AddAttackDamageUpgrade(), 5f);
        map.put(new HealToMaxHealth(), 4f);
        map.put(new ImmortalityEffect(), 1f);
        return map;
    }
}