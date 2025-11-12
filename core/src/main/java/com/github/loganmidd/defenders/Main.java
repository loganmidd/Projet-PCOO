package com.github.loganmidd.defenders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.loganmidd.entity.Player;
import com.github.loganmidd.structures.Block;
import com.github.loganmidd.world.World;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private World world;
    
    @Override
    public void create() {
        this.world = World.getWorld();
        this.world.loadTiledMap("/home/Partage/L2/PCOO/projet/tiled/minimal.tmx");
        // Platforms for testing purposes
        this.world.addBlock(new Block(200, -100, 25, 200));
        this.world.addBlock(new Block(400, -300, 25, 200));        
        this.world.addBlock(new Block(200, -500, 100, 100));
        // Player for testing purposes
        this.world.addEntity(new Player(-200, 100), "pitrouille.png");
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
