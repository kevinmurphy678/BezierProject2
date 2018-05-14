package kev.bezier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import glm_.vec4.Vec4;
import imgui.*;
import imgui.impl.LwjglGL3;

import static kev.bezier.FirstScreen.SPACING;


public class Helper {

    //IMGUI things
    public static Context IMGUI_CONTEXT = new Context();
    public static LwjglGL3 lwjglGL3 = LwjglGL3.INSTANCE;
    public static ImGui IMGUI = ImGui.INSTANCE;
    public static IO io = IMGUI.getIo();
    public static BitmapFont font;
    //public static Vec2 buttonSize = new Vec2(48,24);

    //LIBGDX things
    public static SpriteBatch batch = new SpriteBatch();
    public static ShapeRenderer shapeRenderer = new ShapeRenderer();
    public static OrthographicCamera camera = new OrthographicCamera();
    public static Vec4 clearColor = new Vec4(0.4f,0.3f,0.45f,1);


    //Screen instances
    public static BezierDemo game; //Game instance to switch screens
    public static FirstScreen firstScreen = new FirstScreen();
    public static SecondScreen secondScreen = new SecondScreen();

    public static void initiate(){
        IMGUI.styleColorsDark(null);
        generateFont();
    }

    private static void generateFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Medium.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        //parameter.shadowOffsetX = 3;
        parameter.size = 16;

       // parameter.genMipMaps=true;
        //parameter.magFilter = Texture.TextureFilter.Linear;
        //parameter.minFilter = Texture.TextureFilter.Linear;

        font = generator.generateFont(parameter);
        generator.dispose();
        System.out.println("Generated font");

