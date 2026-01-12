package com.github.loganmidd.effects;

import com.github.loganmidd.entity.Player;

/**
 * A player effect that increases the player's attack damage.
 * <p>
 * This implementation adds a fixed amount of damage to the player's current attack damage value
 * when applied.
 */
public class AddAttackDamageUpgrade implements PlayerEffect {

    /**
     * Returns the name of this attack damage upgrade effect.
     *
     * @return The name of the effect.
     */
    @Override
    public String getName() {
        return "Attack Up";
    }

    /**
     * Returns a description of this attack damage upgrade effect.
     *
     * @return The description of the effect.
     */
    @Override
    public String getDescription() {
        return "Adds +5 damage to player attacks.";
    }

    /**
     * Applies the attack damage upgrade to the specified player.
     * <p>
     * This method increases the player's attack damage by 5 points.
     *
     * @param player The player to apply the effect to.
     */
    @Override
    public void applyEffect(Player player) {
        player.setAttackDamage(player.getAttackDamage() + 5);
    }
    
}
