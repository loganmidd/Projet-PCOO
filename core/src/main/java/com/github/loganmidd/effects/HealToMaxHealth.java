package com.github.loganmidd.effects;

import com.github.loganmidd.entity.Combattant;
import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.entity.Player;
import com.github.loganmidd.world.World;

/**
 * Represents a player effect that restores all combatants (player, towers, and crystals) to their maximum health.
 * <p>
 * This effect iterates through all entities in the world and applies a full heal to specific entity types.
 * 
 * @author Logan Middendorf
 */
public class HealToMaxHealth implements PlayerEffect {

    /**
     * Returns the name of the effect.
     *
     * @return The name "Full Heal".
     */
    @Override
    public String getName() {
        return "Full Heal";    
    }

    /**
     * Returns the description of the effect.
     *
     * @return The description describing which entities are healed.
     */
    @Override
    public String getDescription() {
        return "Fully heals the player, crystals and towers.";
    }

    /**
     * Applies the effect to the player and relevant entities in the world.
     * <p>
     * Iterates through all entities in the world. If an entity is a combattant and is a tower, player, or crystal,
     * its current health is set to its maximum health.
     *
     * @param player The player triggering the effect.
     */
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