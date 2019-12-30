package kotray

private const val T_MIN = 0.001

fun render(world: HitableList, camera: Camera, width: Int, height: Int, sampleCount: Int): Array<Rgb> {
    val t0 = System.nanoTime()
    val pixels = Array(width * height) { i ->
        val x = (i % width).toDouble()
        val y = ((height - 1) - i / width).toDouble()
        var col = Rgb(0.0, 0.0, 0.0)
        repeat(sampleCount) {
            val u = (x + Math.random()) / width
            val v = (y + Math.random()) / height
            val r = camera.getRay(u, v)
            col += color(r, world, 0)
        }
        col / sampleCount.toDouble()
    }
    val t1 = System.nanoTime()
    System.out.printf("time = %,3d ms", (t1 - t0) / 1_000_000)
    return pixels
}

/**
 * 色を返す
 * @param ray レイ
 * @param world 物体群
 * @param depth レイの反射回数？(50回で打ち切る)
 * @return 色
 */
private fun color(ray: Ray, world: HitableList, depth: Int): Rgb {
    if (depth > 50)
        return Rgb(0.0, 0.0, 0.0)  // 反射回数が規定値よりも多い場合は(0,0,0)を返す

    val hitRecord = world.hit(ray, T_MIN, Double.MAX_VALUE)
    if (hitRecord == null) {
        // どの物体ともヒットしない場合は、天球の色を返す
        val unitDirection = ray.direction.unit
        val t = 0.5 * (unitDirection.y + 1.0)
        val v1 = Rgb(1.0, 1.0, 1.0)
        val v2 = Rgb(0.5, 0.7, 1.0)
        return (1.0 - t) * v1 + t * v2
    }

    val p = hitRecord.material.scatter(ray, hitRecord.position, hitRecord.normal) ?: return Rgb(0.0, 0.0, 0.0)
    return p.first * color(p.second, world, depth + 1)
}
