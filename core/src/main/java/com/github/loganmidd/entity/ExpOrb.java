package com.github.loganmidd.entity;

import com.github.loganmidd.utils.Point;
import com.github.loganmidd.world.World;

public class ExpOrb extends Entity {
    public ExpOrb(float x, float y) {
        super(x, y);
        this.setWidth(20);
        this.setHeight(20);
        this.setCollision(false);
    }

    public void logic() {
        super.logic();

        for (Entity entity : World.getWorld().getEntities()) {
            if (entity.isPlayer()) {
                Player player = (Player) entity;
                Point p1 = player.getCenterPoint();
                Point p2 = this.getCenterPoint();
                
                if (this.collidesWith(player)) {
                    player.addExp(1);
                    this.dispose();
                } else {
                    float angle = (float) Math.atan2(p1.getY() - p2.getY(), p1.getX() - p2.getX());
                    float speed = this.getSpeedToPlayer(player);
                    this.addDx(speed * (float) Math.cos(angle));
                    this.addDy(speed * (float) Math.sin(angle));
                }


            }
        }
    }

    public float getSpeedToPlayer(Player player) {
        float distance = player.getCenterPoint().distance2To(this.getCenterPoint());
        double speed = 20*Math.exp(-distance*distance/(20000*20000));
        return (float) speed;
        
    }

    public String getTexturePath() {
        return "spriteNotFound.png";
    }
}
