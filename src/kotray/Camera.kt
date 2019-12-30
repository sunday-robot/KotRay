package  kotray

import java.lang.Math.PI
import kotlin.math.tan

/**
 * カメラ
 * @param lowerLeftCorner :
 * @param horizontal :
 * @param vertical :
 * @param origin 視点
 * @param u カメラのX軸方向(単位ベクトルであること)
 * @param v カメラのY軸方向(単位ベクトルであること)
 * @param w :
 * @param lensRadius レンズのサイズ(ボケを決めるもので、小さいほどボケない。0なら全くボケない。)
 */
class Camera(
        private val lowerLeftCorner: Vec3 = Vec3(-2.0, -1.0, -1.0),
        private val horizontal: Vec3 = Vec3(4.0, 0.0, 0.0),
        private val vertical: Vec3 = Vec3(0.0, 2.0, 0.0),
        private val origin: Vec3 = Vec3(0.0, 0.0, 0.0),
        private val u: Vec3 = Vec3(0.0, 0.0, 0.0),
        private val v: Vec3 = Vec3(0.0, 0.0, 0.0),
        private val w: Vec3 = Vec3(0.0, 0.0, 0.0),
        private val lensRadius: Double = 0.0,
        private val time0: Double = 0.0,
        private val time1: Double = 1.0) {

    /**
     * @param s 横方向の位置(0～1)
     * @param t 縦方向の位置(0～1)
     * @return レイ
     */
    fun getRay(s: Double, t: Double): Ray {
        val rd = lensRadius * randomInUnitDisk()
        val offset = u * rd.x + v * rd.y
        val time = time0 + Math.random() * (time1 - time0)
        return Ray(origin + offset, lowerLeftCorner + s * horizontal + t * vertical - origin - offset, time)
    }
}

/**
 * @param lookFrom 視点
 * @param lookAt 注視点(視線の方向を決めるためだけのもので、ピントがあう場所ではない。ピント位置は、focusDistで指定する。)
 * @param vup 上方向を示すベクトル(視点、注視点のベクトルと同じ方向でなければよい。直交している必要もないし、長さも適当でよい)
 * @param verticalFov 縦方向の視野(角度[°]]
 * @param aspect 縦横比(幅/高さ)
 * @param aperture 絞り(ボケ具体を決めるもの。0なら全くボケない。)
 * @param focusDist 視点からピントが合う位置までの距離
 */
fun createCamera(
        lookFrom: Vec3,
        lookAt: Vec3,
        vup: Vec3,
        verticalFov: Double,
        aspect: Double,
        aperture: Double,
        focusDist: Double,
        time0: Double,
        time1: Double):
        Camera {
    val theta = verticalFov * PI / 180.0
    val halfHeight = tan(theta / 2.0)
    val halfWidth = aspect * halfHeight
    val w = (lookAt - lookFrom).unit
    val u = -cross(vup, w).unit
    val v = -cross(w, u)
    val lowerLeftCorner = (lookFrom + focusDist * w
            - halfWidth * focusDist * u
            - halfHeight * focusDist * v)
    val horizontal = 2 * halfWidth * focusDist * u
    val vertical = 2 * halfHeight * focusDist * v
    val lensRadius = aperture / 2.0
    return Camera(lowerLeftCorner, horizontal, vertical, lookFrom, u, v, w, lensRadius, time0, time1)
}
