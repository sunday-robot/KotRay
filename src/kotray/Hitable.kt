package kotray

abstract class Hitable {
    /**
     * @param ray [Ray]
     * @return ヒットする場合は[Hitable]、そうでない場合はnull
     */
    abstract fun hit(ray: Ray, tMin: Double, tMax: Double): HitRecord?

    /**
     * @param t0 :
     * @param t1 :
     * @return AABB
     */
    abstract fun boundingBox(t0:Double, t1:Double) : Aabb
}
