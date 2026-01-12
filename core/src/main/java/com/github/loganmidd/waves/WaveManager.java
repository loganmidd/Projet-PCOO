package com.github.loganmidd.waves;

import java.util.ArrayList;
import java.util.List;

import com.github.loganmidd.Game.Game;
import com.github.loganmidd.entity.enemies.Enemy;
import com.github.loganmidd.utils.CooldownTimer;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.world.World;

/**
 * Manages the sequence and spawning of enemy waves in the game.
 * <p>
 * This class is responsible for handling the logic of starting waves, spawning
 * enemies at timed intervals, and tracking the current wave progress.
 */
public class WaveManager {
    /** The timer used to space out enemy spawns. */
    private CooldownTimer timer;
    /** The list of enemies remaining to be spawned in the current wave. */
    private ArrayList<Enemy> currentWaveEnemies;
    /** The index of the current wave. */
    private int waveIndex;
    /** The index of the next spawn point to use. */
    private int spawnIndex;
    /** The game instance, used to retrieve wave definitions. */
    private Game game;
    
    /**
     * Constructs a WaveManager with a new timer and empty wave list.
     * Initializes wave and spawn indices to zero.
     */
    public WaveManager() {
        this.timer = new CooldownTimer(1000, true);
        this.currentWaveEnemies = new ArrayList<>();
        this.waveIndex = 0;
        this.spawnIndex = 0;
    }

    /**
     * Sets the game instance for this WaveManager.
     *
     * @param game The Game instance to associate with this manager.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Executes the logic for the wave manager, typically called once per game tick.
     * <p>
     * If the cooldown is over and there are enemies remaining to spawn, it spawns the
     * next enemy at a calculated spawn point.
     */
    public void logic() {
        List<Point> points = World.getWorld().getTMap().getEnemySpawnPoints();
        if (this.timer.isCooldownOver() && !this.currentWaveEnemies.isEmpty()) {
            this.timer.resetCooldown();
            Enemy enemy = this.currentWaveEnemies.get(0);
            this.currentWaveEnemies.remove(0);
            
            Point point = points.get(spawnIndex++ % points.size());
            enemy.setCenterX(point.getX());
            enemy.setCenterY(point.getY());
            World.getWorld().addEntity(enemy);

        }

    }

    /**
     * Retrieves the current wave number.
     *
     * @return The index of the current wave.
     */
    public int getWaveNumber() {
        return this.waveIndex;
    }

    /**
     * Checks if the current wave has been fully spawned.
     * <p>
     * This is true if the internal list of enemies for the current wave is empty.
     *
     * @return true if no enemies are left to spawn for the current wave, false otherwise.
     */
    public boolean isWaveFullySpawned() {
        return this.currentWaveEnemies.isEmpty();
    }

    /**
     * Starts the next wave.
     * <p>
     * Clears the current list of enemies to spawn, retrieves the enemies for the
     * next wave from the game, and increments the wave index.
     */
    public void startNextWave() {
        this.currentWaveEnemies.clear();
        this.currentWaveEnemies.addAll(this.game.getNthWave(this.waveIndex).getEnemies());
        this.waveIndex++;
    }

}