package com.github.loganmidd.waves;

import java.util.ArrayList;

import com.github.loganmidd.entity.enemies.Enemy;

public class Wave {
    public ArrayList<Enemy> enemies;

    public Wave() {
        this.enemies = new ArrayList<>();
    }

    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    public void removeEnemies(Enemy enemy) {
        this.enemies.remove(enemy);
    }

    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

}
