package kotray

import kotlin.math.sqrt

/**
 * 3次元ベクトル
 *
 * @param x :
 * @param y :
 * @param z :
 */
data class Vec3(val x: Double, val y: Double, val z: Double) {
    /** @return ベクトルの長さ */
    val length: Double
        get() = sqrt(squaredLength)

    /** @return ベクトルの長さの二乗 */
    val squaredLength: Double
        get() = x * x + y * y + z * z

    /** @return 長さを1にしたベクトル */
    val unit: Vec3
        get() = div(length)

    operator fun unaryMinus(): Vec3 = Vec3(-x, -y, -z)

    operator fun plus(v: Vec3): Vec3 = Vec3(x + v.x, y + v.y, z + v.z)

    operator fun minus(v: Vec3): Vec3 = Vec3(x - v.x, y - v.y, z - v.z)

    operator fun times(s: Double): Vec3 = Vec3(x * s, y * s, z * s)

    operator fun times(v: Vec3): Vec3 = Vec3(x * v.x, y * v.y, z * v.z)

    operator fun div(s: Double): Vec3 = Vec3(x / s, y / s, z / s)
}

/**
 * 内積
 */
fun dot(v1: Vec3, v2: Vec3): Double = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z

/**
 * 外積
 */
fun cross(v1: Vec3, v2: Vec3): Vec3 {
    return Vec3(
            v1.y * v2.z - v1.z * v2.y,
            v1.z * v2.x - v1.x * v2.z,
            v1.x * v2.y - v1.y * v2.x)
}

operator fun Double.times(vec3: Vec3) = vec3 * this
