package com.github.loganmidd.waves;

import java.util.ArrayList;
import java.util.List;

import com.github.loganmidd.Game.Game;
import com.github.loganmidd.entity.enemies.Enemy;
import com.github.loganmidd.utils.CooldownTimer;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.world.World;

public class WaveManager {
    private CooldownTimer timer;
    private ArrayList<Enemy> currentWaveEnemies;
    private int waveIndex;
    private int spawnIndex;
    private Game game;
    
    public WaveManager() {
        this.timer = new CooldownTimer(1000, true);
        this.currentWaveEnemies = new ArrayList<>();
        this.waveIndex = 0;
        this.spawnIndex = 0;
    }

    public void setGame(Game game) {
        this.game = game;
    }

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

    public int getWaveNumber() {
        return this.waveIndex;
    }

    public boolean isWaveFullySpawned() {
        return this.currentWaveEnemies.isEmpty();
    }

    public void startNextWave() {
        this.currentWaveEnemies.clear();
        this.currentWaveEnemies.addAll(this.game.getNthWave(this.waveIndex).getEnemies());
        this.waveIndex++;
    }

}
