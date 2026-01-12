package com.github.loganmidd.effects;

import com.github.loganmidd.entity.Combattant;
import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.entity.Player;
import com.github.loganmidd.world.World;

public class HealToMaxHealth implements PlayerEffect {

    @Override
    public String getName() {
        return "Full Heal";    
    }

    @Override
    public String getDescription() {
        return "Fully heals the player, crystals and towers.";
    }

    @Override
    public void applyEffect(Player player) {
        for (Entity entity : World.getWorld().getEntities()) {
            if (entity.isCombattant()) {
                if (entity.isTower() || entity.isPlayer() || entity.isCrystal()) {
                    Combattant combattant = (Combattant) entity;
                    combattant.setCurrentHealth(combattant.getMaxHealth());
                }
            }
         }
    } 
    
}
