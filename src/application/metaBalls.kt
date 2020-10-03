package application

import kotray.*
import kotray.hitable.Sphere
import kotray.material.Lambertian
import kotray.material.Metal
import java.io.FileNotFoundException

fun main(args: Array<String>) {
    val imageWidth = 1280
    val imageHeight = 800
    val overSamplingCount = 100

    val world = HitableList(createRandomScene())

    val lookFrom = Vec3(0.0, 2.0, 16.0)
    val lookAt = Vec3(0.0, 0.0, 0.0)
    val vFov = 60.0
    val aspect = imageWidth.toDouble() / imageHeight.toDouble()
    val aperture = 0.1
    val distanceToFocus = 10.0
    val cam = createCamera(lookFrom, lookAt, Vec3(0.0, 1.0, 0.0),
            vFov, aspect, aperture, distanceToFocus, 0.0, 0.0)

    val pixels = render(world, cam, imageWidth, imageHeight, overSamplingCount)

    try {
        saveAsPPM(imageWidth, imageHeight, pixels, "MetalBalls.ppm")
    } catch (e: FileNotFoundException) {
        // TODO Auto-generated catch block
        e.printStackTrace()
    }
}

/**
 * 地面となる非常に大きな球と、三つの大きな球と、多数の小さな球を生成する
 */
private fun createRandomScene(): List<Hitable> {
    val hitables = mutableListOf<Hitable>()

    for (i in -5..5)
        hitables.add(Sphere(Vec3(i * 3.0, 1.0, 0.0), 1.0, Metal(Rgb(1.0, 0.5, 0.25), (i + 5) / 5.0)))

    // 地面となる非常に大きな球
    hitables.add(Sphere(Vec3(0.0, -1000.0, 0.0), 1000.0, Lambertian(Rgb(0.5, 0.5, 0.5))))

    return hitables
}
