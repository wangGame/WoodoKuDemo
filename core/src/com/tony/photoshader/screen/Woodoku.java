package com.tony.photoshader.screen;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.BaseGame;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.screen.BaseScreen;
import com.tony.photoshader.block.BaseBlockActor;
import com.tony.photoshader.block.BlockActor1;
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
        rootView.addActor(blockPanel);

        BlockActor1 blockActor1 = new BlockActor1();
        blockActor1.createBlock();
//        rootView.addActor(blockActor1);




        Actor click = new Actor();
        click.setSize(Constant.GAMEWIDTH,Constant.GAMEHIGHT);
        addActor(click);
        click.addListener(new ClickListener(){
            private Vector2 touchDownV2 = new Vector2();
            private BlockGroup targetBlock;
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touchDownV2.set(x,y);
                click.getParent().localToStageCoordinates(touchDownV2);
                targetBlock = blockPanel.checkTouch(touchDownV2);
                if (targetBlock!=null){
                    targetBlock.stageToLocalCoordinates(touchDownV2);
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                if (targetBlock!=null) {
                    targetBlock.setPosition(x-touchDownV2.x, y-touchDownV2.y);
//                    checkBlock(targetBlock, gameView);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
//                if (targetBlock!=null) {
//                    boolean b = checkBlock(targetBlock, gameView);
//                    if (b) {
//                        gameView.addTagetBlock(targetBlock);
//                    }else {
//                        gameView.addAction(Actions.moveTo(0,0,Align.center));
//                    }
//                }
            }
        });
    }

    public boolean checkBlock(BaseBlockActor baseBlockActor,GameView gameView){
        return gameView.checkBlock(baseBlockActor);
    }
}
