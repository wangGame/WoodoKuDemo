package com.tony.photoshader.screen;

import com.kw.gdx.BaseGame;
import com.kw.gdx.screen.BaseScreen;
import com.tony.photoshader.ai.Blokie;

public class PhotoScreen extends BaseScreen {

    public PhotoScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void initView() {
        super.initView();
        Blokie blokie = new Blokie();
        blokie.runBasicTests();
    }
}
