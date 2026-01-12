package com.github.loganmidd.entity.towers;

import com.github.loganmidd.entity.enemies.Enemy;
import com.github.loganmidd.entity.projectiles.towerprojectiles.DSTBullet;
import com.github.loganmidd.world.World;

public class DeadlyStrikeTower extends Tower {
    public DeadlyStrikeTower(float x, float y) {
        super(x, y);
        this.setMaxAttackDistance(50000);
        this.setCooldown(5000);
        
    }

    public String getTexturePath() {
        return "dst.png";
    }

    public void attack(Enemy target) {
        float angle =  (float) Math.atan2(  target.getCenterY() - this.getCenterY(), 
                                            target.getCenterX() - this.getCenterX());
        DSTBullet proj = new DSTBullet(this, this.getX(), this.getY(), angle);

        World.getWorld().addEntity(proj);
        
    }
}