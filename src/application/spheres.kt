package application

import kotray.*
import kotray.hitable.Sphere
import kotray.material.Dielectric
import kotray.material.Lambertian
import kotray.material.Metal
import java.io.FileNotFoundException

fun main(args: Array<String>) {
    val imageWidth = 1280
    val imageHeight = 720
    val overSamplingCount = 1

    val world = HitableList(createScene())

    val lookFrom = Vec3(13.0, 2.0, 3.0)
    val lookAt = Vec3(0.0, 0.0, 0.0)
    val vFov = 20.0
    val aspect = imageWidth.toDouble() / imageHeight.toDouble()
    val aperture = 0.1
    val distanceToFocus = 10.0
    val cam = createCamera(lookFrom, lookAt, Vec3(0.0, 1.0, 0.0),
            vFov, aspect, aperture, distanceToFocus, 0.0, 0.0)

    val pixels = render(world, cam, imageWidth, imageHeight, overSamplingCount)

    try {
        saveAsPPM(imageWidth, imageHeight, pixels, "Spheres.ppm")
    } catch (e: FileNotFoundException) {
        // TODO Auto-generated catch block
        e.printStackTrace()
    }
}

/**
 * 地面となる非常に大きな球と、三つの大きな球と、多数の小さな球を生成する
 */
private fun createScene(): List<Hitable> {
    val hitables = mutableListOf<Hitable>()

    // 多数の小さな級
    for (a in -11..10) {
        for (b in -11..10) {
            val center = Vec3(a + 0.9 * Math.random(), 0.2, b + 0.9 * Math.random())
            if ((center - Vec3(4.0, 0.2, 0.0)).length <= 0.9)
                continue
            val chooseMat = Math.random()
            val material: Material = when {
                chooseMat < 0.8 -> // ざらついたプラスチックのような素材
                    Lambertian(Rgb(Math.random() * Math.random(), Math.random() * Math.random(), Math.random() * Math.random()))
                chooseMat < 0.95 -> // 金属
                    Metal(Rgb(0.5 * (1 + Math.random()), 0.5 * (1 + Math.random()), 0.5 * (1 + Math.random())),
                            0.5 * Math.random())
                else -> // ガラス
                    Dielectric(1.5)
            }
            hitables.add(Sphere(center, 0.2, material))
        }
    }

    // 三つの大きな球
    hitables.add(Sphere(Vec3(0.0, 1.0, 0.0), 1.0, Dielectric(1.5)))
    hitables.add(Sphere(Vec3(-4.0, 1.0, 0.0), 1.0, Lambertian(Rgb(0.4, 0.2, 0.1))))
    hitables.add(Sphere(Vec3(4.0, 1.0, 0.0), 1.0, Metal(Rgb(0.7, 0.6, 0.5), 0.0)))

    // 地面となる非常に大きな球
    hitables.add(Sphere(Vec3(0.0, -1000.0, 0.0), 1000.0, Lambertian(Rgb(0.5, 0.5, 0.5))))

    return hitables
}
