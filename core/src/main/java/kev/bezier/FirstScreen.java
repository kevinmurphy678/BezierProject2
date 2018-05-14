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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
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
    float smoothness[] = {1};
    CharArray buffer = new CharArray();
    Function f = new Function("f(x) = x");
    char[] buffer2 = new char[32];

    public static final int SPACING = 32;
    public static final int SIZE    = 32;

    boolean drawLines[] = {false};
    boolean drawLinesConnecting[] = {false};
    boolean drawT[] = {false};

    int order = 1;

    Array<Vector2> pointsList = new Array<Vector2>();
    boolean locked[] = new boolean[4];
    @Override
    public void render(float delta) {
        elapsedTime += delta;
        Vec4 color = Helper.clearColor;

        Gdx.gl.glClearColor(color.x, color.y, color.z, color.w);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

       batch.begin();
       font.draw(batch, "Bézier Curves: " + Gdx.graphics.getFramesPerSecond(), 48, Gdx.graphics.getHeight() + 380 + 32 * MathUtils.sin(elapsedTime));
        batch.end();

        lwjglGL3.newFrame();

        Helper.renderMenuBar();
        int modifier = SPACING*SIZE;
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
            points[0] = new Vector2(Math.round(MathUtils.random(modifier)/SPACING)*SPACING,Math.round(MathUtils.random(modifier)/SPACING)*SPACING);
            points[1] = new Vector2(Math.round(MathUtils.random(modifier)/SPACING)*SPACING,Math.round(MathUtils.random(modifier)/SPACING)*SPACING);



            pointsList.clear();
            pointsList.addAll(points);
            curve = new Bezier<Vector2>(points);
            order = 1;
        }

        if (IMGUI.button("Generate Quadratic Curve", new Vec2(256, 48)))
        {
            Vector2[] points = new Vector2[3];
            points[0] = new Vector2(Math.round(MathUtils.random(modifier)/SPACING)*SPACING,Math.round(MathUtils.random(modifier)/SPACING)*SPACING);
            points[1] = new Vector2(Math.round(MathUtils.random(modifier)/SPACING)*SPACING,Math.round(MathUtils.random(modifier)/SPACING)*SPACING);
            points[2] = new Vector2(Math.round(MathUtils.random(modifier)/SPACING)*SPACING,Math.round(MathUtils.random(modifier)/SPACING)*SPACING);
            pointsList.clear();
            pointsList.addAll(points);
            curve = new Bezier<Vector2>(points);
            order = 2;
        }

        if (IMGUI.button("Generate Cubic Curve", new Vec2(256, 48)))
        {
            Vector2[] points = new Vector2[4];
            points[0] = new Vector2(Math.round(MathUtils.random(modifier)/SPACING)*SPACING,Math.round(MathUtils.random(modifier)/SPACING)*SPACING);
            points[1] = new Vector2(Math.round(MathUtils.random(modifier)/SPACING)*SPACING,Math.round(MathUtils.random(modifier)/SPACING)*SPACING);
            points[2] = new Vector2(Math.round(MathUtils.random(modifier)/SPACING)*SPACING,Math.round(MathUtils.random(modifier)/SPACING)*SPACING);
            points[3] = new Vector2(Math.round(MathUtils.random(modifier)/SPACING)*SPACING,Math.round(MathUtils.random(modifier)/SPACING)*SPACING);
            pointsList.clear();
            pointsList.addAll(points);
            curve = new Bezier<Vector2>(points);
            order = 3;
        }

        if(Gdx.input.isTouched()) {
            for (Vector2 point : pointsList) { //dumb way of doing this: don't code at 3 am
                Vector3 coords = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                if (coords.x < point.x + 4 && coords.x > point.x - 4 || locked[pointsList.indexOf(point,false)]) {
                    if (coords.y < point.y + 4 && coords.y > point.y - 4 || locked[pointsList.indexOf(point,false)]) {

                        boolean dragging = false;
                        for(int i = 0; i < 4 && i != pointsList.indexOf(point,false); i++)
                        {
                            if(locked[i])dragging=true;
                        }
                        if(!dragging) {
                            point.add(Gdx.input.getDeltaX(), -Gdx.input.getDeltaY());
                            locked[pointsList.indexOf(point, false)] = true;
                            Vector2[] newPoints = pointsList.toArray(Vector2.class);
                            curve = new Bezier<Vector2>(newPoints);
                        }
                    }
                }
            }
        }else{locked = new boolean[4];}

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
        IMGUI.checkbox("Draw T", drawT);

        if(order==1 && pointsList.size>0)
        {

            float x =    (pointsList.get(0).x / SPACING) - (SPACING/2)  ;
            float x1 =   (pointsList.get(1).x / SPACING)  - (SPACING/2) ;
            float y =    (pointsList.get(0).y / SPACING) - (SPACING/2)  ;
            float y1 =   (pointsList.get(1).y / SPACING)  - (SPACING/2) ;

            Vector2 value = new Vector2();
            curve.valueAt(value, smoothness[0]);
            value.x = (value.x / SPACING) - (SPACING/2);
            value.y = (value.y / SPACING) - (SPACING/2);


            IMGUI.text("(1-t) * P0 + t * P1");
            IMGUI.text("X = " + (1-smoothness[0]) + " * " + x + " + " + smoothness[0] + " * " + x1 + " = " + value.x);
            IMGUI.text("Y = " + (1-smoothness[0]) + " * " + y + " + " + smoothness[0] + " * " + y1 + " = " + value.y);
        }

        Helper.drawGrid(0,0,SPACING,SIZE);
        Helper.renderBezierCurve(curve, smoothness[0],  order, drawLines[0], drawLinesConnecting[0],drawT[0]);

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