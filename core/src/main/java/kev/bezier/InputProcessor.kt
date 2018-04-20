package kev.bezier

import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.*
import com.badlogic.gdx.InputAdapter
import glm_.vec2.Vec2d
import imgui.impl.LwjglGL3
import org.lwjgl.glfw.GLFW

class ImGuiInputProcessor : InputAdapter() {
    private val gdxGLFWKeyMap = mutableMapOf<Int, Int>()

    init {
        gdxGLFWKeyMap[Keys.TAB] = GLFW.GLFW_KEY_TAB

        gdxGLFWKeyMap[Keys.LEFT] = GLFW.GLFW_KEY_LEFT
        gdxGLFWKeyMap[Keys.RIGHT] = GLFW.GLFW_KEY_RIGHT
        gdxGLFWKeyMap[Keys.UP] = GLFW.GLFW_KEY_UP
        gdxGLFWKeyMap[Keys.DOWN] = GLFW.GLFW_KEY_DOWN

        gdxGLFWKeyMap[Keys.PAGE_UP] = GLFW.GLFW_KEY_PAGE_UP
        gdxGLFWKeyMap[Keys.PAGE_DOWN] = GLFW.GLFW_KEY_PAGE_DOWN

        gdxGLFWKeyMap[Keys.HOME] = GLFW.GLFW_KEY_HOME
        gdxGLFWKeyMap[Keys.END] = GLFW.GLFW_KEY_END

        gdxGLFWKeyMap[Keys.BACKSPACE] = GLFW.GLFW_KEY_BACKSPACE

        gdxGLFWKeyMap[Keys.ENTER] = GLFW.GLFW_KEY_ENTER
        gdxGLFWKeyMap[Keys.ESCAPE] = GLFW.GLFW_KEY_ESCAPE

        gdxGLFWKeyMap[Keys.CONTROL_LEFT] = GLFW.GLFW_KEY_LEFT_CONTROL
        gdxGLFWKeyMap[Keys.CONTROL_RIGHT] = GLFW.GLFW_KEY_RIGHT_CONTROL
        gdxGLFWKeyMap[Keys.ALT_LEFT] = GLFW.GLFW_KEY_LEFT_ALT
        gdxGLFWKeyMap[Keys.ALT_RIGHT] = GLFW.GLFW_KEY_RIGHT_ALT
        gdxGLFWKeyMap[Keys.SHIFT_LEFT] = GLFW.GLFW_KEY_LEFT_SHIFT
        gdxGLFWKeyMap[Keys.SHIFT_RIGHT] = GLFW.GLFW_KEY_RIGHT_SHIFT

        gdxGLFWKeyMap[Keys.A] = GLFW.GLFW_KEY_A
        gdxGLFWKeyMap[Keys.C] = GLFW.GLFW_KEY_C
        gdxGLFWKeyMap[Keys.V] = GLFW.GLFW_KEY_V
        gdxGLFWKeyMap[Keys.X] = GLFW.GLFW_KEY_X
        gdxGLFWKeyMap[Keys.Y] = GLFW.GLFW_KEY_Y
        gdxGLFWKeyMap[Keys.Z] = GLFW.GLFW_KEY_Z
    }

    override fun keyTyped(character: Char): Boolean {
        LwjglGL3.charCallback(character.toInt())
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        LwjglGL3.scrollCallback(Vec2d(0, -amount))
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        LwjglGL3.mouseButtonCallback(button, GLFW.GLFW_PRESS, 0)
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        gdxGLFWKeyMap[keycode]?.apply {
            LwjglGL3.keyCallback(this, 0, GLFW.GLFW_RELEASE, 0)
        }
        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        gdxGLFWKeyMap[keycode]?.apply {
            LwjglGL3.keyCallback(this, 0, GLFW.GLFW_PRESS, 0)
        }
        return false
    }
}