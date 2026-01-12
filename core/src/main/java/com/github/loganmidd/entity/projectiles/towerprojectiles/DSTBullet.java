package com.github.loganmidd.entity.projectiles.towerprojectiles;

import com.github.loganmidd.entity.Combattant;
import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.entity.projectiles.Projectile;

public class DSTBullet extends Projectile {

    public DSTBullet(Entity sender, float x, float y, float angle) {
        super(sender, x, y, angle, 50);
        this.setWidth(25);
        this.setHeight(61);
    }

    @Override
    public boolean collisionWith(Combattant combattant) {
        if (combattant.isEnemy()) {
            combattant.takeDamage(this);
            this.dispose();
            return true;
        }
        return false;
    }

    @Override
    public String getTexturePath() {
        return "dstBullet.png";
    }
    
}
