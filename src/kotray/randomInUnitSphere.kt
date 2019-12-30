package kotray

/**
 * @return 原点を中心とする半径1の球内のランダムな座標
 */
fun randomInUnitSphere(): Vec3 {
    while (true) {
        val p = 2.0 * Vec3(Math.random(), Math.random(), Math.random()) - Vec3(1.0, 1.0, 1.0)
        if (p.squaredLength < 1.0)
            return p
    }
}
