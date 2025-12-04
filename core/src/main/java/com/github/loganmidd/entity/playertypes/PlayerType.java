package com.github.loganmidd.entity.playertypes;

public interface PlayerType {
    public void primaryAttack();
    public void startSecondaryAttack();
    public void endSecondaryAttack();
    public String getTexturePath(); 
}