import GameBoot.Companion.sizes
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxScreen
import ktx.assets.disposeSafely

abstract class BaseScreen : KtxScreen {

    private val actionMap = mutableMapOf<Int, Action.Name>()
    protected val batch = SpriteBatch()
    protected val camera = OrthographicCamera().apply {
        setToOrtho(false, sizes.windowWidthF(), sizes.windowHeightF())
    }
    protected val hudStage = Stage(FitViewport(sizes.windowWidthF(), sizes.windowHeightF()), batch)

    abstract fun doAction(action: Action)

    fun registerAction(inputKey: Int, actionName: Action.Name) {
        actionMap[inputKey] = actionName
    }

    fun getActionMap(): MutableMap<Int, Action.Name> = actionMap

    override fun show() {
        (Gdx.input.inputProcessor as InputMultiplexer).apply {
            addProcessor(hudStage)
        }
    }

    override fun hide() {
        (Gdx.input.inputProcessor as InputMultiplexer).apply {
            removeProcessor(hudStage)
        }
    }

    override fun resize(width: Int, height: Int) {
        hudStage.viewport.update(width, height)
    }

    override fun dispose() {
        hudStage.disposeSafely()
        batch.disposeSafely()
    }
}
