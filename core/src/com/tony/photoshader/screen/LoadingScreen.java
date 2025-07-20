package com.tony.photoshader.screen;

import com.kw.gdx.BaseGame;
import com.kw.gdx.resource.annotation.ScreenResource;
import com.kw.gdx.screen.BaseScreen;

/**
 * Author by tony
 * Date on 2025/7/19.
 */
@ScreenResource("cocos/LoadingScreen.json")
public class LoadingScreen extends BaseScreen {
    private float time = 0;

    public LoadingScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        time += delta;
        if (time > 0) {
            time = -Integer.MIN_VALUE;
            setScreen(WoodokuScreen.class);
        }
    }
}
