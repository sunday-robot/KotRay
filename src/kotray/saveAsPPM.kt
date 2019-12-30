package kotray

import java.io.FileNotFoundException
import java.io.PrintStream
import kotlin.math.sqrt

/**
 * PNM形式のファイルに保存する
 * @param width　幅
 * @param height　高さ
 * @param pixels　画素
 * @param filePath ファイルパス
 */
@Throws(FileNotFoundException::class)
fun saveAsPPM(width: Int, height: Int, pixels: Array<Rgb>, filePath: String) {
    val ps = PrintStream(filePath)
    ps.printf("P3\n")
    ps.printf("%d %d\n", width, height)
    ps.printf("255\n")
    for (y in 0 until height) {
        for (x in 0 until width) {
            val p = pixels[x + y * width]
            val p2 = Vec3(sqrt(p.red), sqrt(p.green), sqrt(p.blue))
            val r = (255 * p2.x + 0.5).toInt()
            val g = (255 * p2.y + 0.5).toInt()
            val b = (255 * p2.z + 0.5).toInt()
            ps.printf("%d %d %d\n", r, g, b)
        }
    }
    ps.close()
}
