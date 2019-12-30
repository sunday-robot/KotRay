package kotray.hitable

import kotray.*
import kotray.Material
import kotlin.math.sqrt

/**
 * @param center :
 * @param radius :
 * @param material :
 */
class Sphere(
        private val center: Vec3,
        private val radius: Double,
        private val material: Material) : Hitable() {
    override fun hit(ray: Ray, tMin: Double, tMax: Double): HitRecord? {
        val oc = ray.origin - center
        val a = ray.direction.squaredLength
        val b = dot(oc, ray.direction)
        val c = oc.squaredLength - radius * radius
        val discriminant = b * b - a * c
        if (discriminant < 0)
            return null
        val d2 = sqrt(discriminant)
        val t = (-b - d2) / a
        if (t > tMin && t < tMax) {
            val p = ray.positionAt(t)
            val normal = (p - center) / radius
            return HitRecord(t, p, normal, material)
        }
        val t2 = (-b + d2) / a
        if (t2 > tMin && t2 < tMax) {
            val p = ray.positionAt(t2)
            val normal = (p - center) / radius
            return HitRecord(t2, p, normal, material)
        }
        return null
    }
    override fun boundingBox(t0: Double, t1: Double): Aabb {
        val min = center - Vec3(radius, radius, radius)
        val max = center + Vec3(radius, radius, radius)
        return Aabb(min, max)
    }
}
