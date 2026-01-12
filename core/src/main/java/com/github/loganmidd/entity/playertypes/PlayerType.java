package com.github.loganmidd.entity.playertypes;

import com.github.loganmidd.entity.towers.Tower;

/**
 * Defines the behavior and characteristics for different player types within the game.
 * <p>
 * Implementations of this interface represent distinct player archetypes, dictating their
 * combat mechanics (attacks) and associated towers.
 *
 * @author Logan Middendorf
 */
public interface PlayerType {
    /**
     * Performs the primary attack action for the player type.
     * <p>
     * This typically represents a standard, frequently usable offensive action.
     */
    public void primaryAttack();

    /**
     * Initiates the secondary attack sequence for the player type.
     * <p>
     * This may begin a charging phase or prepare a special ability.
     */
    public void startSecondaryAttack();

    /**
     * Concludes the secondary attack sequence for the player type.
     * <p>
     * This finalizes the secondary action initiated by {@link #startSecondaryAttack()}.
     */
    public void endSecondaryAttack();

    /**
     * Retrieves the file path to the texture asset associated with this player type.
     *
     * @return The path string to the player's texture image.
     */
    public String getTexturePath();

    /**
     * Retrieves the primary tower associated with this player type.
     *
     * @return The {@link Tower} instance representing the primary tower.
     */
    public Tower getPrimaryTower();

    /**
     * Retrieves the secondary tower associated with this player type.
     *
     * @return The {@link Tower} instance representing the secondary tower.
     */
    public Tower getSecondaryTower();
}
