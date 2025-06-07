package com.tony.photoshader.screen;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.BaseGame;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.screen.BaseScreen;
import com.tony.photoshader.block.BaseBlockActor;
import com.tony.photoshader.view.BlockGroup;
import com.tony.photoshader.view.BlockPanel;
import com.tony.photoshader.view.GameView;

public class Woodoku extends BaseScreen {

    public Woodoku(BaseGame game) {
        super(game);
    }

    @Override
    public void initView() {
        super.initView();

        GameView gameView = new GameView();
        addActor(gameView);
        gameView.setPosition(Constant.GAMEWIDTH/2f,Constant.GAMEHIGHT/2f, Align.center);

        BlockPanel blockPanel = new BlockPanel();
        blockPanel.setBlock();
        rootView.addActor(blockPanel);
        gameView.setBlockPanel(blockPanel);

        Actor click = new Actor();
        click.setSize(Constant.GAMEWIDTH,Constant.GAMEHIGHT);
        addActor(click);
        click.addListener(new ClickListener(){
            private Vector2 touchDownV2 = new Vector2();
            private BaseBlockActor targetBlock;
            private Vector2 touchDown = new Vector2();
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean b = super.touchDown(event, x, y, pointer, button);
                touchDownV2.set(x,y);
                click.getParent().localToStageCoordinates(touchDownV2);
                BlockGroup blockGroup = blockPanel.checkTouch(touchDownV2);
                if (blockGroup == null)return b;
                targetBlock = blockPanel.checkTouch(touchDownV2).getBlock();
                if (targetBlock!=null){
                    targetBlock.stageToLocalCoordinates(touchDownV2);
                }
                touchDown.set(x,y);
                return b;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                if (targetBlock!=null) {
                    Vector2 vector2  = new Vector2();
                    vector2.set(x,y);
                    click.getParent().localToStageCoordinates(vector2);
                    targetBlock.getParent().stageToLocalCoordinates(vector2);
                    targetBlock.setPosition(vector2.x-touchDownV2.x,vector2.y-touchDownV2.y);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (targetBlock!=null) {
                    boolean b = checkBlock(targetBlock, gameView);
                    if (b) {
                        gameView.addTagetBlock(targetBlock);
                    }else {
                        targetBlock.addAction(Actions.moveTo(0,0,0.4f));
                    }
                    targetBlock = null;
                }
            }
        });
    }

    public boolean checkBlock(BaseBlockActor baseBlockActor,GameView gameView){
        return gameView.checkBlock(baseBlockActor);
    }
}
