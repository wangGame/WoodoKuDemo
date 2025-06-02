package com.tony.photoshader.screen;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.BaseGame;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.screen.BaseScreen;
import com.tony.photoshader.block.BaseBlockActor;
import com.tony.photoshader.block.BlockActor1;
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

        BlockActor1 blockActor1 = new BlockActor1();
        blockActor1.createBlock();
        addActor(blockActor1);

        Actor click = new Actor();
        click.setSize(Constant.GAMEWIDTH,Constant.GAMEHIGHT);
        addActor(click);
        click.addListener(new ClickListener(){
            private BaseBlockActor targetBlock;
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                float x1 = blockActor1.getX();
                float y1 = blockActor1.getY();
                float x2 = blockActor1.getX(Align.right);
                float y2 = blockActor1.getY(Align.top);
                if (x >= x1){
                    if (x<=x2){
                        if (y>=y1){
                            if (y<=y2){
                                targetBlock  = blockActor1;
                            }
                        }
                    }
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                if (targetBlock!=null) {
                    targetBlock.setPosition(x, y);
                    System.out.println(checkBlock(targetBlock, gameView));
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (targetBlock!=null) {
                    boolean b = checkBlock(targetBlock, gameView);
                    if (b) {
                        gameView.addTagetBlock(targetBlock);
                    }
                }
            }
        });
    }

    public boolean checkBlock(BaseBlockActor baseBlockActor,GameView gameView){
        return gameView.checkBlock(baseBlockActor);
    }
}
