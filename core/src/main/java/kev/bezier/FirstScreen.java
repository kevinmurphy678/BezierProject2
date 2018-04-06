package kev.bezier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import glm_.vec2.Vec2;
import glm_.vec4.Vec4;
import imgui.Context;
import imgui.ImGui;
import imgui.impl.LwjglGL3;
import uno.glfw.GlfwWindow;

//Import helper variables like font,camera,batch, imgui stuffs
import static kev.bezier.Helper.*;

public class FirstScreen implements Screen {
    @Override
    public void show() {

    }

    float elapsedTime;
    @Override
    public void render(float delta) {
        elapsedTime+=delta;
        Vec4 color = Helper.clearColor;

        Gdx.gl.glClearColor(color.x, color.y, color.z, color.w);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Welcome",48,Gdx.graphics.getHeight()-96 + 32*MathUtils.sin(elapsedTime));
        batch.end();

        lwjglGL3.newFrame();

        Helper.renderMenuBar();

        //IMGUI.text("FPS: " + Gdx.graphics.getFramesPerSecond());
        //if(IMGUI.button("Don't click", new Vec2(256,128)))

        IMGUI.render();

        if(IMGUI.getDrawData() != null)
            lwjglGL3.renderDrawData(IMGUI.getDrawData());
    }

    @Override
    public void resize(int width, int height) {
        //Set new camera size
        Gdx.graphics.setWindowedMode(width,height);
        camera.setToOrtho(false);
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
        lwjglGL3.shutdown();
    }
}