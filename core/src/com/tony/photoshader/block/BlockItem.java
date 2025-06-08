package com.tony.photoshader.block;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;

public class BlockItem extends Group {
    private Image left;
    private Image right;
    private Image up;
    private Image down;

    public BlockItem(){
        setSize(100,100);
        Image bg = new Image(Asset.getAsset().getTexture("img/white.png"));
        addActor(bg);
        bg.setSize(90,90);
        bg.setPosition(50,50, Align.center);

        Image kuang = new Image(Asset.getAsset().getTexture("img/kuangxu.png"));
        addActor(kuang);
        kuang.setColor(Color.GOLD);
        kuang.setSize(120,120);
        kuang.setPosition(50,50, Align.center);

    }
}
