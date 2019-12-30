package kotray.material

import kotray.*

/**
 * 完全な拡散反射をする材質(入射角とは無関係に反射する)
 *
 * @param albedo 素材の色
 */
class Lambertian(private val albedo: Rgb) : Material() {
    override fun scatter(ray: Ray, position: Vec3, normal: Vec3): Pair<Rgb, Ray> {
        val direction = normal + randomInUnitSphere()
        val scatteredRay = Ray(position, direction, ray.time)
        return Pair(albedo, scatteredRay)
    }
}
