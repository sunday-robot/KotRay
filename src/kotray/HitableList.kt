package kotray

/**
 * [Ray]と[Hitable]の衝突情報
 */
class HitableList(
        private val list: List<Hitable>) {
    fun hit(r: Ray, tMin: Double, tMax: Double): HitRecord? {
        var hitRecord: HitRecord? = null
        var closestSoFar = tMax
        for (hitable in list) {
            val tmp = hitable.hit(r, tMin, closestSoFar)
            if (tmp != null) {
                closestSoFar = tmp.t
                hitRecord = tmp
            }
        }
        return hitRecord
    }

    fun boundingBox(t0:Double, t1:Double) : Aabb? {
        if (list.isEmpty())
            return null
        var box = list[0].boundingBox(t0, t1)
        for (i in 1 until list.size) {
            box += list[i].boundingBox(t0, t1)
        }
        return box
    }
}
