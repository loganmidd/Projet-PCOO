package com.github.loganmidd.effects;

import com.github.loganmidd.entity.Player;

public interface PlayerEffect {
    public String getName();
    public String getDescription();
    public void applyEffect(Player player);

}
