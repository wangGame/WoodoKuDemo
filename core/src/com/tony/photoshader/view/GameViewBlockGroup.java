package com.tony.photoshader.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.utils.Layer;

public class GameViewBlockGroup extends Group {
    public Label label;
    public GameViewBlockGroup(){
        label = new Label(0+"",new Label.LabelStyle(){{
            font = Asset.getAsset().loadBitFont("font/Krub-Bold_redStroke_52.fnt");
        }});
        addActor(label);
    }

    public void updateLabelPosition() {
        label.setPosition(getWidth()/2f,getHeight()/2f, Align.center);
    }

    public void setLabelValue(int i) {
        label.setText(i+"");
    }

    public void resetLabelValue(){
        label.setText("0");
    }
}
