package com.github.loganmidd.entity.enemies;

import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.entity.Player;
import com.github.loganmidd.utils.EnemyPathVertex;
import com.github.loganmidd.utils.Point;
import com.github.loganmidd.world.World;

public abstract class Enemy extends Entity {
    private EnemyPathVertex currentNode;
    private float speed;
    public Enemy(float x, float y) {
        super(x, y);
        this.speed = 3f; // Default
        this.currentNode = World.getWorld().getEnemyPaths().getClosestVertex(this.getCenterPoint());
    }

    public void logic() {
        super.logic();
        // If the enemy is not following a non crystal entity, check if it should
        float maxDistance = 200;
        float bestDistance = maxDistance;
        for (Entity e : World.getWorld().getEntities()) {
            if (e.getClass().equals(Player.class)) {
                float distance = e.getCenterPoint().distanceTo(this.getCenterPoint());
                if (bestDistance > distance) {
                    this.currentNode = new EnemyPathVertex(e.getCenterPoint(), null, false);
                    bestDistance = distance;
                }
            } 
        }
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
        float dr = Math.min(this.speed, this.getCenterPoint().distanceTo(this.currentNode.getPoint()));
        this.setDx((float) (-dr * Math.cos(angle)));
        this.setDy((float) (-dr * Math.sin(angle))); 
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
    
}
