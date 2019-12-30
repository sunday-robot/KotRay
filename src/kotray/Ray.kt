package kotray

/**
 * @param origin 原点
 * @param direction 方向
 */
data class Ray(val origin: Vec3, val direction: Vec3, val time: Double) {
    /**
     * @param t :
     * @return tにおける位置
     */
    fun positionAt(t: Double): Vec3 = origin + direction * t
}
