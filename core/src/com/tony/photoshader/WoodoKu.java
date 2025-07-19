package com.tony.photoshader;

import com.kw.gdx.BaseGame;
import com.kw.gdx.constant.Constant;
import com.tony.photoshader.screen.LoadingScreen;
import com.tony.photoshader.screen.WoodokuScreen;

public class WoodoKu extends BaseGame {
    public WoodoKu(){
        Constant.viewColor.set(0.5f,0.5f,0.5f,0);
    }

    @Override
    protected void initScreen() {
        super.initScreen();
        setScreen(LoadingScreen.class);
    }
}
