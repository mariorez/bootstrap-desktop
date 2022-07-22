package system

import com.github.quillraven.fleks.AllOf
import com.github.quillraven.fleks.ComponentMapper
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import component.PlayerComponent
import component.TransformComponent
import ktx.math.plus
import ktx.math.times

@AllOf([PlayerComponent::class])
class MovementSystem(
    private val transformMap: ComponentMapper<TransformComponent>
) : IteratingSystem() {

    /**
     * Adjust velocity vector based on accelerator vector,
     * then adjust position based on velocity vector.
     * If not accelerating, deceleration value is applied.
     * Speed is limited by maxSpeed value.
     * Accelerator vector reset to (0,0) at end of method.
     */
    override fun onTickEntity(entity: Entity) {
        transformMap[entity].apply {
            // apply acceleration
            velocity += accelerator * deltaTime

            var speed = velocity.len()

            // decrease speed (decelerate) when not accelerating
            if (accelerator.len() == 0f) {
                speed -= deceleration * deltaTime
            }

            // keep speed within set bounds
            speed = speed.coerceIn(0f, maxSpeed)

            // update velocity
            setSpeed(speed)

            // update position according to value stored in movement vector
            if (velocity.x != 0f || velocity.y != 0f) {
                position += velocity * deltaTime
            }

            // reset acceleration
            accelerator.set(0f, 0f)
        }
    }
}
