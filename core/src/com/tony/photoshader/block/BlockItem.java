package com.tony.photoshader.block;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.tony.photoshader.constant.Constant;

public class BlockItem extends Group {
    public BlockItem(){
        setSize(Constant.blockWidth,Constant.blockWidth);
        Image bg = new Image(Asset.getAsset().getTexture("img/white.png"));
        addActor(bg);
        bg.setSize(Constant.blockWidth,Constant.blockWidth);
        bg.setPosition(Constant.blockWidth/2f,Constant.blockWidth/2f, Align.center);

        Image kuang = new Image(Asset.getAsset().getTexture("img/kuangxu.png"));
        addActor(kuang);
        kuang.setColor(Color.GOLD);
        kuang.setSize(Constant.blockWidth,Constant.blockWidth);
        kuang.setPosition(Constant.blockWidth/2f,Constant.blockWidth/2f, Align.center);
    }
}
