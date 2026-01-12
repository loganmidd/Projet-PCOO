package com.github.loganmidd.entity.playertypes;

import com.badlogic.gdx.utils.TimeUtils;
import com.github.loganmidd.entity.Player;
import com.github.loganmidd.entity.projectiles.WizardProjectile;
import com.github.loganmidd.entity.towers.DeadlyStrikeTower;
import com.github.loganmidd.entity.towers.Tower;
import com.github.loganmidd.entity.towers.WizardBlueTower;
import com.github.loganmidd.world.World;

public class Wizard implements PlayerType {
    private Player host;
    private long startTime;

    public Wizard(Player host) {
        this.host = host;
        float factor = 3f;
        this.host.setHeight(42*factor);
        this.host.setWidth(26*factor);
    }

    public void primaryAttack() {
        float angle =  (float) Math.atan2(this.host.getDy(), this.host.getDx());
        WizardProjectile proj = new WizardProjectile(this.host, this.host.getCenterX(), this.host.getCenterY(), angle);
        proj.setAttackDamage(this.host.getAttackDamage() * this.host.getLevel());        
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
            WizardProjectile proj = new WizardProjectile(this.host, this.host.getCenterX(), this.host.getCenterY(), hostAngle);
            proj.setAttackDamage(this.host.getAttackDamage() * this.host.getLevel());
            World.getWorld().addEntity(proj);
            numProjectiles--;
        } 

        for (int i = 0; i<numProjectiles; i++) {
            float angle = hostAngle + (totalAngle)*((float) i)/numProjectiles;
            WizardProjectile proj = new WizardProjectile(this.host, this.host.getCenterX(), this.host.getCenterY(), angle);
            proj.setAttackDamage(this.host.getAttackDamage() * this.host.getLevel());
            World.getWorld().addEntity(proj);
        }
        
    }


    public String getTexturePath () {
        return "wizard.png";
    }

    public Tower getPrimaryTower() {
        return new WizardBlueTower(0, 0);
    }

    public Tower getSecondaryTower() {
        return new DeadlyStrikeTower(0, 0);

    }

}
