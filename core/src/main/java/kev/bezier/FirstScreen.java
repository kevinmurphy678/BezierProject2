package kev.bezier;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.CharArray;
import com.badlogic.gdx.utils.TimeUtils;
import glm_.vec2.Vec2;
import glm_.vec4.Vec4;
import imgui.Context;
import imgui.ImGui;
import imgui.impl.LwjglGL3;
import org.mariuszgromada.math.mxparser.Function;
import uno.glfw.GlfwWindow;

//Import helper variables like font,camera,batch, imgui stuffs
import java.util.ArrayList;

import static kev.bezier.Helper.*;

public class FirstScreen implements Screen {
    @Override
    public void show() {

    }
    Bezier<Vector2> curve;

    float elapsedTime;
    float smoothness[] = {10};
    CharArray buffer = new CharArray();
    Function f = new Function("f(x) = x");
    char[] buffer2 = new char[32];

    final int SPACING = 32;
    final int SIZE    = 32;

    boolean drawLines[] = {false};
    boolean drawLinesConnecting[] = {false};

    int order = 1;

    @Override
    public void render(float delta) {
        elapsedTime += delta;
        Vec4 color = Helper.clearColor;

        Gdx.gl.glClearColor(color.x, color.y, color.z, color.w);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

       //batch.begin();
       //font.draw(batch, "Bézier Curves", 48, Gdx.graphics.getHeight() + 380 + 32 * MathUtils.sin(elapsedTime));
       // batch.end();

        lwjglGL3.newFrame();

        Helper.renderMenuBar();
        float modifier = SPACING*SIZE;
        //IMGUI.text("FPS: " + Gdx.graphics.getFramesPerSecond());
//        if (IMGUI.button("Generate Sin Curve", new Vec2(256, 48)))
//        {
//            Vector2[] points = new Vector2[4];
//            points[0] = new Vector2(0,0);
//            points[1] = new Vector2(0.3642f * modifier,0);
//            points[2] = new Vector2(0.6358f * modifier, 1 * modifier);
//            points[3] = new Vector2(1*modifier,1*modifier);
//            curve = new Bezier<Vector2>(points);
//        }

        if (IMGUI.button("Generate Linear Curve", new Vec2(256, 48)))
        {
            Vector2[] points = new Vector2[2];
            points[0] = new Vector2(MathUtils.random(modifier),MathUtils.random(modifier));
            points[1] = new Vector2(MathUtils.random(modifier),MathUtils.random(modifier));
            curve = new Bezier<Vector2>(points);
            order = 1;
        }

        if (IMGUI.button("Generate Quadratic Curve", new Vec2(256, 48)))
        {
            Vector2[] points = new Vector2[3];
            points[0] = new Vector2(MathUtils.random(modifier),MathUtils.random(modifier));
            points[1] = new Vector2(MathUtils.random(modifier),MathUtils.random(modifier));
            points[2] = new Vector2(MathUtils.random(modifier),MathUtils.random(modifier));
            curve = new Bezier<Vector2>(points);
            order = 2;
        }

        if (IMGUI.button("Generate Cubic Curve", new Vec2(256, 48)))
        {
            Vector2[] points = new Vector2[4];
            points[0] = new Vector2(MathUtils.random(modifier),MathUtils.random(modifier));
            points[1] = new Vector2(MathUtils.random(modifier),MathUtils.random(modifier));
            points[2] = new Vector2(MathUtils.random(modifier),MathUtils.random(modifier));
            points[3] = new Vector2(MathUtils.random(modifier),MathUtils.random(modifier));
            curve = new Bezier<Vector2>(points);
            order = 3;
        }



//        IMGUI.inputText("Function",buffer2,buffer2.length);
//
//        if(buffer2.length>0) {
//            f = new Function(new String(buffer2).trim());
//        }
//
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.BLACK);
//        for(float x = -SIZE/2; x < SIZE/2; x+=0.05f)
//        {
//            shapeRenderer.circle(SPACING*x + modifier/2, SPACING*(float)f.calculate(x)+modifier/2,4);
//        }
//        shapeRenderer.end();

        if(Gdx.input.isTouched() && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
            camera.update();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W))
            camera.zoom-=delta;
        else if(Gdx.input.isKeyPressed(Input.Keys.S))
            camera.zoom+=delta;

        IMGUI.sliderFloat("T [0,1]", smoothness, 0 ,1, "",1);

        IMGUI.checkbox("Draw point lines", drawLines);
        IMGUI.checkbox("Draw connecting lines", drawLinesConnecting);

        Helper.drawGrid(0,0,SPACING,SIZE);
        Helper.renderBezierCurve(curve, smoothness[0],  order, drawLines[0], drawLinesConnecting[0]);

        IMGUI.render();

        if(IMGUI.getDrawData() != null)
            lwjglGL3.renderDrawData(IMGUI.getDrawData());

        camera.update();
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