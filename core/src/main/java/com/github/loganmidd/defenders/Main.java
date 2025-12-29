package com.github.loganmidd.defenders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.loganmidd.world.World;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private World world;
    
    @Override
    public void create() {
        this.world = World.getWorld();
        this.world.loadTiledMap("/home/Partage/L2/PCOO/projet/tiled/test.tmx"); // Manual for now...
    }

    @Override
    public void resize(int width, int height) {
        this.world.resize(width, height);
    }

    public void input() {
        this.world.input();
    }

    public void logic() { 
        this.world.logic();
    }

    @Override
    public void render() {
        this.input();
        this.logic();

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        
        this.world.render();
    }   

    @Override
    public void dispose() {
        this.world.dispose();
    }
}
