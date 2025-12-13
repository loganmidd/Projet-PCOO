package com.github.loganmidd.entity.enemies;

public class Goblin extends Enemy {
    public Goblin(float x, float y) {
        super(x, y);
        this.setSpeed(2f);
    }

    public String getTexturePath() {
        return "goblin.png";
    }
}
