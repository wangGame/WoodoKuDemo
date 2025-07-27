package com.tony.photoshader.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kw.gdx.BaseGame;
import com.kw.gdx.resource.annotation.ScreenResource;
import com.kw.gdx.screen.BaseScreen;
import com.tony.photoshader.block.BaseBlockActor;
import com.tony.photoshader.block.BlockManager;

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
    public void initView() {
        super.initView();

        addActor(new Table(){{
            for (int i = 0; i < 18; i++) {
                BaseBlockActor baseBlockActor = BlockManager.getBaseBlockActor(i + 1);
                baseBlockActor.createBlock();
                add(baseBlockActor);
                if (i!=0 &&i%5==0){
                    row();
                }
            }
            pack();
        }});
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
