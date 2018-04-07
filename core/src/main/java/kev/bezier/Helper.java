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
import com.badlogic.gdx.math.Vector2;
import glm_.vec4.Vec4;
import imgui.*;
import imgui.impl.LwjglGL3;


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
        parameter.shadowOffsetX = 3;
        parameter.size = 48;

        parameter.genMipMaps=true;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.Linear;

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

    public static void renderBezierCurve(Bezier<Vector2> curve, float smoothness, boolean drawHandles, boolean drawDots)
    {
        if(curve==null)return;
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        float distance = 1f / smoothness;
        System.out.println(distance);
        for(int i = 0; i < smoothness; i++)
        {
            float t = i / smoothness;
            Vector2 start = new Vector2();
            Vector2 end = new Vector2();
            curve.valueAt(start, t);
            curve.valueAt(end, t-distance);
            shapeRenderer.line(start,end);
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //Point 1
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(curve.points.get(0).x, curve.points.get(0).y, 4);


        shapeRenderer.setColor(Color.NAVY);
        shapeRenderer.circle(curve.points.get(1).x, curve.points.get(1).y, 4);
        shapeRenderer.setColor(Color.NAVY);
        shapeRenderer.circle(curve.points.get(2).x, curve.points.get(2).y, 4);

        //Point 2
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(curve.points.get(3).x, curve.points.get(3).y, 4);
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
