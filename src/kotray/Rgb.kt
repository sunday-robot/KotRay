package kotray

/**
 * 色(光)
 *
 * 実際の光は、様々な波長の混成であるので、物理的には全く不正確なものである。
 * 波長による屈折率の違いなどがないので、虹はできない。
 *
 * Vec3とは3つの浮動小数点数から構成されているということがたまたま一致しているというだけなので、同じクラスにはしていない。
 *
 * @param red :
 * @param green :
 * @param  blue :
 */
data class Rgb(val red: Double, val green: Double, val blue: Double) {
    operator fun plus(rgb: Rgb): Rgb = Rgb(red + rgb.red, green + rgb.green, blue + rgb.blue)

    operator fun times(rgb: Rgb): Rgb = Rgb(red * rgb.red, green * rgb.green, blue * rgb.blue)

    operator fun times(s: Double): Rgb = Rgb(red * s, green * s, blue * s)

    operator fun div(s: Double): Rgb = Rgb(red / s, green / s, blue / s)
}

operator fun Double.times(rgb: Rgb) = rgb * this
