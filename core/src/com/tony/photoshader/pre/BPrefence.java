package com.tony.photoshader.pre;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Author by tony
 * Date on 2025/7/20.
 */
public class BPrefence {
    private Preferences preferences;
    private static BPrefence bPrefence;

    public static BPrefence getbPrefence() {
        if (bPrefence == null){
            bPrefence = new BPrefence();
        }
        return bPrefence;
    }

    private BPrefence(){
        preferences = Gdx.app.getPreferences("BlockBlast");
    }

    public int getMaxScore(){
        return preferences.getInteger("maxScore",0);
    }

    public void saveMaxScore(int maxScore){
        preferences.putInteger("maxScore",maxScore);
        preferences.flush();
    }
}
