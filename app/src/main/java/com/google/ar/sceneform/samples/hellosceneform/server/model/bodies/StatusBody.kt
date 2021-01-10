package com.google.ar.sceneform.samples.hellosceneform.server.model.bodies

import com.google.ar.sceneform.samples.hellosceneform.server.model.Position

class StatusBody (
    val action: String,
    val positions: Array<Position>
)