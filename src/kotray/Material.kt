package kotray

import kotlin.math.sqrt

/**
 * 素材の基底クラス
 */
abstract class Material {
    /**
     * @param ray レイ
     * @param position レイの衝突点
     * @param normal 衝突点の法線
     * @return 衝突点で分散(？)されたレイ
     */
    abstract fun scatter(ray: Ray, position: Vec3, normal: Vec3): Pair<Rgb, Ray>?

    /**
     * 反射
     * @param v 入射ベクトル
     * @param normal 法線ベクトル
     * @return 反射ベクトル
     */
    fun reflect(v: Vec3, normal: Vec3): Vec3 = v - 2 * dot(v, normal) * normal

    /**
     * 屈折
     * @param v 入射ベクトル
     * @param normal 法線ベクトル
     * @param niOverNt ?
     * @return 屈折ベクトル(屈折しない場合はnull)
     */
    fun refract(v: Vec3, normal: Vec3, niOverNt: Double): Vec3? {
        val uv = v.unit
        val dt = dot(uv, normal)
        val discriminant = 1.0 - niOverNt * niOverNt * (1.0 - dt * dt)
        if (discriminant <= 0.0) {
            return null
        }
        return niOverNt * (uv - normal * dt) - normal * sqrt(discriminant)
    }
}
