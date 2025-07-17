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
        int[][] data = blockActor.getData();
        for (int i = 0; i < data.length; i++) {
            for (int i1 = 0; i1 < data[0].length; i1++) {
                System.out.print(data[data.length - 1 - i][i1]);
            }
            System.out.println();
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
