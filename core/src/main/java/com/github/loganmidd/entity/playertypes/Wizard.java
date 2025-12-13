package com.github.loganmidd.entity.playertypes;

import com.badlogic.gdx.utils.TimeUtils;
import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.entity.projectiles.WizardProjectile;
import com.github.loganmidd.world.World;

public class Wizard implements PlayerType {
    private Entity host;
    private long startTime;

    public Wizard(Entity host) {
        this.host = host;
        float factor = 3f;
        this.host.setHeight(42*factor);
        this.host.setWidth(26*factor);
    }

    public void primaryAttack() {
        float angle =  (float) Math.atan2(this.host.getDy(), this.host.getDx());
        WizardProjectile proj = new WizardProjectile(this.host.getCenterX(), this.host.getCenterY(), angle);
        World.getWorld().addEntity(proj);
    }

    public void startSecondaryAttack() {
        this.startTime = TimeUtils.millis();
    }

    public void endSecondaryAttack() {
        float difference = (float) TimeUtils.timeSinceMillis(this.startTime) / 1000f;
        difference = Math.min(difference, 2); // 2 seconds max charge
        int numProjectiles = ((int) Math.floor(8 * difference)) + 1;
        float hostAngle =  (float) Math.atan2(this.host.getDy(), this.host.getDx());
        float totalAngle = (float) Math.PI / 4f;
        if (numProjectiles % 2 == 1) {
            WizardProjectile proj = new WizardProjectile(this.host.getCenterX(), this.host.getCenterY(), hostAngle);
            World.getWorld().addEntity(proj);
            numProjectiles--;
        } 

        for (int i = 0; i<numProjectiles; i++) {
            float angle = hostAngle + (totalAngle)*((float) i)/numProjectiles;
            WizardProjectile proj = new WizardProjectile(this.host.getCenterX(), this.host.getCenterY(), angle);
            World.getWorld().addEntity(proj);
        }
        
    }


    public String getTexturePath () {
        return "wizard.png";
    }

}
