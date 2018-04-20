package kev.bezier;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import uno.glfw.GlfwWindow;



public class BezierDemo extends Game {
    @Override
    public void create()
    {
        Lwjgl3Graphics lwjgl3Graphics = (Lwjgl3Graphics) Gdx.graphics;
        long windowHandle = lwjgl3Graphics.getWindow().getWindowHandle();
        Helper.lwjglGL3.init(new GlfwWindow(windowHandle),false);
        Helper.initiate();

        Helper.game = this;

        Gdx.input.setInputProcessor(new ImGuiInputProcessor());

        setScreen(Helper.firstScreen);
    }

    @Override
    public void resize(int width, int height) {
        //Set new camera size
        Gdx.graphics.setWindowedMode(width,height);
        Helper.camera.setToOrtho(false);
        Helper.shapeRenderer.setProjectionMatrix(Helper.camera.combined);
        Helper.batch.setProjectionMatrix(Helper.camera.combined);
    }
}