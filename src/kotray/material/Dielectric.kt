package kotray.material

import kotray.*

/**
 * 誘電体マテリアル(透明な材質)
 *
 * @param refractiveIndex 屈折率
 */
class Dielectric(private val refractiveIndex: Double) : Material() {
    override fun scatter(ray: Ray, position: Vec3, normal: Vec3): Pair<Rgb, Ray>? {
        val attenuation = Rgb(1.0, 1.0, 1.0)

        val outwardNormal: Vec3
        val rri: Double // 相対屈折率(元の素材の屈折率/先の素材の屈折率)
        if (dot(ray.direction, normal) > 0) {
            // レイがこの素材の物体から出る場合
            outwardNormal = -normal
            rri = refractiveIndex
        } else {
            // レイがこの素材の物体に入る場合
            outwardNormal = normal
            rri = 1.0 / refractiveIndex
        }
        val refractedDirection = refract(ray.direction, outwardNormal, rri)
                ?: return Pair(attenuation, Ray(position, reflect(ray.direction, normal), 0.0))
        val cosine = -dot(ray.direction, outwardNormal) / ray.direction.length
        val reflectProb = schlick(cosine, refractiveIndex)
        val direction = if (Math.random() < reflectProb)
            reflect(ray.direction, normal)
        else
            refractedDirection
        return Pair(attenuation, Ray(position, direction, 0.0))
    }
}
