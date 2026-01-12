package com.github.loganmidd.ui;

import com.badlogic.gdx.utils.Disposable;

public interface UI extends Disposable {
    public void render();
    public void resize(int width, int height);
}
