package com.github.loganmidd.utils;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * A utility class for managing cooldown timers.
 */
public class CooldownTimer {
    /** The duration of the cooldown in milliseconds. */
    long cooldownLength; // In milliseconds
    /** The timestamp when the cooldown started, in milliseconds. */
    long cooldownStart;
    

    /**
     * Constructs a new CooldownTimer with the specified length and starting state.
     *
     * @param length The duration of the cooldown in milliseconds.
     * @param startWithoutCooldown If true, the timer starts immediately without a cooldown period. If false, the timer starts in a cooled down state.
     */
    public CooldownTimer(long length, boolean startWithoutCooldown) {
        this.cooldownLength = length;
        this.cooldownStart = TimeUtils.millis();
        if (!startWithoutCooldown) {
            this.cooldownStart -= this.cooldownLength;
        }
    }

    /**
     * Constructs a new CooldownTimer with the specified length, starting in a cooled down state.
     *
     * @param length The duration of the cooldown in milliseconds.
     */
    public CooldownTimer(long length) {
        this(length, false);
    }

    /**
     * Constructs a new CooldownTimer with a default duration of 500 milliseconds, starting in a cooled down state.
     */
    public CooldownTimer() {
        this(500);
    }

    /**
     * Checks if the cooldown period has elapsed.
     *
     * @return True if the cooldown is over, false otherwise.
     */
    public boolean isCooldownOver() {
        return this.cooldownStart + this.cooldownLength < TimeUtils.millis();
    }

    /**
     * Resets the cooldown timer to the current time.
     */
    public void resetCooldown() {
        this.cooldownStart = TimeUtils.millis();
    }

    /**
     * Retrieves the current cooldown duration.
     *
     * @return The cooldown length in milliseconds.
     */
    public long getCooldownLength() {
        return this.cooldownLength;
    }

    /**
     * Sets the cooldown duration.
     *
     * @param cooldownLength The new cooldown length in milliseconds.
     */
    public void setCooldownLength(long cooldownLength) {
        this.cooldownLength = cooldownLength;
    }

    
}

