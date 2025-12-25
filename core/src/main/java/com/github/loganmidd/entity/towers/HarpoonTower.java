package com.github.loganmidd.entity.towers;

import com.github.loganmidd.entity.enemies.Enemy;
import com.github.loganmidd.entity.projectiles.towerprojectiles.HarpoonTowerProjectile;
import com.github.loganmidd.world.World;

public class HarpoonTower extends Tower {

    public HarpoonTower(float x, float y) {
        super(x, y);
    }

    public void attack(Enemy enemy) {
        float angle =  (float) Math.atan2(  enemy.getCenterY() - this.getCenterY(), 
                                            enemy.getCenterX() - this.getCenterX());
        HarpoonTowerProjectile proj = new HarpoonTowerProjectile(this.getX(), this.getY(), angle);

        World.getWorld().addEntity(proj);
    }

    @Override
    public String getTexturePath() {
        return "ballista.png";
    }
    
}
