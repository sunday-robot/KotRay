package kotray

/**
 * [Ray]と[Hitable]の衝突情報
 * @param t レイ軸上の位置
 * @param position 衝突点
 * @param normal 衝突点の法線
 * @param material 衝突点の表面素材
 */
data class HitRecord(val t: Double, val position: Vec3, val normal: Vec3, val material: Material)
