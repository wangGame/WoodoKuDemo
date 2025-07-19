package com.tony.photoshader.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.tony.photoshader.block.BaseBlockActor;
import com.tony.photoshader.block.BlockManager;

public class BlockGroup extends Group {
    private BaseBlockActor blockActor;
    private boolean used;
    private GameView gameView;
    public BlockGroup(GameView gameView){
        setSize(300,300);
        this.gameView = gameView;
    }

    public BaseBlockActor getBlock() {
        return blockActor;
    }

    public void genBlock(){
        blockActor = BlockManager.getBaseBlockActor();
        int index = 10;
        while (!gameView.checkArr(blockActor.getData())) {
            blockActor = BlockManager.getBaseBlockActor();
            index -- ;
            if (index<=0)break;
        }
        for (int i = 0; i < 18; i++) {
            blockActor = BlockManager.getBaseBlockActor();
            if (gameView.checkArr(blockActor.getData())) {
                break;
            }
        }
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

}