        Font font = io.getFonts().addFontFromFileTTF("Roboto-Medium.ttf", 18f, new FontConfig(), io.getFonts().getGlyphRangesDefault());
        io.setFontDefault(font);
    }


    public static void drawGrid(int xPos, int yPos, int spacing, int size)
    {

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for(int x = 0; x < size*spacing+1; x+= spacing) {
            if(x == (size*spacing)/2)    shapeRenderer.setColor(Color.BLUE); else shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.line(new Vector2(x + xPos, yPos), new Vector2(x + xPos, size * spacing + yPos));
        }
        for(int y = 0; y < size*spacing+1; y+= spacing) {
            if(y == (size*spacing)/2)    shapeRenderer.setColor(Color.RED); else shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.line(new Vector2(xPos, y + yPos), new Vector2(size * spacing + xPos, y + yPos));
        }
        shapeRenderer.end();
    }


    static private final float smoothness = 128f;
    public static void renderBezierCurve(Bezier<Vector2> curve, float t, int order, boolean drawLines, boolean drawLinesConnecting, boolean drawT)
    {
        if(curve==null)return;


        if(drawLines && order > 1)
        {
            for(int i=0; i < order;i++)
            {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

                shapeRenderer.setColor(Color.CORAL);
                shapeRenderer.rectLine(curve.points.get(i), curve.points.get(i+1),2);

                shapeRenderer.end();
            }
        }

        if(drawLinesConnecting && order > 1)
        {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

                Vector2 first = new Vector2(), second;
                if(order >= 2) {
                    Vector2 firstCurvePoint = new Vector2(curve.points.get(0));
                    Vector2 secondCurvePoint = new Vector2(curve.points.get(1));

                    firstCurvePoint.interpolate(curve.points.get(1), t, Interpolation.linear);
                    secondCurvePoint.interpolate(curve.points.get(2), t, Interpolation.linear);
                    shapeRenderer.setColor(Color.GREEN);
                    shapeRenderer.rectLine(firstCurvePoint, secondCurvePoint, 2);

                    if(drawT) {
                        shapeRenderer.setColor(Color.YELLOW);
                        shapeRenderer.circle(firstCurvePoint.x, firstCurvePoint.y, 3);
                        shapeRenderer.circle(secondCurvePoint.x, secondCurvePoint.y, 3);

                        batch.begin();
                        font.draw(batch, "T: " + t, firstCurvePoint.x, firstCurvePoint.y);
                        font.draw(batch, "T: " + t, secondCurvePoint.x, secondCurvePoint.y);
                        batch.end();
                    }


                    first = new Vector2(firstCurvePoint.interpolate(secondCurvePoint,t, Interpolation.linear));
                }

                if(order==3)
                {
                    Vector2 firstCurvePoint = new Vector2(curve.points.get(1));
                    Vector2 secondCurvePoint = new Vector2(curve.points.get(2));

                    firstCurvePoint.interpolate(curve.points.get(2), t, Interpolation.linear);
                    second = secondCurvePoint.interpolate(curve.points.get(3), t, Interpolation.linear);

                    shapeRenderer.setColor(Color.GREEN);
                    shapeRenderer.rectLine(firstCurvePoint, secondCurvePoint, 2);

                    if(drawT) {
                        shapeRenderer.setColor(Color.YELLOW);
                        shapeRenderer.circle(firstCurvePoint.x, firstCurvePoint.y, 3);
                        shapeRenderer.circle(secondCurvePoint.x, secondCurvePoint.y, 3);

                        batch.begin();
                        font.draw(batch, "T: " + t, firstCurvePoint.x, firstCurvePoint.y);
                        font.draw(batch, "T: " + t, secondCurvePoint.x, secondCurvePoint.y);
                        batch.end();
                    }

                    Vector2 firstCurvePoint2 = new Vector2(firstCurvePoint);

                    firstCurvePoint2.interpolate(second, t, Interpolation.linear);
                    first.interpolate(first, t, Interpolation.linear); //wrong

                    shapeRenderer.setColor(Color.RED);
                    shapeRenderer.rectLine(firstCurvePoint2, first,2);

                    if(drawT) {
                        shapeRenderer.setColor(Color.YELLOW);
                        shapeRenderer.circle(firstCurvePoint2.x, firstCurvePoint2.y, 3);
                        shapeRenderer.circle(first.x, first.y, 3);

                        batch.begin();
                        font.draw(batch, "T: " + t, firstCurvePoint2.x, firstCurvePoint2.y);
                        font.draw(batch, "T: " + t, first.x, first.y);
                        batch.end();
                    }
                }

             shapeRenderer.end();
        }

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Vector2 prevPoint = new Vector2();
        curve.valueAt(prevPoint, 0);
        for(int i = 1; i < smoothness+1; i++)
        {
            float time = i / smoothness;
            Vector2 start = new Vector2(prevPoint);
            curve.valueAt(prevPoint, time);
            shapeRenderer.line(start,prevPoint);
            if(time>t)break;
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.circle(prevPoint.x,prevPoint.y, 4);
        if(drawT) {
            batch.begin();
            font.draw(batch, "T: " + t, prevPoint.x, prevPoint.y);
            batch.end();
        }

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(curve.points.get(0).x, curve.points.get(0).y, 4);
        batch.begin();

        float x =    (curve.points.get(0).x / SPACING) - (SPACING/2)  ;
        float x1 =   (curve.points.get(1).x / SPACING)  - (SPACING/2)  ;
        float y =    (curve.points.get(0).y / SPACING) - (SPACING/2)  ;
        float y1 =   (curve.points.get(1).y / SPACING)  - (SPACING/2)  ;

        font.draw(batch, "(" + x1+ "," + y1 + ")", curve.points.get(1).x, curve.points.get(1).y);
        font.draw(batch, "(" + x+ "," + y + ")", curve.points.get(0).x, curve.points.get(0).y);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(curve.points.get(1).x, curve.points.get(1).y, 4);

        if(order>1) {
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(curve.points.get(2).x, curve.points.get(2).y, 4);

            float x2 =    (curve.points.get(2).x / SPACING) - (SPACING/2)  ;
            float y2 =   (curve.points.get(2).y / SPACING)  - (SPACING/2)  ;

            font.draw(batch, "(" + x2+ "," + y2 + ")", curve.points.get(2).x, curve.points.get(2).y);


        }

        if(order>2) {

            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(curve.points.get(3).x, curve.points.get(3).y, 4);

            float x3 =    (curve.points.get(3).x / SPACING) - (SPACING/2)  ;
            float y3 =   (curve.points.get(3).y / SPACING)  - (SPACING/2)  ;

            font.draw(batch, "(" + x3+ "," + y3+ ")", curve.points.get(3).x, curve.points.get(3).y);
        }

        batch.end();
        shapeRenderer.end();



    }

    public static void renderMenuBar()
    {
        if(IMGUI.beginMainMenuBar())
        {
            if(IMGUI.beginMenu("File",true))
            {
                if(IMGUI.menuItem("Quit", "", false,true))
                {
                    Gdx.app.exit();
                }
                IMGUI.endMenu();
            }
            if(IMGUI.beginMenu("Scene",true))
            {
                if(IMGUI.menuItem("Intro", "", game.getScreen().equals(firstScreen), true))
                {
                    game.setScreen(firstScreen);
                }
                if(IMGUI.menuItem("Another Scene", "", game.getScreen().equals(secondScreen), true))
                {
                    game.setScreen(secondScreen);
                }
                IMGUI.endMenu();
            }
            if(IMGUI.beginMenu("Settings",true))
            {
                IMGUI.colorEdit3("Clear Color", clearColor, 0);
                IMGUI.endMenu();
            }
            IMGUI.endMainMenuBar();
        }
    }
}
