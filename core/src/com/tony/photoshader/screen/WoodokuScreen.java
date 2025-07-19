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
import com.tony.photoshader.view.BlockGroup;
import com.tony.photoshader.view.BlockPanelLogic;
import com.tony.photoshader.view.GameView;

@ScreenResource("cocos/GameScreen.json")
public class WoodokuScreen extends BaseScreen {
    private GameView gameView;
    private BlockPanelLogic blockPanelLogic;
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

        blockPanelLogic = new BlockPanelLogic(gameView,rootView);
        blockPanelLogic.setBlock();

        gameView.setBlockPanel(blockPanelLogic);

        Actor click = new Actor();
        click.setSize(Constant.GAMEWIDTH,Constant.GAMEHIGHT);
        addActor(click);
        click.addListener(new ClickListener(){
            private Vector2 touchDownV2 = new Vector2();
            private BaseBlockActor targetBlock;
            private Vector2 touchDown = new Vector2();
            private Vector2 offTouch = new Vector2(0,199);
            private Vector2 positionTargetV2  = new Vector2();

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                boolean b = super.touchDown(event, x, y, pointer, button);
                touchDownV2.set(x,y);
                click.getParent().localToStageCoordinates(touchDownV2);
                BlockGroup blockGroup = blockPanelLogic.checkTouch(touchDownV2);
                if (blockGroup == null)return b;
                targetBlock = blockGroup.getBlock();
                if (targetBlock!=null){
                    targetBlock.stageToLocalCoordinates(touchDownV2);
                }

                targetBlock.setOrigin(Align.center);
                targetBlock.setScale(1,1);
                touchDown.set(x,y);
                return b;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                if (targetBlock!=null) {
                    positionTargetV2.set(x,y);
                    click.getParent().localToStageCoordinates(positionTargetV2);
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
                    boolean b = checkBlock(targetBlock, gameView);
                    if (b) {
                        gameView.addTagetBlock(targetBlock);
                    }else {
                        targetBlock.addAction(
                                Actions.parallel(
                                        Actions.moveToAligned(150,150,Align.center,0.1f),
                                        Actions.scaleTo(0.8f,0.8f,0.1f)
                                )
                        );
                        if (!checkAll()) {
                            setScreen(WoodokuScreen.class);
//                            touchDisable();
                        }
                    }
                    targetBlock = null;
                }
            }
        });
    }

    public boolean checkAll(){
        Array<BaseBlockActor> allNotUse = blockPanelLogic.getAllNotUse();
        return gameView.checkBlockAll(allNotUse);
    }

    public boolean checkBlock(BaseBlockActor baseBlockActor,GameView gameView){
        return gameView.checkBlock(baseBlockActor);
    }
}
