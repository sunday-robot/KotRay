package kotray

import java.lang.Math.random

/**
 * @return 原点を中心とする半径1のXY平面上の円の中のランダムな位置
 */
fun randomInUnitDisk(): Vec3 {
    while (true) {
        val p = 2.0 * Vec3(random(), random(), 0.0) - Vec3(1.0, 1.0, 0.0)
        if (p.squaredLength < 1.0)
            return p
    }
}
