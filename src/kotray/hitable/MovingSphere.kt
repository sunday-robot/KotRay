package kotray.hitable

import kotray.*
import kotlin.math.sqrt

/**
 * @param center0 :
 * @param time0 :
 * @param center1 :
 * @param time1 :
 * @param radius :
 * @param material :
 */
class MovingSphere(
        private val center0: Vec3,
        private val time0: Double,
        private val center1: Vec3,
        private val time1: Double,
        private val radius: Double,
        private val material: Material) : Hitable() {
    /**
     * t=0における位置
     */
    private val centerOrigin: Vec3
    private val v: Vec3

    init {
        val dt = time1 - time0
        centerOrigin = (center0 * time1 - center1 * time0) / dt
        v = (center1 - center0) / dt
    }

    private fun center(time: Double): Vec3 {
        return centerOrigin + v * time
    }

    override fun hit(ray: Ray, tMin: Double, tMax: Double): HitRecord? {
        val center = center(ray.time)
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
        val c0 = center(t0)
        val c1 = center(t1)
        val (minX, maxX) = boundingBoxSub(c0.x, c1.x)
        val (minY, maxY) = boundingBoxSub(c0.y, c1.z)
        val (minZ, maxZ) = boundingBoxSub(c0.y, c1.z)
        return Aabb(Vec3(minX, minY, minZ), Vec3(maxX, maxY, maxZ))
    }

    private fun boundingBoxSub(c0: Double, c1: Double): Pair<Double, Double> {
        return if (c0 < c1)
            Pair(c0 - radius, c1 + radius)
        else
            Pair(c1 - radius, c0 + radius)
    }
}
