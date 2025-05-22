package com.tony.photoshader;

import com.kw.gdx.BaseGame;
import com.kw.gdx.constant.Constant;
import com.tony.photoshader.screen.PhotoScreen;

public class PhotoShader extends BaseGame {
    public PhotoShader(){
        Constant.viewColor.set(0.5f,0.5f,0.5f,0);
    }
    @Override
    protected void initScreen() {
        super.initScreen();
        setScreen(PhotoScreen.class);
    }
}
