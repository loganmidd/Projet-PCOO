package com.github.loganmidd.entity.towers;

import com.github.loganmidd.entity.enemies.Enemy;

public class KnightWall extends Tower {
    public KnightWall(float x, float y) {
        super(x, y);
    }

    public String  getTexturePath() {
        return "knightWall.png";
    }

    public void attack(Enemy enemy) {
        // Nothing, just blocks enemies
    }
}
