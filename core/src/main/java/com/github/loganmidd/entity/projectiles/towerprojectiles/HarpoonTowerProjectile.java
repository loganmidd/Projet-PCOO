package com.github.loganmidd.entity.projectiles.towerprojectiles;

import com.github.loganmidd.entity.Combattant;
import com.github.loganmidd.entity.projectiles.Projectile;

public class HarpoonTowerProjectile extends Projectile {
    private int pierced;

    public HarpoonTowerProjectile(float x, float y, float angle) {
        super(x, y, angle);
        this.pierced = 0;
    }

    @Override
    public boolean collisionWith(Combattant combattant) {
        if (combattant.isEnemy()) {
            combattant.takeDamage(this);
            this.pierced++;
            if (this.pierced >= 10) {
                this.dispose();
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTexturePath() {
        return "spriteNotFound.png";
    }
    
}
