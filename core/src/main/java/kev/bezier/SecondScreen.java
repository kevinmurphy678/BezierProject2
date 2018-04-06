package kev.bezier;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import glm_.vec4.Vec4;

import static kev.bezier.Helper.*;

public class SecondScreen implements Screen {
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Vec4 color = Helper.clearColor;

        Gdx.gl.glClearColor(color.x, color.y, color.z, color.w);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "This is another screen..",48,Gdx.graphics.getHeight()-96);
        batch.end();

        lwjglGL3.newFrame();

        Helper.renderMenuBar();

        IMGUI.render();

        if(IMGUI.getDrawData() != null)
            lwjglGL3.renderDrawData(IMGUI.getDrawData());
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
