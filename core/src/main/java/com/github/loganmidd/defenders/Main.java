package com.github.loganmidd.defenders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.loganmidd.entity.Entity;
import com.github.loganmidd.entity.Player;
import com.github.loganmidd.structures.Block;
import com.github.loganmidd.world.World;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private World world;
    private SpriteBatch batch;
    private OrthographicCamera cam;
    private static float zoomFactor = 1000f; // Number is arbitrary 
    
    @Override
    public void create() {
        this.batch = new SpriteBatch();
        // Initial Camera
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        this.cam = new OrthographicCamera(zoomFactor, zoomFactor*(h/w));
        this.cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		this.cam.update();

        this.world = new World(this.cam);

        // Platforms for testing purposes
        this.world.addBlock(new Block(200, 100, 25, 200));
        this.world.addBlock(new Block(400, 300, 25, 200));
        this.world.addBlock(new Block(200, -200, 100, 100));
        // Player for testing purposes
        this.world.addEntity(new Player(100, 100));
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = zoomFactor;
        cam.viewportHeight = zoomFactor * height/width;
        cam.update();
    }

    public void input() {
        this.world.input();
    }

    public void logic() { 
        this.world.logic();
        // Center camera on player (currently only entity)
        Entity e = this.world.getEntities().get(0);
        this.cam.position.x = e.getX() + e.getWidth() / 2;
        this.cam.position.y = e.getY() + e.getWidth() / 2;
    }

    @Override
    public void render() {
        this.input();
        this.logic();

        this.cam.update();
        this.batch.setProjectionMatrix(this.cam.combined);
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        this.batch.begin();
        this.world.render();
        this.batch.end();
    }   

    @Override
    public void dispose() {
        this.batch.dispose();
    }
}
