package com.github.loganmidd.entity.playertypes;

import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.entity.projectiles.KnightSword;
import com.github.loganmidd.entity.towers.HarpoonTower;
import com.github.loganmidd.entity.towers.KnightWall;
import com.github.loganmidd.entity.towers.Tower;
import com.github.loganmidd.world.World;

public class Knight implements PlayerType {
    private Entity host;
    private KnightSword sword;

    public Knight(Entity host) {
        this.host = host;
        float factor = 2.5f;
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

    public Tower getPrimaryTower() {
        return new KnightWall(0, 0);
    }

    public Tower getSecondaryTower() {
        return new HarpoonTower(0, 0);
    }


    public String getTexturePath() {
        return "knight.png";
    }
}
