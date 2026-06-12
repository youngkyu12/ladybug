package com.example.ladybug.game.main

import kr.ac.tukorea.ge.spgp2026.a2dg.objects.Sprite
import kr.ac.tukorea.ge.spgp2026.a2dg.objects.GyroscopeController
import kr.ac.tukorea.ge.spgp2026.a2dg.view.GameContext
import com.example.ladybug.R

class Player(
    gctx: GameContext,
    private val gyroscopeController: GyroscopeController,
) : Sprite(gctx, R.mipmap.ladybug_player) {
    private val speed = 550f

    init {
        setCenterProportionalWidth(200f, 700f, 200f)
    }

    // 매 프레임 센서 입력값을 읽어 위치를 바꾼다.
    override fun update(gctx: GameContext) {
        x += gyroscopeController.x * speed * gctx.frameTime
        y += gyroscopeController.y * speed * gctx.frameTime

        val halfWidth = width / 2f
        val halfHeight = height / 2f
        x = x.coerceIn(halfWidth, gctx.metrics.width - halfWidth)
        y = y.coerceIn(halfHeight, gctx.metrics.height - halfHeight)
        syncDstRect()
    }
}
