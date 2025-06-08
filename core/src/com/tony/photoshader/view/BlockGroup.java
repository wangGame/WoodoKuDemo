package com.tony.photoshader.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.tony.photoshader.block.BaseBlockActor;
import com.tony.photoshader.block.BlockManager;

public class BlockGroup extends Group {
    private BaseBlockActor blockActor;
    private boolean used;

    public BlockGroup(){
        setSize(300,300);
    }

    public BaseBlockActor getBlock() {
        return blockActor;
    }

    public void genBlock(){
        blockActor = BlockManager.getBaseBlockActor();
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
