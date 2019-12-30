package kotray.material

import kotray.*

/**
 * 金属マテリアル
 *
 * @param albedo 色
 * @param fuzz <=1
 */
class Metal(private val albedo: Rgb, private val fuzz: Double) : Material() {
    override fun scatter(ray: Ray, position: Vec3, normal: Vec3): Pair<Rgb, Ray>? {
        val reflectionDirection = reflect(ray.direction.unit, normal)
        val scatteredRay = Ray(position, reflectionDirection + fuzz * randomInUnitSphere(), 0.0)
        if (dot(scatteredRay.direction, normal) <= 0)
            return null
        return Pair(albedo, scatteredRay)
    }
}