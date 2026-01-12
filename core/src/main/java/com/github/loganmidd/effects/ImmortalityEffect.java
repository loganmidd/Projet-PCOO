package com.github.loganmidd.effects;

import com.github.loganmidd.entity.Player;

public class ImmortalityEffect implements PlayerEffect {

    @Override
    public String getName() {
        return "Become immortal";
    }

    @Override
    public String getDescription() {
        return "Makes the player immortal. Kind of broken";
    }

    @Override
    public void applyEffect(Player player) {
        player.setImmortal(true);
    }
    
}
