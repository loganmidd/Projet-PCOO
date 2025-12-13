package com.github.loganmidd.entity.playertypes;

import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.entity.projectiles.KnightSword;
import com.github.loganmidd.world.World;

public class Knight implements PlayerType {
    private Entity host;
    private KnightSword sword;

    public Knight(Entity host) {
        this.host = host;
        float factor = 3f;
        this.host.setHeight(factor*44);
        this.host.setWidth(factor*48);
    }

    public void primaryAttack() {
        // SWORD
        if (this.sword == null || this.sword.isDisposed()) {
            KnightSword sword = new KnightSword(this.host);
            this.sword = sword;
            World.getWorld().addEntity(sword);
        }
    }

    public void startSecondaryAttack() {
        // SHIELD UP
    }

    public void endSecondaryAttack() {
        // SHIELD DOWN
    }


    public String getTexturePath() {
        return "knight.png";
    }
}
