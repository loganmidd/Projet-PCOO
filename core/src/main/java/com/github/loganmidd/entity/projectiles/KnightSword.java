package com.github.loganmidd.entity.projectiles;

import com.github.loganmidd.entity.Combattant;
import com.github.loganmidd.entity.Entity;

public class KnightSword extends Projectile {
    private float current;
    private float start;
    private Entity host;
    private float distanceToHost;

    // For this projectile, we will not follow
    // a strict line

    public KnightSword(Entity host) {
        super(host, host.getCenterX(), host.getCenterY(), 0, 0);
        this.setSlowDownFactor(0);
        float factor = 2f;
        this.setWidth(17*factor);
        this.setHeight(45*factor);

        this.host = host;
        this.distanceToHost = 50f;
        this.start = (float) Math.atan2(this.host.getDy(), this.host.getDx());
        this.current = -45f;
    }

    public void logic() {
        super.logic();
        // Update rotation
        if (current > 45) {
            this.dispose();
        }

        this.current += 5;
        this.getTextureRenderer().setRotation(180*this.start/((float) Math.PI) + this.current - 90);
    }

    protected float calculateX(int t) {
        float x = this.host.getX() + this.host.getWidth()/4f;
        return x + distanceToHost*((float) Math.cos(this.start + this.current * (float)Math.PI/180));
    }

    protected float calculateY(int t) {
        float y = this.host.getY() + this.host.getHeight()/4f;
        return y + distanceToHost*((float) Math.sin(this.start + this.current * (float)Math.PI/180));
    }

    public boolean collisionWith(Combattant combattant) {
        if (combattant.isEnemy()) {
            combattant.takeDamage(this);;
            this.dispose();
            return true;
        }
        return false;
    }

    public String getTexturePath() {
        return "sword.png";
    }

}
