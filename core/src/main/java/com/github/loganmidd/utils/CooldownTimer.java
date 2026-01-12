package com.github.loganmidd.utils;

import com.badlogic.gdx.utils.TimeUtils;

public class CooldownTimer {
    long cooldownLength; // In milliseconds
    long cooldownStart;
    

    public CooldownTimer(long length, boolean startWithoutCooldown) {
        this.cooldownLength = length;
        this.cooldownStart = TimeUtils.millis();
        if (!startWithoutCooldown) {
            this.cooldownStart -= this.cooldownLength;
        }
    }

    public CooldownTimer(long length) {
        this(length, false);
    }

    public CooldownTimer() {
        this(500);
    }

    public boolean isCooldownOver() {
        return this.cooldownStart + this.cooldownLength < TimeUtils.millis();
    }

    public void resetCooldown() {
        this.cooldownStart = TimeUtils.millis();
    }

    public long getCooldownLength() {
        return this.cooldownLength;
    }

    public void setCooldownLength(long cooldownLength) {
        this.cooldownLength = cooldownLength;
    }

    
}
