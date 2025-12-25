package com.github.loganmidd.entity.towers;

import com.github.loganmidd.entity.Combattant;
import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.entity.enemies.Enemy;
import com.github.loganmidd.utils.CooldownTimer;
import com.github.loganmidd.world.World;

public abstract class Tower extends Combattant {
    private CooldownTimer timer;
    private float maxAttackDistance;
    private boolean facesEnemy;
    private float startingAngle;
    private float currentAngle;
    private float spanAngle;

    public Tower(float x, float y) {
        super(x, y);
        this.setSlowDownFactor(0);
        this.setCenterX(x);
        this.setCenterY(y);
        this.setMovable(false);
        this.facesEnemy = true;

        this.spanAngle = (float) Math.PI/2f;
        this.currentAngle = 0f;
        this.startingAngle = 0f;

        this.timer = new CooldownTimer(2000);
        this.maxAttackDistance = 320f;
        
    }


    public void setMaxAttackDistance(float maxAttackDistance) {
        this.maxAttackDistance = maxAttackDistance;
    }

    public void setCooldown(long cooldown) {
        this.timer.setCooldownLength(cooldown);
    } 

    public float getMaxAttackDistance() {
        return this.maxAttackDistance;
    }

    public long getCooldown() {
        return this.timer.getCooldownLength();
    }

    public float getStartingAngle() {
        return startingAngle;
    }


    public void setStartingAngle(float startingAngle) {
        this.startingAngle = startingAngle;
    }


    public float getSpanAngle() {
        return spanAngle;
    }


    public void setSpanAngle(float spanAngle) {
        this.spanAngle = spanAngle;
    }

    public float getCurrentAngle() {
        return this.currentAngle;
    }

    public void setCurrentAngle(float angle) {
        this.currentAngle = angle;
        this.getTextureRenderer().setRotation((float) (180/Math.PI * angle - 90));
    }


    public boolean isTower() {
        return true;
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
        float angle = (float) Math.atan2(enemy.getCenterY() - this.getCenterY(), enemy.getCenterX() - this.getCenterX());
        
        return enemy.getCenterPoint().distance2To(this.getCenterPoint()) < this.maxAttackDistance * this.maxAttackDistance
            && Math.abs(angle - this.startingAngle) < this.spanAngle / 2f;
    }

    @Override
    public void logic() {
        super.logic();

        Enemy enemy = this.getClosestEnemy();

        if (enemy != null && this.canSee(enemy)) {
            if (this.timer.isCooldownOver()) {
                this.attack(enemy);
                this.timer.resetCooldown();
            }

            if (this.facesEnemy) {
                float angle = (float) Math.atan2(enemy.getCenterY() - this.getCenterY(), enemy.getCenterX() - this.getCenterX());
                this.turnTo(angle);
            }
        }
    }

    public void turnTo(float angle) {

        this.setCurrentAngle(angle);
    
    }
}
