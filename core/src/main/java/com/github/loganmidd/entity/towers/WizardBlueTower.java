package com.github.loganmidd.entity.towers;

import com.github.loganmidd.entity.enemies.Enemy;
import com.github.loganmidd.entity.projectiles.WizardProjectile;
import com.github.loganmidd.world.World;

public class WizardBlueTower extends Tower {
    public WizardBlueTower(float x, float y) {
        super(x, y);
    }

    public String getTexturePath() {
        return "blueWizardTower.png";
    }

    public void attack(Enemy target) {
        float angle =  (float) Math.atan2(target.getCenterY() - this.getCenterY(), target.getCenterX() - this.getCenterX());
        WizardProjectile proj = new WizardProjectile(this, this.getX(), this.getY(), angle);
        World.getWorld().addEntity(proj);
        
    }
}
