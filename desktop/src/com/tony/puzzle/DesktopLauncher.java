package com.tony.puzzle;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tony.photoshader.WoodoKu;

import kw.test.file.Bean;
import kw.test.file.ReadFileConfig;

//1.43f
public class DesktopLauncher {
    public static void main(String[] args) {
        ReadFileConfig readFileConfig = new ReadFileConfig();
        Bean value = readFileConfig.getValue();
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = value.getName();
        config.x = 1000;
        config.y = 0;
        config.samples = 8;
        config.useHDPI = true;
        config.height = (int)(1280 * 0.5f);
        config.width = (int) (720 * 0.5f);
        config.stencil = 8;
        new LwjglApplication(new WoodoKu(), config);
    }
}