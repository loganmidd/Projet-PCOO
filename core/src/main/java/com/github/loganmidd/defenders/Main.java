package com.github.loganmidd.defenders;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.loganmidd.Game.ExampleGame;
import com.github.loganmidd.Game.Game;
import com.github.loganmidd.ui.TitleUI;
import com.github.loganmidd.world.World;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private World world;
    private Game game;
    private TitleUI ui;
    
    @Override
    public void create() {
        this.game = new ExampleGame();
        this.ui = new TitleUI(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                initWorld();
                return true;
            } 
        });
        
    }

    public void initWorld() {
        this.world = World.getWorld();
        this.world.loadTiledMap(this.game.getMapPath());
        this.ui.getStage().dispose();
        this.ui = null;
        this.world.setGame(this.game);
    }

    @Override
    public void resize(int width, int height) {
        if (this.world != null) {
            this.world.resize(width, height);
        } else if (this.ui != null) { 
            this.ui.resize(width, height);
        }
        
    }

    public void input() {
       this.world.input();
    }

    public void logic() { 
        this.world.logic();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        if (this.world != null) {
            this.input();
            this.logic();
            this.world.render();
        }

        if (this.ui != null) {
            this.ui.render();
        }
    }   

    @Override
    public void dispose() {
        if (this.world != null) {
            this.world.dispose();
        } else {
            this.ui.getStage().dispose();
        }
    }
}
