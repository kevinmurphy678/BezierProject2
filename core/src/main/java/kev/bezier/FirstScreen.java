package kev.bezier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.graphics.GL20;
import glm_.vec2.Vec2;
import imgui.Context;
import imgui.ImGui;
import imgui.impl.LwjglGL3;
import uno.glfw.GlfwWindow;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {

    Context IMGUI_CONTEXT = new Context();
    LwjglGL3 lwjglGL3 = LwjglGL3.INSTANCE;
    ImGui IMGUI = ImGui.INSTANCE;

    @Override
    public void show() {
        Lwjgl3Graphics lwjgl3Graphics = (Lwjgl3Graphics)Gdx.graphics;
        long windowHandle = lwjgl3Graphics.getWindow().getWindowHandle();
        lwjglGL3.init(new GlfwWindow(windowHandle),false);
    }
    String fuck = "Hello";
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.6f, 0.5f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        lwjglGL3.newFrame();

        IMGUI.text(fuck);
        if(IMGUI.button("Don't click", new Vec2(256,128)))
            fuck += " what in the fuck did you just say to me?";

        IMGUI.render();

        if(IMGUI.getDrawData() != null)
            lwjglGL3.renderDrawData(IMGUI.getDrawData());
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}