package com.github.loganmidd.effects;

import com.github.loganmidd.entity.Player;

public class AddAttackDamageUpgrade implements PlayerEffect {

    @Override
    public String getName() {
        return "Attack Up";
    }

    @Override
    public String getDescription() {
        return "Adds +5 damage to player attacks.";
    }

    @Override
    public void applyEffect(Player player) {
        player.setAttackDamage(player.getAttackDamage() + 5);
    }
    
}
