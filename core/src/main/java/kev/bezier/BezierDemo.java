package kev.bezier;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import uno.glfw.GlfwWindow;

import static kev.bezier.Helper.lwjglGL3;

public class BezierDemo extends Game {
    @Override
    public void create()
    {
        Lwjgl3Graphics lwjgl3Graphics = (Lwjgl3Graphics) Gdx.graphics;
        long windowHandle = lwjgl3Graphics.getWindow().getWindowHandle();
        lwjglGL3.init(new GlfwWindow(windowHandle),false);
        Helper.initiate();

        Helper.game = this;
        setScreen(Helper.firstScreen);
    }
}