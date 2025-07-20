package com.tony.photoshader.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.tony.photoshader.block.BaseBlockActor;
import com.tony.photoshader.block.BlockManager;

public class BottomBlockItem extends Group {
    private BaseBlockActor blockActor;
    private boolean used;
    private GameView gameView;

    public BottomBlockItem(GameView gameView){
        setSize(300,300);
        this.gameView = gameView;
    }

    public void genBlock(){
        blockActor = null;
        int tryTimes = 10;
        blockActor = BlockManager.getBaseBlockActor();
        while (!gameView.checkArr(blockActor.getData())) {
            blockActor = BlockManager.getBaseBlockActor();
            tryTimes ++ ;
            if (tryTimes<=0){
                blockActor = null;
                break;
            }
        }
        if (blockActor == null) {
            //如果尝试几次失败后，那么就遍历，如果遍历也就不了它，只能等死了
            for (int i = 0; i < 18; i++) {
                blockActor = BlockManager.getBaseBlockActor(i);
                if (gameView.checkArr(blockActor.getData())) {
                    break;
                }else {
                    blockActor = null;
                }
            }
        }
        if (blockActor == null){
            blockActor = BlockManager.getBaseBlockActor();
        }
        blockActor.createBlock();
        addActor(blockActor);
        blockActor.setPosition(150,150, Align.center);
        used = false;
    }

    public void setBlock(BaseBlockActor block){
        blockActor = block;
        blockActor.createBlock();
        addActor(blockActor);
        blockActor.setPosition(150,150, Align.center);
        used = false;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean checkUsed(){
        return used;
    }

    public BaseBlockActor getBlock() {
        return blockActor;
    }
}
