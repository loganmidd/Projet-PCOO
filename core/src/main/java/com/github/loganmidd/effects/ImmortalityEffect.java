package com.github.loganmidd.effects;

import com.github.loganmidd.entity.Player;

/**
 * Represents an effect that renders a Player immortal.
 * <p>
 * This class implements the {@link PlayerEffect} interface to provide
 * functionality for making a player invulnerable to damage.
 * 
 * @author Logan Middendorf
 */
public class ImmortalityEffect implements PlayerEffect {

    /**
     * Retrieves the display name of this effect.
     *
     * @return The name of the effect: "Become immortal".
     */
    @Override
    public String getName() {
        return "Become immortal";
    }

    /**
     * Retrieves the description of this effect.
     *
     * @return The description of the effect: "Makes the player immortal. Kind of broken".
     */
    @Override
    public String getDescription() {
        return "Makes the player immortal. Kind of broken";
    }

    /**
     * Applies the immortal effect to the specified player.
     * <p>
     * This sets the player's immortal state to true, making them invulnerable.
     *
     * @param player The player to whom the effect is applied.
     */
    @Override
    public void applyEffect(Player player) {
        player.setImmortal(true);
    }
    
}