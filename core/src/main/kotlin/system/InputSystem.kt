package system

import com.badlogic.gdx.math.Vector2
import com.github.quillraven.fleks.AllOf
import com.github.quillraven.fleks.ComponentMapper
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import component.InputComponent
import component.PlayerComponent
import component.TransformComponent

@AllOf([PlayerComponent::class])
class InputSystem(
    private val inputMap: ComponentMapper<InputComponent>,
    private val transformMap: ComponentMapper<TransformComponent>,
) : IteratingSystem() {

    private val speedUp = Vector2()

    override fun onTickEntity(entity: Entity) {

        inputMap[entity].also { playerInput ->
            if (playerInput.isMoving) {
                transformMap[entity].apply {
                    speedUp.set(acceleration, 0f).also { speed ->
                        if (playerInput.right) accelerator.add(speed.setAngleDeg(0f))
                        if (playerInput.up) accelerator.add(speed.setAngleDeg(90f))
                        if (playerInput.left) accelerator.add(speed.setAngleDeg(180f))
                        if (playerInput.down) accelerator.add(speed.setAngleDeg(270f))
                    }
                }
            }
        }
    }
}
