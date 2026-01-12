package com.github.loganmidd.waves;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.github.loganmidd.entity.enemies.Enemy;

/**
 * Provides utility methods for loading and saving wave data.
 * <p>
 * This class handles the serialization and deserialization of {@link Wave} objects
 * to and from JSON files. Files are expected to be located in the "waves/"
 * directory relative to the application's local file handle.
 */
public class WaveLoader {

    /**
     * Loads a wave from a JSON file.
     * <p>
     * Reads the specified file, parses it as a list of {@link Enemy} objects,
     * and constructs a new {@link Wave} containing these enemies.
     *
     * @param fileName The name of the wave file to load.
     * @return The loaded {@link Wave} object.
     */
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

    /**
     * Writes a wave to a JSON file.
     * <p>
     * Serializes the list of enemies from the given {@link Wave} object into a
     * JSON string and writes it to the specified file. If the file does not exist,
     * it will be created.
     *
     * @param fileName The name of the wave file to write to.
     * @param wave The {@link Wave} object containing the enemies to save.
     * @throws IOException If an I/O error occurs during file creation or writing.
     */
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
