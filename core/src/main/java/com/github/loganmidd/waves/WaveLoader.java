package com.github.loganmidd.waves;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.github.loganmidd.entity.enemies.Enemy;

public class WaveLoader {
    public static Wave load(String fileName) {
        FileHandle handle = Gdx.files.local("waves/" + fileName);
        String enemyString = handle.readString();
        Json json = new Json();
        Wave wave = new Wave();
        for (Object object : json.fromJson(ArrayList.class, enemyString)) {
            Enemy enemy = (Enemy) object;
            wave.addEnemy(enemy);
        }
        return wave;
    }

    public static void write(String fileName, Wave wave) throws IOException {
        Json json = new Json();
        FileHandle handle = Gdx.files.local("waves/" + fileName);
        if (!handle.exists()) {
            new File(fileName).createNewFile();
        }
        String jsonString = json.prettyPrint(wave.getEnemies());
        handle.writeString(jsonString, false);
    }
}