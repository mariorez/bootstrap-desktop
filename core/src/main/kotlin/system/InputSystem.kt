package system

import com.github.quillraven.fleks.AllOf
import com.github.quillraven.fleks.ComponentMapper
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import component.InputComponent
import component.PlayerComponent
import component.TransformComponent
import ktx.math.plus

@AllOf([PlayerComponent::class])
class InputSystem(
    private val inputMap: ComponentMapper<InputComponent>,
    private val transformMap: ComponentMapper<TransformComponent>,
) : IteratingSystem() {

    override fun onTickEntity(entity: Entity) {

        inputMap[entity].also { playerInput ->
            if (playerInput.isMoving) {
                transformMap[entity].apply {
                    accelerator.set(acceleration, 0f).also { accelerate ->
                        if (playerInput.right) accelerator += accelerate.setAngleDeg(0f)
                        if (playerInput.up) accelerator += accelerate.setAngleDeg(90f)
                        if (playerInput.left) accelerator += accelerate.setAngleDeg(180f)
                        if (playerInput.down) accelerator += accelerate.setAngleDeg(270f)
                    }
                }
            }
        }
    }
}
