package com.tony.photoshader.screen;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.BaseGame;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.resource.annotation.ScreenResource;
import com.kw.gdx.screen.BaseScreen;
import com.tony.photoshader.block.BaseBlockActor;
import com.tony.photoshader.constant.BConstant;
import com.tony.photoshader.view.BottomBlockItem;
import com.tony.photoshader.view.BottomBlockLogic;
import com.tony.photoshader.view.GameView;

@ScreenResource("cocos/GameScreen.json")
public class WoodokuScreen extends BaseScreen {
    private GameView gameView;
    private BottomBlockLogic bottomBlockLogic;

    public WoodokuScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void initView() {
        super.initView();

        Group gameBoard = rootView.findActor("gameBoard");
        gameView = new GameView();
        gameBoard.addActor(gameView);
        gameView.setPosition(
                gameBoard.getWidth()/2f,
                gameBoard.getHeight()/2f, Align.center);

        bottomBlockLogic = new BottomBlockLogic(gameView,rootView);
        bottomBlockLogic.setBlock();
        gameView.setBlockPanel(bottomBlockLogic);

        Actor userTouchPanel = new Actor();
        userTouchPanel.setSize(Constant.GAMEWIDTH,Constant.GAMEHIGHT);
        addActor(userTouchPanel);
        userTouchPanel.addListener(new ClickListener(){
            private Vector2 touchDownV2 = new Vector2();
            private BaseBlockActor targetBlock;
            private Vector2 offTouch = new Vector2(0,199);
            private Vector2 positionTargetV2  = new Vector2();

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean b = super.touchDown(event, x, y, pointer, button);
                touchDownV2.set(x,y);
                userTouchPanel.getParent().localToStageCoordinates(touchDownV2);
                BottomBlockItem bottomBlockItem = bottomBlockLogic.checkTouch(touchDownV2);
                if (bottomBlockItem == null)return b;
                targetBlock = bottomBlockItem.getBlock();
                if (targetBlock!=null){
                    targetBlock.stageToLocalCoordinates(touchDownV2);
                }
                targetBlock.setOrigin(Align.center);
                targetBlock.setScale(1,1);

                return b;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                if (targetBlock!=null) {
                    positionTargetV2.set(x,y);
                    userTouchPanel.getParent().localToStageCoordinates(positionTargetV2);
                    targetBlock.getParent().stageToLocalCoordinates(positionTargetV2);
                    targetBlock.setPosition(positionTargetV2.x-touchDownV2.x+offTouch.x,positionTargetV2.y-touchDownV2.y+offTouch.y);
                    gameView.resetColor();
                    if(checkBlock(targetBlock, gameView)){
                        gameView.setCanMove(targetBlock);
                    }
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (targetBlock!=null) {
                    if (checkBlock(targetBlock, gameView)) {
                        gameView.addTagetBlock(targetBlock);
                        BConstant.score+=targetBlock.getScore();
                    }else {
                        targetBlock.addAction(
                                Actions.parallel(
                                        Actions.moveToAligned(150,150,Align.center,0.1f),
                                        Actions.scaleTo(0.6f,0.6f,0.1f)
                                )
                        );
                        if (!checkAllBlock()) {
                            setScreen(WoodokuScreen.class);
                        }
                    }
                    targetBlock = null;
                }
            }
        });
    }

    public boolean checkAllBlock(){
        Array<BaseBlockActor> allNotUse = bottomBlockLogic.getAllNotUse();
        return gameView.checkBlockAll(allNotUse);
    }

    public boolean checkBlock(BaseBlockActor baseBlockActor,GameView gameView){
        return gameView.checkBlock(baseBlockActor);
    }
}
