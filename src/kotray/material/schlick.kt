package kotray.material

import kotlin.math.pow

/**
 * @param cosine 余弦
 * @param refIdx 屈折率
 * @return ?
 */
fun schlick(cosine: Double, refIdx: Double): Double {
    val tmp = (1.0 - refIdx) / (1.0 + refIdx)
    val r0 = tmp * tmp
    return r0 + (1.0 - r0) * (1.0 - cosine).pow(5.0)
}

fun originalSchlick(cosine: Double, n1: Double, n2:Double): Double {
    val tmp = (n1 - n2) / (n1 + n2)
    val r0 = tmp * tmp
    return r0 + (1.0 - r0) * (1.0 - cosine).pow(5.0)
}
