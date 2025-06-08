package com.tony.photoshader.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.utils.Layer;
import com.tony.photoshader.block.BlockItem;

public class GameViewBlockGroup extends Group {
    private Label label;
    private BlockItem blockItem;
    public GameViewBlockGroup(){
        setSize(100,100);
        Image bg = new Image(Asset.getAsset().getTexture("img/white.png"));
        addActor(bg);
        bg.setSize(100,100);
        label = new Label(0+"",new Label.LabelStyle(){{
            font = Asset.getAsset().loadBitFont("font/Krub-Bold_redStroke_52.fnt");
        }});
        addActor(label);

        blockItem = new BlockItem();
        addActor(blockItem);
        blockItem.setPosition(getWidth()/2f,getHeight()/2,Align.center);
        blockItem.setVisible(false);
    }

    public void updateLabelPosition() {
        label.setPosition(getWidth()/2f,getHeight()/2f, Align.center);
    }

    public void setLabelValue(int i) {
        label.setText(i+"");
        if (i == 1){
            blockItem.setVisible(true);
        }
    }

    public void resetLabelValue(){
        label.setText("0");
        blockItem.setVisible(false);
    }
}
