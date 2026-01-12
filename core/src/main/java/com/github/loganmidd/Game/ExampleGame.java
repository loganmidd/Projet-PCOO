package com.github.loganmidd.Game;

import java.util.HashMap;
import java.util.Map;

import com.github.loganmidd.effects.AddAttackDamageUpgrade;
import com.github.loganmidd.effects.HealToMaxHealth;
import com.github.loganmidd.effects.ImmortalityEffect;
import com.github.loganmidd.effects.PlayerEffect;
import com.github.loganmidd.entity.enemies.Goblin;
import com.github.loganmidd.waves.Wave;
import com.github.loganmidd.waves.WaveLoader;

public class ExampleGame implements Game {
    @Override
    public Wave getNthWave(int n) {
        if (n == 0) {
            return WaveLoader.load("wave.json");
        }
        Wave wave = new Wave();
        for (int i=1; i<Math.pow(2, n+1); i++) {
            wave.addEnemy(new Goblin());
        }
        return wave;
    }

    @Override
    public String getMapPath() {
        return "tiled/example.tmx";
    }

    @Override
    public Map<PlayerEffect, Float> getPlayerEffects() {
        HashMap<PlayerEffect, Float> map = new HashMap<>();
        map.put(new AddAttackDamageUpgrade(), 5f);
        map.put(new HealToMaxHealth(), 4f);
        map.put(new ImmortalityEffect(), 1f);
        return map;
    }
}
