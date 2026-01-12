package com.github.loganmidd.entity.playertypes;

import com.github.loganmidd.entity.Player;
import com.github.loganmidd.entity.projectiles.KnightSword;
import com.github.loganmidd.entity.towers.HarpoonTower;
import com.github.loganmidd.entity.towers.KnightWall;
import com.github.loganmidd.entity.towers.Tower;
import com.github.loganmidd.world.World;

/**
 * Represents the Knight player type. The Knight utilizes a sword for primary attacks and sets specific dimensions for the player entity.
 * <p>
 * This class is responsible for handling the Knight's specific attack mechanics, tower types, and texture path.
 * </p>
 *
 * @author Logan Middendorf
 */
public class Knight implements PlayerType {
    /**
     * The Player instance hosting this Knight type.
     */
    private Player host;

    /**
     * The active sword entity used for primary attacks.
     */
    private KnightSword sword;

    /**
     * Constructs a Knight for the specified host Player.
     * <p>
     * Initializes the Knight and sets the host's width and height based on a scaling factor.
     * </p>
     *
     * @param host The Player hosting this Knight type
     */
    public Knight(Player host) {
        this.host = host;
        float factor = 2.5f;
        this.host.setHeight(factor*44);
        this.host.setWidth(factor*48);
    }

    /**
     * Performs the primary attack using a sword.
     * <p>
     * Creates a new sword if the current one is null or disposed. The sword's attack damage is scaled by the host's current level.
     * </p>
     */
    public void primaryAttack() {
        // SWORD
        if (this.sword == null || this.sword.isDisposed()) {
            KnightSword sword = new KnightSword(this.host);
            this.sword = sword;
            this.sword.setAttackDamage(this.sword.getAttackDamage() * this.host.getLevel());
            World.getWorld().addEntity(sword);
        }
    }

    /**
     * Initiates the secondary attack action (Shield Up).
     */
    public void startSecondaryAttack() {
        // Unfinished :(
    }

    /**
     * Ends the secondary attack action (Shield Down).
     */
    public void endSecondaryAttack() {
       // Unfinished :(
    }

    /**
     * Retrieves the primary tower type for the Knight.
     *
     * @return A new instance of KnightWall at position (0,0)
     */
    public Tower getPrimaryTower() {
        return new KnightWall(0, 0);
    }

    /**
     * Retrieves the secondary tower type for the Knight.
     *
     * @return A new instance of HarpoonTower at position (0,0)
     */
    public Tower getSecondaryTower() {
        return new HarpoonTower(0, 0);
    }

    /**
     * Retrieves the texture path for the Knight.
     *
     * @return The file path to the Knight's texture ("knight.png")
     */
    public String getTexturePath() {
        return "knight.png";
    }
}