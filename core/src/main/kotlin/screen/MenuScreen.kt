package screen

import Action
import BaseScreen
import GameBoot
import GameBoot.Companion.assets
import GameBoot.Companion.sizes
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import generateTextButton
import ktx.actors.onTouchDown

class MenuScreen(
    private val gameBoot: GameBoot
) : BaseScreen() {

    init {
        hudStage.apply {
            addActor(Table().apply {
                setFillParent(true)
                add(Image(assets.get<Texture>("logo.png"))).colspan(2).padBottom(50f)
                row()
                add(generateTextButton("Start").apply {
                    onTouchDown { gameBoot.setScreen<GameScreen>() }
                })
                add(generateTextButton("Quit").apply {
                    onTouchDown { Gdx.app.exit() }
                })
            })
        }
    }

    override fun render(delta: Float) {
        hudStage.draw()

        if (Gdx.input.isKeyPressed(Input.Keys.F))
            Gdx.graphics.setFullscreenMode(Gdx.graphics.displayMode)

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.graphics.setWindowedMode(sizes.windowWidth, sizes.windowHeight)
    }

    override fun doAction(action: Action) {}
}
