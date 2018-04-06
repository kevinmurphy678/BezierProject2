package kev.bezier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
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
