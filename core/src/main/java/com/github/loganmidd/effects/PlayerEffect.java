package com.github.loganmidd.effects;

import com.github.loganmidd.entity.Player;

/**
 * Represents an effect that can be applied to a player.
 * <p>
 * Implementations of this interface define specific behaviors, such as
 * altering player stats or applying temporary states.
 */
public interface PlayerEffect {

    /**
     * Retrieves the name of the effect.
     *
     * @return The name of the effect.
     */
    public String getName();

    /**
     * Retrieves the description of the effect.
     *
     * @return A string describing the effect.
     */
    public String getDescription();

    /**
     * Applies the effect to the specified player.
     *
     * @param player The player to apply the effect to.
     */
    public void applyEffect(Player player);

}