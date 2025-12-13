package com.github.loganmidd.entity.towers;

import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.entity.enemies.Enemy;
import com.github.loganmidd.utils.CooldownTimer;
import com.github.loganmidd.world.World;

public abstract class Tower extends Entity {
    private CooldownTimer timer;
    private float maxAttackDistance;

    public Tower(float x, float y) {
        super(x, y);
        this.setSlowDownFactor(0);
        this.setCenterX(x);
        this.setCenterY(y);
        this.setMovable(false);

        this.timer = new CooldownTimer(2000);
        this.maxAttackDistance = 320f;
        
    }

    public boolean isEnemy() {
        return false;
    }

    public Enemy getClosestEnemy() { 
        Enemy closest = null;
        float closestDistance = Float.POSITIVE_INFINITY;

        for (Entity entity : World.getWorld().getEntities()) {
            if (entity.isEnemy()) {
                Enemy enemy = (Enemy) entity;
                float distance = enemy.getCenterPoint().distance2To(this.getCenterPoint());
                if (distance < closestDistance) {
                    closest = enemy;
                    closestDistance = distance;
                }
            }
        }
        return closest;
    }

    public abstract void attack(Enemy enemy);
    
    public boolean canSee(Enemy enemy) {
        return enemy.getCenterPoint().distance2To(this.getCenterPoint()) < this.maxAttackDistance * this.maxAttackDistance;
    }

    @Override
    public void logic() {
        super.logic();

        if (this.timer.isCooldownOver()) {
            Enemy enemy = this.getClosestEnemy();
            if (enemy != null && this.canSee(enemy)) {
                this.attack(enemy);
                this.timer.resetCooldown();
            }
        }
        

    }
}
