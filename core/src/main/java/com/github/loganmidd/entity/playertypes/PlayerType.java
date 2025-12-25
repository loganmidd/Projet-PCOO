package com.github.loganmidd.entity.playertypes;

import com.github.loganmidd.entity.towers.Tower;

public interface PlayerType {
    public void primaryAttack();
    public void startSecondaryAttack();
    public void endSecondaryAttack();
    public String getTexturePath(); 
    public Tower getPrimaryTower();
    public Tower getSecondaryTower();
}