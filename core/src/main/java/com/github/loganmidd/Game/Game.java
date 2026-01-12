package com.github.loganmidd.Game;

import java.util.Map;

import com.github.loganmidd.effects.PlayerEffect;
import com.github.loganmidd.waves.Wave;

public interface Game {
    public Wave getNthWave(int n);
    public String getMapPath();
    public Map<PlayerEffect, Float> getPlayerEffects();
}
