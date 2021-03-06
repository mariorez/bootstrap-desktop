package component

import com.badlogic.gdx.math.Vector2

data class TransformComponent(
    var position: Vector2 = Vector2(),
    var velocity: Vector2 = Vector2(),
    var accelerator: Vector2 = Vector2(),
    var acceleration: Float = 0f,
    var deceleration: Float = 0f,
    var maxSpeed: Float = 1000f,
    var rotation: Float = 0f,
    var zIndex: Float = 0f
) {
    fun setSpeed(speed: Float) {
        // if length is zero, then assume motion angle is zero degrees
        if (velocity.len() == 0f) velocity.set(speed, 0f)
        else velocity.setLength(speed)
    }

    fun getMotionAngle(): Float {
        return velocity.angleDeg()
    }

    fun setMotionAngle(angle: Float) {
        velocity.setAngleDeg(angle)
    }

    fun rotateBy(degrees: Float) {
        if (degrees != 0f) rotation = (rotation + degrees) % 360
    }
}
