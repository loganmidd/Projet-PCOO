package com.github.loganmidd.entity.enemies;

import com.github.loganmidd.entity.Combattant;
import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.utils.CooldownTimer;
import com.github.loganmidd.utils.EnemyPathVertex;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.world.World;

public abstract class Enemy extends Combattant {
    private EnemyPathVertex currentNode;
    private float speed;
    private CooldownTimer attackTimer;
    
    private float attackDistance;

    public Enemy(float x, float y) {
        super(x, y);
        this.speed = 3f;
        this.attackTimer = new CooldownTimer(1000);
        this.attackDistance = 100f; 
    } 

    public Enemy() {
        this(0, 0); 
    }

    public void initEnemyPath() {
        this.currentNode = World.getWorld().getEnemyPaths().getClosestVertex(this.getCenterPoint());
    }

    public void logic() {
        super.logic();
        // If the enemy is not following a non crystal entity, check if it should
        float maxDistance = 200;
        float bestDistance = maxDistance;
        for (Entity e : World.getWorld().getEntities()) {
            if (e.isCombattant() && !e.isEnemy()) {
                Combattant combattant = (Combattant) e;
                if (combattant.isTargetable()) {
                    float distance = e.getCenterPoint().distanceTo(this.getCenterPoint());
                    if (bestDistance > distance) {
                        this.currentNode = new EnemyPathVertex(e.getCenterPoint(), null, false);
                        this.currentNode.setTargetCombattant((Combattant) e);
                        bestDistance = distance;
                    }
                }
            } 
        }
        // If close enough, attack
        if (bestDistance < this.attackDistance && this.attackTimer.isCooldownOver()) {
            Combattant combattant = this.currentNode.getTargetCombattant();
            this.attack(combattant);
            this.attackTimer.resetCooldown();
        }

        // If following an entity
        if (maxDistance - bestDistance < 0.1  && this.currentNode.isEntityNode() && !this.currentNode.isCrystal()) {
            this.currentNode = World.getWorld().getEnemyPaths().getClosestVertex(this.getCenterPoint());
        }

        // To follow predefined enemy path
        if (this.getCenterPoint().distanceTo(this.currentNode.getPoint()) < 10 && this.currentNode.getNext() != null) {
            this.currentNode = this.currentNode.getNext();
        }
        Point p = this.currentNode.getPoint();
        float x = p.getX();
        float y = p.getY();
        float angle = (float) Math.atan2(this.getCenterY() - y, this.getCenterX() - x);
        float distance = this.getCenterPoint().distanceTo(this.currentNode.getPoint());
        float dr = Math.min(this.speed, distance);
        this.setDx((float) (-dr * Math.cos(angle)));
        this.setDy((float) (-dr * Math.sin(angle))); 
    }

    public void attack(Combattant combattant) {
        combattant.takeDamage(this);
    }

    public boolean isEnemy() {
        return true;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAttackDistance() {
        return attackDistance;
    }

    public void setAttackDistance(float attackDistance) {
        this.attackDistance = attackDistance;
    }
}
