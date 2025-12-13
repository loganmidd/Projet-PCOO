package com.github.loganmidd.entity.projectiles;

import com.github.loganmidd.entity.Entity;

public class WizardProjectile extends Projectile {
    private float angle;

    public WizardProjectile(float x, float y, float angle) {
        super(x, y, angle, 10);
        this.angle = 0;
    }

    @Override
    public void logic() {
        super.logic();
        this.getTextureRenderer().setRotation(this.angle);
        this.angle += 20;
    }

    public String getTexturePath() {
        return "fireball.png";
    }

    public boolean collisionWith(Entity entity) {
        if (entity.isEnemy()) {
            entity.dispose();
            this.dispose();
            return true;
        }
        return false;
    }
    
}
