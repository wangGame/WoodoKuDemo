package kw.test.file;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;

public class DesktopLauncher {
    public static void main(String[] args) {
        ReadFileConfig readFileConfig = new ReadFileConfig();
        Bean value = readFileConfig.getValue();
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = value.getName();
        config.x = 1000;
        config.y = 0;
        config.height = (int)(960);
        config.width = (int) (540);
        config.stencil = 8;
        new LwjglApplication(new Game() {
            @Override
            public void create() {
                FileHandle internal = Gdx.files.internal("/");
            }

            public void x(FileHandle fileHandle){
                if (fileHandle.isDirectory()) {
                    x(fileHandle);
                }else {
                    if (fileHandle.name().endsWith(".webp")) {
                        String path = fileHandle.parent().path();

                    }
                }
            }
        }, config);
    }
}