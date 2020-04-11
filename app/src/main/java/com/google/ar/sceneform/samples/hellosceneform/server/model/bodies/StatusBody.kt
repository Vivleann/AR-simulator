package com.sobol.testserverrequests.server.model.bodies

import com.sobol.testserverrequests.server.model.Position

class StatusBody (
    val action: String,
    val positions: Array<Position>
)