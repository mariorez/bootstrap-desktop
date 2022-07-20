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

            // move by
            if (velocity.x != 0f || velocity.y != 0f) {
                position += velocity * deltaTime
            }

            // reset acceleration
            accelerator.set(0f, 0f)
        }
    }
}
