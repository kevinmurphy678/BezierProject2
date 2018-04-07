package kev.bezier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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
    Vector2[] points = new Vector2[4];
    Bezier<Vector2> curve;

    float elapsedTime;
    @Override
    public void render(float delta) {
        elapsedTime += delta;
        Vec4 color = Helper.clearColor;

        Gdx.gl.glClearColor(color.x, color.y, color.z, color.w);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        batch.begin();
        font.draw(batch, "Welcome", 48, Gdx.graphics.getHeight() - 96 + 32 * MathUtils.sin(elapsedTime));
        batch.end();

        lwjglGL3.newFrame();

        Helper.renderMenuBar();

        //IMGUI.text("FPS: " + Gdx.graphics.getFramesPerSecond());
        if (IMGUI.button("Generate Curve", new Vec2(128, 96)))
        {
            float modifier = 32*16;
            points[0] = new Vector2(0,0);
            points[1] = new Vector2(0.3642f * modifier,0);
            points[2] = new Vector2(0.6358f * modifier, 1 * modifier);
            points[3] = new Vector2(1*modifier,1*modifier);
            curve = new Bezier<Vector2>(points);
        }

        if(Gdx.input.isTouched()) {
            camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
            camera.update();
        }

        System.out.println(camera.position);
        Helper.drawGrid(0,0,32,16);
        Helper.renderBezierCurve(curve, 100, true, true);

        IMGUI.render();

        if(IMGUI.getDrawData() != null)
            lwjglGL3.renderDrawData(IMGUI.getDrawData());
    }

    @Override
    public void resize(int width, int height) {

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