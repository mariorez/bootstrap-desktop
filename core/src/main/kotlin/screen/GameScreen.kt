package screen

import Action
import Action.Type.START
import BaseScreen
import GameBoot
import GameBoot.Companion.assets
import GameBoot.Companion.sizes
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.world
import component.InputComponent
import component.PlayerComponent
import component.RenderComponent
import component.TransformComponent
import generateButton
import ktx.actors.onTouchDown
import system.InputSystem
import system.MovementSystem
import system.RenderSystem

class GameScreen(
    private val gameBoot: GameBoot
) : BaseScreen() {
    private val player: Entity
    private val world = world {
        injectables {
            add(batch)
            add(camera)
        }
        systems {
            add<InputSystem>()
            add<MovementSystem>()
            add<RenderSystem>()
        }
    }

    init {
        registerAction(Input.Keys.W, Action.Name.UP)
        registerAction(Input.Keys.UP, Action.Name.UP)
        registerAction(Input.Keys.S, Action.Name.DOWN)
        registerAction(Input.Keys.DOWN, Action.Name.DOWN)
        registerAction(Input.Keys.A, Action.Name.LEFT)
        registerAction(Input.Keys.LEFT, Action.Name.LEFT)
        registerAction(Input.Keys.D, Action.Name.RIGHT)
        registerAction(Input.Keys.RIGHT, Action.Name.RIGHT)

        buildHud()

        world.apply {
            player = entity {
                val logoTexture = assets.get<Texture>("logo.png")
                add<PlayerComponent>()
                add<InputComponent>()
                add<RenderComponent> { sprite = Sprite(logoTexture) }
                add<TransformComponent> {
                    position.x = sizes.worldWidthF() / 2 - logoTexture.width / 2
                    position.y = sizes.worldHeightF() / 2 - logoTexture.height / 2
                    acceleration = 400f
                    deceleration = 250f
                    maxSpeed = 150f
                }
            }
        }
    }

    override fun render(delta: Float) {
        world.update(delta)
        hudStage.draw()
    }

    override fun dispose() {
        super.dispose()
        world.dispose()
    }

    private fun buildHud() {
        val restartButton = generateButton(assets["back-button.png"]).apply {
            onTouchDown { gameBoot.setScreen<MenuScreen>() }
        }

        hudStage.addActor(Table().apply {
            setFillParent(true)
            pad(10f)
            add(restartButton).expandX().expandY().right().top()
        })
    }

    override fun doAction(action: Action) {
        world.mapper<InputComponent>().getOrNull(player)?.let {
            val isStarting = action.type == START
            when (action.name) {
                Action.Name.UP -> it.up = isStarting
                Action.Name.DOWN -> it.down = isStarting
                Action.Name.LEFT -> it.left = isStarting
                Action.Name.RIGHT -> it.right = isStarting
                else -> {}
            }
        }

        super.doAction(action)
    }
}
