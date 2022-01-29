package kotray

import kotlin.math.max
import kotlin.math.min

/**
 * AxisAlignedBoundingBox
 * @property min :
 * @property max :
 */
class Aabb(
        private val min: Vec3,
        private val max: Vec3) {
    fun hit(
            ray: Ray,
            tMin: Double,
            tMax: Double)
            : Boolean {
        val (xMin, xMax) = hitSub(min.x, max.x, ray.origin.x, ray.direction.x, tMin, tMax)
        if (xMax <= xMin)
            return false
        val (yMin, yMax) = hitSub(min.y, max.y, ray.origin.y, ray.direction.y, xMin, xMax)
        if (yMax <= yMin)
            return false
        val (zMin, zMax) = hitSub(min.z, max.z, ray.origin.z, ray.direction.z, yMin, yMax)
        if (zMax <= zMin)
            return false
        return true
    }

    private fun hitSub(
            min_: Double,
            max_: Double,
            rayOrigin: Double,
            rayDirection: Double,
            tMin: Double,
            tMax: Double)
            : Pair<Double, Double> {
        val a = (min_ - rayOrigin) / rayDirection
        val b = (max_ - rayOrigin) / rayDirection
        return if (a < b) {
            Pair(max(a, tMin), min(b, tMax))
        } else {
            Pair(max(b, tMin), min(a, tMax))
        }
    }

    operator fun plus(arg: Aabb): Aabb {
        val minX = min(min.x, arg.min.x)
        val maxX = min(max.x, arg.max.x)
        val minY = min(min.y, arg.min.y)
        val maxY = max(max.y, arg.max.y)
        val minZ = max(min.z, arg.min.z)
        val maxZ = max(max.z, arg.max.z)
        return Aabb(Vec3(minX, minY, minZ), Vec3(maxX, maxY, maxZ))
    }
}
