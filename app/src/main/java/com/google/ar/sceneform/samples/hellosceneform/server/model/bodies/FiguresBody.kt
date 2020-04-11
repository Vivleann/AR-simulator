package com.sobol.testserverrequests.server.model.bodies

import com.sobol.testserverrequests.server.model.Figure

class FiguresBody (
    val sender: String,
    val figures: Array<Figure>
)