package com.google.ar.sceneform.samples.hellosceneform.android

import com.google.ar.sceneform.samples.hellosceneform.model.Description

class Database {

    private object Holder {
        val INSTANCE = Database()
    }

    companion object {
        val instance: Database by lazy { Holder.INSTANCE }
    }

    val descriptions = arrayOf(
            Description(
                    "Странное зеркало",
                    "Мяч, врязаясь в пирамидку, отражается в обратном направлении"
            ),
            Description(
                    "Портал",
                    "Мяч, врязаясь в пирамидку, отражается от противоположной стороны с инвертированным вектором по оси направления стороны кубика."
            ),
            Description(
                    "Выпрямитель",
                    "Мяч, врязаясь в пирамидку, отражается от стороны, в которую врезался, под углом 90 градусов ( перпендикулярно стороне)."
            ),
            Description(
                    "Нормальный ",
                    "Оставшиеся две пирамидки отражают мяч по обычному закону отражения"
            ),
            Description(
                    "Угол",
                    "Мяч, вгрызаясь в пирамидку, отражается от стороны кубика, в которую он врезался, под углом 45 градусов в сторону, соответствующую развороту кубика."
            )
    )

    private var polygonIcCnt = -1
    var currentId = 0
    var figuresLeft = 5

    fun getId(): Int {
        polygonIcCnt++
        return polygonIcCnt
    }

}